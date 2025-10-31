package dev.mattz.data.gui.controllers;

import dev.mattz.data.graphics.drawable_objects.*;
import dev.mattz.data.graphics.drawable_objects.Polygon;
import dev.mattz.data.gui.models.ColorPaletteModel;
import dev.mattz.data.gui.views.CanvasView;
import dev.mattz.data.gui.views.MainView;
import dev.mattz.data.gui.views.ToolbarView;

import javax.swing.*;
import java.awt.event.*;

public class CanvasController {
    private final MainView mainView;
    private final CanvasView canvasView;
    private final ToolbarView toolbarView;

    private final ColorPaletteModel colorPaletteModel;

    private Point2D pointMoveModeSelectedPoint;
    private Point2D lineModeStartPoint, lineModeEndPoint;
    private Point2D polygonModeStartPoint, polygonModeCurrentPoint;
    private Point2D rectangleBaseStartPoint, rectangleBaseEndPoint, rectangleAltitude;

    private Polygon tempPolygon;
    private PencilStroke tempPencilStroke;
    private Rectangle tempRectangle;

    public CanvasController(MainView mainView, CanvasView canvasView, ToolbarView toolbarView) {
        this.mainView = mainView;
        this.canvasView = canvasView;
        this.toolbarView = toolbarView;
        canvasView.setFocusable(true);

        this.colorPaletteModel = ColorPaletteModel.getInstance();

        canvasView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                switch (canvasView.getCurrentMode()) {
                    case POINT_MOVE -> pointMoveModeMousePressed(event);
                    case LINE -> lineModeMousePressed(event);
                    case POLYGON -> polygonModeMousePressed(event);
                    case PENCIL_DRAW -> pencilModeMousePressed(event);
                    case SEED_FILL -> seedFillModeMousePressed(event);
                    case RECTANGLE -> rectangleModeMousePressed(event);
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                switch (canvasView.getCurrentMode()) {
                    case POINT_MOVE -> pointMoveModeMouseReleased();
                    case LINE -> lineModeMouseReleased(event);
                    case PENCIL_DRAW -> pencilModeMouseReleased();
                    case RECTANGLE -> rectangleModeMouseReleased(event);
                }

            }
        });

        canvasView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                switch (canvasView.getCurrentMode()) {
                    case POINT_MOVE -> pointMoveModeMouseDragged(event);
                    case LINE -> lineModeMouseDragged(event);
                    case PENCIL_DRAW -> pencilModeMouseDragged(event);
                    case RECTANGLE -> rectangleModeMouseDragged(event);
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
        canvasView.repaint();
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
            toolbarView.setLocked(true);
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
                        canvasView.drawLine(polygonModeCurrentPoint, polygonModeStartPoint, colorPaletteModel.getPrimaryColor());
                    canvasView.clearPolygonStart();
                    canvasView.addDrawable(tempPolygon);
                    polygonModeStartPoint = polygonModeCurrentPoint = null;
                    toolbarView.setLocked(false);
                    canvasView.drawAll();
                } else {
                    tempPolygon.addPoint(polygonModeNextPoint);
                    if (SwingUtilities.isLeftMouseButton(event))
                        canvasView.drawLine(polygonModeCurrentPoint, polygonModeNextPoint, colorPaletteModel.getPrimaryColor());
                    polygonModeCurrentPoint = polygonModeNextPoint;
                }
            }
        }
        canvasView.repaint();
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
        canvasView.repaint();
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
        canvasView.repaint();
    }

    //Line mode
    private void rectangleModeMousePressed(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            toolbarView.setLocked(true);
            if (rectangleBaseStartPoint == null) {
                tempRectangle = new Rectangle(colorPaletteModel.getPrimaryColor());
                rectangleBaseStartPoint = new Point2D(event.getX(), event.getY());
                tempRectangle.addPoint(rectangleBaseStartPoint);
            } else {
                rectangleAltitude = new Point2D(event.getX(), event.getY());
                computeRectangle();
                canvasView.clearTemporaryLine();
                canvasView.addDrawable(tempRectangle);
                tempRectangle = null;
                rectangleBaseStartPoint = rectangleBaseEndPoint = rectangleAltitude = null;
                toolbarView.setLocked(false);
                canvasView.drawAll();
            }
        }
    }

    private void rectangleModeMouseDragged(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            rectangleBaseEndPoint = event.isShiftDown() ? snapToFixedAngle(rectangleBaseStartPoint, new Point2D(event.getX(), event.getY())) : new Point2D(event.getX(), event.getY());
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView.setTemporaryLine(rectangleBaseStartPoint, rectangleBaseEndPoint, colorPaletteModel.getPrimaryColor());
            canvasView.repaint();
        }
    }

    private void rectangleModeMouseReleased(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            if (tempRectangle != null) {
                Point2D point = event.isShiftDown() ? snapToFixedAngle(rectangleBaseStartPoint, new Point2D(event.getX(), event.getY())) : new Point2D(event.getX(), event.getY());
                tempRectangle.addPoint(point);
            }
        }
    }

    private void computeRectangle() {
        double ABx = rectangleBaseEndPoint.getX() - rectangleBaseStartPoint.getX();
        double ABy = rectangleBaseEndPoint.getY() - rectangleBaseStartPoint.getY();
        double ACx = rectangleAltitude.getX() - rectangleBaseStartPoint.getX();
        double ACy = rectangleAltitude.getY() - rectangleBaseStartPoint.getY();

        double lenAB = Math.hypot(ABx, ABy);
        double ux = ABx / lenAB;
        double uy = ABy / lenAB;

        double dot = ACx * ux + ACy * uy;
        double projX = dot * ux;
        double projY = dot * uy;

        double hx = ACx - projX;
        double hy = ACy - projY;

        tempRectangle.addPoint(new Point2D((int) (rectangleBaseEndPoint.getX() + hx), (int) (rectangleBaseEndPoint.getY() + hy)));
        tempRectangle.addPoint(new Point2D((int) (rectangleBaseStartPoint.getX() + hx), (int) (rectangleBaseStartPoint.getY() + hy)));
        tempRectangle.addPoint(rectangleBaseStartPoint);
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
