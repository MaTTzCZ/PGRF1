package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode;
import dev.mattz.data.graphics.RasterizedBufferImage;
import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.gui.models.ColorPaletteModel;
import dev.mattz.data.gui.models.ToolbarModel;
import dev.mattz.data.gui.views.CanvasView;

import javax.swing.*;
import java.awt.event.*;

public class CanvasController {
    private final CanvasView view;
    private final ColorPaletteModel colorPaletteModel;
    private final ToolbarModel toolbarModel;
    private final RasterizedBufferImage rasterizedBufferImage;

    private Point2D startPoint, currentPoint, newPoint;

    public CanvasController(CanvasView view) {
        this.view = view;
        view.setFocusable(true);

        this.colorPaletteModel = ColorPaletteModel.getInstance();
        this.toolbarModel = ToolbarModel.getInstance();
        this.rasterizedBufferImage = new RasterizedBufferImage(view.getRasterizedBufferImage());

        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                switch (toolbarModel.getCurrentMode()) {
                    case LINE -> startPoint = new Point2D(e.getX(), e.getY());
                    case POLYGON -> {
                        if (startPoint == null) {
                            startPoint = currentPoint = new Point2D(e.getX(), e.getY());
                            view.setPolygonStart(startPoint);
                        } else {
                            newPoint = new Point2D(e.getX(), e.getY());
                            polygonMode(e);
                        }
                    }

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (toolbarModel.getCurrentMode() == Mode.LINE) {
                    currentPoint = new Point2D(e.getX(), e.getY());
                    if (e.isShiftDown()) currentPoint = snapToFixedAngle(startPoint, currentPoint);
                    lineMode(e);
                }
            }
        });

        view.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                switch (toolbarModel.getCurrentMode()) {
                    case LINE -> lineMode(e);
                    case PENCIL -> pencilMode(e);
                }
            }
        });

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    view.clear();
                }
            }
        });
    }

    private void lineMode(MouseEvent event) {
        if (currentPoint == null) {
            Point2D newPoint = new Point2D(event.getX(), event.getY());
            if (event.isShiftDown()) newPoint = snapToFixedAngle(startPoint, new Point2D(event.getX(), event.getY()));
            if (SwingUtilities.isLeftMouseButton(event))
                view.setTemporaryLine(startPoint, newPoint, colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                view.setTemporaryLine(startPoint, newPoint, colorPaletteModel.getSecondaryColor());
        } else {
            if (SwingUtilities.isLeftMouseButton(event))
                rasterizedBufferImage.lineMode(startPoint, currentPoint, colorPaletteModel.getPrimaryColor(), colorPaletteModel.getSecondaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                rasterizedBufferImage.lineMode(startPoint, currentPoint, colorPaletteModel.getSecondaryColor(), colorPaletteModel.getPrimaryColor());
            startPoint = currentPoint = null;
            view.clearTemporaryLine();
        }
    }

    private void polygonMode(MouseEvent event) {
        if (Math.abs(newPoint.getX() - startPoint.getX()) <= 5 && Math.abs(newPoint.getY() - startPoint.getY()) <= 5) {
            newPoint = startPoint;
            if (SwingUtilities.isLeftMouseButton(event))
                rasterizedBufferImage.lineMode(currentPoint, startPoint, colorPaletteModel.getPrimaryColor(), colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                rasterizedBufferImage.lineMode(currentPoint, startPoint, colorPaletteModel.getSecondaryColor(), colorPaletteModel.getSecondaryColor());
            view.clearPolygonStart();
            startPoint = currentPoint = newPoint = null;
        } else {
            if (SwingUtilities.isLeftMouseButton(event))
                rasterizedBufferImage.lineMode(currentPoint, newPoint, colorPaletteModel.getPrimaryColor(), colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                rasterizedBufferImage.lineMode(currentPoint, newPoint, colorPaletteModel.getSecondaryColor(), colorPaletteModel.getSecondaryColor());
            currentPoint = newPoint;
            view.repaint();
        }
    }

    private void pencilMode(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event))
            rasterizedBufferImage.pencilMode(new Point2D(event.getX(), event.getY()), colorPaletteModel.getPrimaryColor());
        else if (SwingUtilities.isRightMouseButton(event))
            rasterizedBufferImage.pencilMode(new Point2D(event.getX(), event.getY()), colorPaletteModel.getSecondaryColor());
        view.repaint();
    }

    private Point2D snapToFixedAngle(Point2D start, Point2D current) {
        double dx = current.getX() - start.getX();
        double dy = current.getY() - start.getY();

        double angle = Math.atan2(dy, dx);
        double length = Math.sqrt(dx * dx + dy * dy);

        double snappedAngle = Math.round(Math.toDegrees(angle) / 45.0) * 45.0;

        double rad = Math.toRadians(snappedAngle);
        int snappedX = (int) Math.round(start.getX() + Math.cos(rad) * length);
        int snappedY = (int) Math.round(start.getY() + Math.sin(rad) * length);

        return new Point2D(snappedX, snappedY);
    }
}
