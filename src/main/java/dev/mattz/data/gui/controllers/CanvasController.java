package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode;
import dev.mattz.data.graphics.drawable_objects.*;
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

    private Point2D startPoint, currentPoint, newPoint, movedPoint;
    Polygon polygon;
    private PencilStroke pencilStroke;

    public CanvasController(CanvasView canvasView, MainView mainView) {
        this.canvasView = canvasView;
        this.mainView = mainView;
        canvasView.setFocusable(true);

        this.colorPaletteModel = ColorPaletteModel.getInstance();
        this.toolbarModel = ToolbarModel.getInstance();

        canvasView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                switch (toolbarModel.getCurrentMode()) {
                    case MOVE -> {
                        for (Drawable drawable : canvasView.getDrawables()) {
                            for (Point2D point : drawable.getAllPoints()) {
                                if (Math.abs(e.getX() - point.getX()) <= 5 && Math.abs(e.getY() - point.getY()) <= 5) {
                                    movedPoint = point;
                                    break;
                                }
                            }
                            if (movedPoint != null) break;
                        }
                    }
                    case LINE -> {
                        startPoint = new Point2D(e.getX(), e.getY());
                    }
                    case POLYGON -> {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            toolbarModel.setLocked(true);
                            if (startPoint == null) {
                                polygon = new Polygon(colorPaletteModel.getPrimaryColor());
                                startPoint = currentPoint = new Point2D(e.getX(), e.getY());
                                polygon.addPoint(startPoint);
                                canvasView.setPolygonStart(startPoint);
                            } else {
                                newPoint = new Point2D(e.getX(), e.getY());
                                polygonMode(e);
                            }
                        }
                    }
                    case PENCIL -> {
                        pencilMode(e);
                    }

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switch (toolbarModel.getCurrentMode()) {
                    case MOVE -> movedPoint = null;
                    case LINE -> {
                        currentPoint = new Point2D(e.getX(), e.getY());
                        if (e.isShiftDown())
                            currentPoint = snapToFixedAngle(startPoint, currentPoint);
                        lineMode(e);
                    }
                    case PENCIL -> pencilMode(e);
                }

            }
        });

        canvasView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                switch (toolbarModel.getCurrentMode()) {
                    case MOVE -> {
                        if (movedPoint != null){
                            System.out.println("negr");
                            movedPoint.setPosition(e.getX(), e.getY());
                        }
                    }
                    case LINE -> lineMode(e);
                    case PENCIL -> {
                        canvasView.setRGB(e.getX(), e.getY(), SwingUtilities.isLeftMouseButton(e) ? colorPaletteModel.getPrimaryColor() : colorPaletteModel.getSecondaryColor());
                        pencilStroke.addPoint(new Point2D(e.getX(), e.getY()));
                    }
                }
            }
        });

        canvasView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    canvasView.clearDrawables();
                    canvasView.clearBufferedImage();
                }
            }
        });
    }

    private void lineMode(MouseEvent event) {
        if (currentPoint == null) {
            Point2D newPoint = new Point2D(event.getX(), event.getY());
            if (event.isShiftDown())
                newPoint = snapToFixedAngle(startPoint, new Point2D(event.getX(), event.getY()));
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView.setTemporaryLine(startPoint, newPoint, colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView.setTemporaryLine(startPoint, newPoint, colorPaletteModel.getSecondaryColor());
        } else {
            if (SwingUtilities.isLeftMouseButton(event)) {
                if (mainView.isGradientLineSelected())
                    canvasView.addDrawable(new GradientLine(startPoint, currentPoint, colorPaletteModel.getPrimaryColor(), colorPaletteModel.getSecondaryColor()));
                else
                    canvasView.addDrawable(new Line(startPoint, currentPoint, colorPaletteModel.getPrimaryColor()));
            } else if (SwingUtilities.isRightMouseButton(event)) {
                if (mainView.isGradientLineSelected())
                    canvasView.addDrawable(new GradientLine(startPoint, currentPoint, colorPaletteModel.getSecondaryColor(), colorPaletteModel.getPrimaryColor()));
                else
                    canvasView.addDrawable(new Line(startPoint, currentPoint, colorPaletteModel.getSecondaryColor()));
            }
            startPoint = currentPoint = null;
            canvasView.clearTemporaryLine();
        }
    }

    private void polygonMode(MouseEvent event) {
        if (Math.abs(newPoint.getX() - startPoint.getX()) <= 5 && Math.abs(newPoint.getY() - startPoint.getY()) <= 5) {
            newPoint = startPoint;
            polygon.addPoint(newPoint);
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView.drawPolygonLine(currentPoint, startPoint, colorPaletteModel.getPrimaryColor());
            canvasView.clearPolygonStart();
            canvasView.addDrawable(polygon);
            startPoint = currentPoint = newPoint = null;
            toolbarModel.setLocked(false);
        } else {
            polygon.addPoint(newPoint);
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView.drawPolygonLine(currentPoint, newPoint, colorPaletteModel.getPrimaryColor());
            currentPoint = newPoint;
            canvasView.repaint();
        }
    }

    private void pencilMode(MouseEvent event) {
        if (pencilStroke == null) {
            if (SwingUtilities.isLeftMouseButton(event))
                pencilStroke = new PencilStroke(colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                pencilStroke = new PencilStroke(colorPaletteModel.getSecondaryColor());
        } else {
            canvasView.addDrawable(pencilStroke);
            pencilStroke = null;
        }
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
