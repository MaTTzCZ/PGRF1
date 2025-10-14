package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode;
import dev.mattz.data.graphics.RasterImage;
import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.gui.models.ColorPaletteModel;
import dev.mattz.data.gui.models.ToolbarModel;
import dev.mattz.data.gui.views.CanvasView;
import dev.mattz.data.gui.views.MainView;

import javax.swing.*;
import java.awt.event.*;

public class CanvasController {
    private final MainView mainView;
    private final CanvasView canvasView;
    private final ColorPaletteModel colorPaletteModel;
    private final ToolbarModel toolbarModel;
    private final RasterImage rasterImage;

    private Point2D startPoint, currentPoint, newPoint;

    public CanvasController(CanvasView canvasView, MainView mainView) {
        this.canvasView = canvasView;
        this.mainView = mainView;
        canvasView.setFocusable(true);

        this.colorPaletteModel = ColorPaletteModel.getInstance();
        this.toolbarModel = ToolbarModel.getInstance();
        this.rasterImage = new RasterImage(canvasView.getRasterizedBufferImage());

        canvasView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                switch (toolbarModel.getCurrentMode()) {
                    case LINE -> startPoint = new Point2D(e.getX(), e.getY());
                    case POLYGON -> {
                        if (startPoint == null) {
                            startPoint = currentPoint = new Point2D(e.getX(), e.getY());
                            canvasView.setPolygonStart(startPoint);
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

        canvasView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                switch (toolbarModel.getCurrentMode()) {
                    case LINE -> lineMode(e);
                    case PENCIL -> pencilMode(e);
                }
            }
        });

        canvasView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    canvasView.clear();
                }
            }
        });
    }

    private void lineMode(MouseEvent event) {
        if (currentPoint == null) {
            Point2D newPoint = new Point2D(event.getX(), event.getY());
            if (event.isShiftDown()) newPoint = snapToFixedAngle(startPoint, new Point2D(event.getX(), event.getY()));
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView.setTemporaryLine(startPoint, newPoint, colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView.setTemporaryLine(startPoint, newPoint, colorPaletteModel.getSecondaryColor());
        } else {
            if (SwingUtilities.isLeftMouseButton(event))
                if (mainView.isGradientLineSelected())
                    rasterImage.gradientLineMode(startPoint, currentPoint, colorPaletteModel.getPrimaryColor(), colorPaletteModel.getSecondaryColor());
                else rasterImage.lineMode(startPoint, currentPoint, colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                if (mainView.isGradientLineSelected())
                    rasterImage.gradientLineMode(startPoint, currentPoint, colorPaletteModel.getSecondaryColor(), colorPaletteModel.getPrimaryColor());
                else rasterImage.lineMode(startPoint, currentPoint, colorPaletteModel.getSecondaryColor());
            startPoint = currentPoint = null;
            canvasView.clearTemporaryLine();
        }
    }


    private void polygonMode(MouseEvent event) {
        if (Math.abs(newPoint.getX() - startPoint.getX()) <= 5 && Math.abs(newPoint.getY() - startPoint.getY()) <= 5) {
            newPoint = startPoint;
            if (SwingUtilities.isLeftMouseButton(event))
                rasterImage.polygonMode(currentPoint, startPoint, colorPaletteModel.getPrimaryColor(), true);
            else if (SwingUtilities.isRightMouseButton(event))
                rasterImage.polygonMode(currentPoint, startPoint, colorPaletteModel.getSecondaryColor(), true);
            canvasView.clearPolygonStart();
            startPoint = currentPoint = newPoint = null;
        } else {
            if (SwingUtilities.isLeftMouseButton(event))
                rasterImage.polygonMode(currentPoint, newPoint, colorPaletteModel.getPrimaryColor(), false);
            else if (SwingUtilities.isRightMouseButton(event))
                rasterImage.polygonMode(currentPoint, newPoint, colorPaletteModel.getSecondaryColor(), false);
            currentPoint = newPoint;
            canvasView.repaint();
        }
    }

    private void pencilMode(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event))
            rasterImage.pencilMode(new Point2D(event.getX(), event.getY()), colorPaletteModel.getPrimaryColor());
        else if (SwingUtilities.isRightMouseButton(event))
            rasterImage.pencilMode(new Point2D(event.getX(), event.getY()), colorPaletteModel.getSecondaryColor());
        canvasView.repaint();
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
