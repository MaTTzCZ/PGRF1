package dev.mattz.data.gui.controllers;

import dev.mattz.data.graphics.drawable_objects.*;
import dev.mattz.data.graphics.drawable_objects.Polygon;
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

    private Point2D pointMoveModeSelectedPoint;
    private Point2D lineModeStartPoint, lineModeEndPoint;
    private Point2D polygonModeStartPoint, polygonModeCurrentPoint;
    private Polygon tempPolygon;
    private PencilStroke tempPencilStroke;

    public CanvasController(CanvasView canvasView, MainView mainView) {
        this.canvasView = canvasView;
        this.mainView = mainView;
        canvasView.setFocusable(true);

        this.colorPaletteModel = ColorPaletteModel.getInstance();
        this.toolbarModel = ToolbarModel.getInstance();

        canvasView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                switch (toolbarModel.getCurrentMode()) {
                    case POINT_MOVE -> pointMoveModeMousePressed(event);
                    case LINE -> lineModeMousePressed(event);
                    case POLYGON -> polygonModeMousePressed(event);
                    case PENCIL_DRAW -> pencilModeMousePressed(event);
                    case SEED_FILL -> seedFillModeMousePressed(event);
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                switch (toolbarModel.getCurrentMode()) {
                    case POINT_MOVE -> pointMoveModeMouseReleased();
                    case LINE -> lineModeMouseReleased(event);
                    case PENCIL_DRAW -> pencilModeMouseReleased();
                }

            }
        });

        canvasView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                switch (toolbarModel.getCurrentMode()) {
                    case POINT_MOVE -> pointMoveModeMouseDragged(event);
                    case LINE -> lineModeMouseDragged(event);
                    case PENCIL_DRAW -> pencilModeMouseDragged(event);
                }
            }
        });

        canvasView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                setKeyBinds(event);
            }
        });
    }

    //Point move mode
    private void pointMoveModeMousePressed(MouseEvent event) {
        for (Drawable drawable : canvasView.getDrawables()) {
            for (Point2D point : drawable.getAllPoints()) {
                if (Math.abs(event.getX() - point.getX()) <= 5 && Math.abs(event.getY() - point.getY()) <= 5) {
                    pointMoveModeSelectedPoint = point;
                    return;
                }
            }
        }
    }

    private void pointMoveModeMouseDragged(MouseEvent event) {
        if (pointMoveModeSelectedPoint != null)
            pointMoveModeSelectedPoint.setPosition(event.getX(), event.getY());

    }

    private void pointMoveModeMouseReleased() {
        pointMoveModeSelectedPoint = null;
    }


    //Line mode
    private void lineModeMousePressed(MouseEvent event) {
        lineModeStartPoint = new Point2D(event.getX(), event.getY());
    }

    private void lineModeMouseDragged(MouseEvent event) {
        lineModeEndPoint = event.isShiftDown() ? snapToFixedAngle(lineModeStartPoint, new Point2D(event.getX(), event.getY())) : new Point2D(event.getX(), event.getY());

        if (mainView.isGradientLineSelected()) {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView.setTemporaryLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getPrimaryColor(), colorPaletteModel.getSecondaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView.setTemporaryLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getSecondaryColor(), colorPaletteModel.getPrimaryColor());
        } else {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView.setTemporaryLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView.setTemporaryLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getSecondaryColor());
        }
    }

    private void lineModeMouseReleased(MouseEvent event) {
        if (mainView.isGradientLineSelected()) {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView.addDrawable(new GradientLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getPrimaryColor(), colorPaletteModel.getSecondaryColor()));
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView.addDrawable(new GradientLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getSecondaryColor(), colorPaletteModel.getPrimaryColor()));
        } else {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView.addDrawable(new Line(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getPrimaryColor()));
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView.addDrawable(new Line(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getSecondaryColor()));
        }
        lineModeStartPoint = lineModeEndPoint = null;
        canvasView.clearTemporaryLine();
    }

    //Polygon mode
    private void polygonModeMousePressed(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            toolbarModel.setLocked(true);
            if (polygonModeStartPoint == null) {
                tempPolygon = new Polygon(colorPaletteModel.getPrimaryColor());
                polygonModeStartPoint = polygonModeCurrentPoint = new Point2D(event.getX(), event.getY());
                tempPolygon.addPoint(polygonModeStartPoint);
                canvasView.setPolygonStart(polygonModeStartPoint);
            } else {
                Point2D polygonModeNextPoint = new Point2D(event.getX(), event.getY());
                if (Math.abs(polygonModeNextPoint.getX() - polygonModeStartPoint.getX()) <= 5 && Math.abs(polygonModeNextPoint.getY() - polygonModeStartPoint.getY()) <= 5) {
                    polygonModeNextPoint = polygonModeStartPoint;
                    tempPolygon.addPoint(polygonModeNextPoint);
                    if (SwingUtilities.isLeftMouseButton(event))
                        canvasView.drawPolygonLine(polygonModeCurrentPoint, polygonModeStartPoint, colorPaletteModel.getPrimaryColor());
                    canvasView.clearPolygonStart();
                    canvasView.addDrawable(tempPolygon);
                    polygonModeStartPoint = polygonModeCurrentPoint = null;
                    toolbarModel.setLocked(false);
                } else {
                    tempPolygon.addPoint(polygonModeNextPoint);
                    if (SwingUtilities.isLeftMouseButton(event))
                        canvasView.drawPolygonLine(polygonModeCurrentPoint, polygonModeNextPoint, colorPaletteModel.getPrimaryColor());
                    polygonModeCurrentPoint = polygonModeNextPoint;
                }
            }
            canvasView.repaint();
        }
    }

    //Pencil draw mode
    private void pencilModeMousePressed(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event))
            tempPencilStroke = new PencilStroke(colorPaletteModel.getPrimaryColor());
        else if (SwingUtilities.isRightMouseButton(event))
            tempPencilStroke = new PencilStroke(colorPaletteModel.getSecondaryColor());
    }

    private void pencilModeMouseDragged(MouseEvent event) {
        canvasView.setRGB(event.getX(), event.getY(), SwingUtilities.isLeftMouseButton(event) ? colorPaletteModel.getPrimaryColor() : colorPaletteModel.getSecondaryColor());
        tempPencilStroke.addPoint(new Point2D(event.getX(), event.getY()));
    }

    private void pencilModeMouseReleased() {
        canvasView.addDrawable(tempPencilStroke);
        tempPencilStroke = null;
    }

    //Seed fill mode
    private void seedFillModeMousePressed(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event))
            canvasView.seedFill(event.getX(), event.getY(), colorPaletteModel.getPrimaryColor());
        else if (SwingUtilities.isRightMouseButton(event))
            canvasView.seedFill(event.getX(), event.getY(), colorPaletteModel.getSecondaryColor());
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

    private void setKeyBinds(KeyEvent event) {
        if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_Z) {
            canvasView.undo();
        } else if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_Y) {
            canvasView.redo();
        } else if (event.getKeyCode() == KeyEvent.VK_C) {
            canvasView.clearDrawables();
            canvasView.clearBufferedImage();
        }
    }
}
