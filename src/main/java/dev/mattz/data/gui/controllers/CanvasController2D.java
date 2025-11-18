package dev.mattz.data.gui.controllers;

import dev.mattz.data.graphics.drawable_objects.*;
import dev.mattz.data.graphics.drawable_objects.Polygon;
import dev.mattz.data.graphics.rasterizers.line_clipping.SutherlandHodgmanClipper;
import dev.mattz.data.gui.models.ColorPaletteModel;
import dev.mattz.data.gui.views.CanvasView2D;
import dev.mattz.data.gui.views.MainView2D;
import dev.mattz.data.gui.views.ToolbarView;

import javax.swing.*;
import java.awt.event.*;

public class CanvasController2D {
    private final MainView2D mainView2D;
    private final CanvasView2D canvasView2D;
    private final ToolbarView toolbarView;

    private final ColorPaletteModel colorPaletteModel;

    private final SutherlandHodgmanClipper sutherlandHodgmanClipper;

    private Point2D pointMoveModeSelectedPoint;
    private Point2D lineModeStartPoint, lineModeEndPoint;
    private Point2D polygonStartPoint, polygonCurrentPoint;
    private Point2D rectangleBaseStartPoint, rectangleBaseEndPoint, rectangleAltitude;

    private Polygon tempPolygon, tempIntersectionPolygon;
    private PencilStroke tempPencilStroke;
    private Rectangle tempRectangle;

    public CanvasController2D(MainView2D mainView2D, CanvasView2D canvasView2D, ToolbarView toolbarView) {
        this.mainView2D = mainView2D;
        this.canvasView2D = canvasView2D;
        this.toolbarView = toolbarView;
        canvasView2D.setFocusable(true);

        this.colorPaletteModel = ColorPaletteModel.getInstance();

        this.sutherlandHodgmanClipper = new SutherlandHodgmanClipper();

        canvasView2D.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                switch (canvasView2D.getCurrentMode()) {
                    case POINT_MOVE -> pointMoveModeMousePressed(event);
                    case LINE -> lineModeMousePressed(event);
                    case POLYGON -> polygonModeMousePressed(event);
                    case PENCIL_DRAW -> pencilDrawModeMousePressed(event);
                    case SEED_FILL -> seedFillModeMousePressed(event);
                    case RECTANGLE -> rectangleModeMousePressed(event);
                    case POLYGON_INTERSECTION -> polygonIntersectionModeMousePressed(event);
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                switch (canvasView2D.getCurrentMode()) {
                    case POINT_MOVE -> pointMoveModeMouseReleased();
                    case LINE -> lineModeMouseReleased(event);
                    case PENCIL_DRAW -> pencilDrawModeMouseReleased();
                    case RECTANGLE -> rectangleModeMouseReleased(event);
                }

            }
        });

        canvasView2D.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                switch (canvasView2D.getCurrentMode()) {
                    case POINT_MOVE -> pointMoveModeMouseDragged(event);
                    case LINE -> lineModeMouseDragged(event);
                    case PENCIL_DRAW -> pencilDrawModeMouseDragged(event);
                    case RECTANGLE -> rectangleModeMouseDragged(event);
                }
            }
        });

        canvasView2D.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                setKeyBinds(event);
            }
        });
    }

    //Point move mode
    private void pointMoveModeMousePressed(MouseEvent event) {
        for (Drawable2D drawable2D : canvasView2D.getDrawables()) {
            for (Point2D point : drawable2D.getAllPoints()) {
                if (Math.abs(event.getX() - point.getX()) <= 5 && Math.abs(event.getY() - point.getY()) <= 5) {
                    pointMoveModeSelectedPoint = point;
                    return;
                }
            }
        }
    }

    private void pointMoveModeMouseDragged(MouseEvent event) {
        if (pointMoveModeSelectedPoint != null) pointMoveModeSelectedPoint.setPosition(event.getX(), event.getY());

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
        if (mainView2D.isGradientLineSelected()) {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView2D.setTemporaryLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getPrimaryColor(), colorPaletteModel.getSecondaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView2D.setTemporaryLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getSecondaryColor(), colorPaletteModel.getPrimaryColor());
        } else {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView2D.setTemporaryLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView2D.setTemporaryLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getSecondaryColor());
        }
        canvasView2D.repaint();
    }

    private void lineModeMouseReleased(MouseEvent event) {
        if (mainView2D.isGradientLineSelected()) {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView2D.addDrawable(new GradientLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getPrimaryColor(), colorPaletteModel.getSecondaryColor()));
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView2D.addDrawable(new GradientLine(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getSecondaryColor(), colorPaletteModel.getPrimaryColor()));
        } else {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView2D.addDrawable(new Line(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getPrimaryColor()));
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView2D.addDrawable(new Line(lineModeStartPoint, lineModeEndPoint, colorPaletteModel.getSecondaryColor()));
        }
        lineModeStartPoint = lineModeEndPoint = null;
        canvasView2D.clearTemporaryLine();
    }

    //Polygon mode
    private void polygonModeMousePressed(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            toolbarView.setLocked(true);
            if (polygonStartPoint == null) {
                if (mainView2D.isPolygonFillSelected())
                    tempPolygon = new FilledPolygon(colorPaletteModel.getPrimaryColor());
                else tempPolygon = new Polygon(colorPaletteModel.getPrimaryColor());
                polygonStartPoint = polygonCurrentPoint = new Point2D(event.getX(), event.getY());
                tempPolygon.addPoint(polygonStartPoint);
                canvasView2D.setPolygonStart(polygonStartPoint);
            } else {
                Point2D polygonModeNextPoint = new Point2D(event.getX(), event.getY());
                if (Math.abs(polygonModeNextPoint.getX() - polygonStartPoint.getX()) <= 5 && Math.abs(polygonModeNextPoint.getY() - polygonStartPoint.getY()) <= 5) {
                    if (tempPolygon.size() > 2) {
                        polygonModeNextPoint = polygonStartPoint;
                        tempPolygon.addPoint(polygonModeNextPoint);
                        canvasView2D.drawLine(polygonCurrentPoint, polygonStartPoint, colorPaletteModel.getPrimaryColor());
                        canvasView2D.addDrawable(tempPolygon);
                    }
                    canvasView2D.clearPolygonStart();
                    polygonStartPoint = polygonCurrentPoint = null;
                    toolbarView.setLocked(false);
                    canvasView2D.drawAll();
                } else {
                    tempPolygon.addPoint(polygonModeNextPoint);
                    canvasView2D.drawLine(polygonCurrentPoint, polygonModeNextPoint, colorPaletteModel.getPrimaryColor());
                    polygonCurrentPoint = polygonModeNextPoint;
                }
            }
        }
        canvasView2D.repaint();
    }

    //Pencil draw mode
    private void pencilDrawModeMousePressed(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event))
            tempPencilStroke = new PencilStroke(colorPaletteModel.getPrimaryColor());
        else if (SwingUtilities.isRightMouseButton(event))
            tempPencilStroke = new PencilStroke(colorPaletteModel.getSecondaryColor());
    }

    private void pencilDrawModeMouseDragged(MouseEvent event) {
        canvasView2D.setRGB(event.getX(), event.getY(), SwingUtilities.isLeftMouseButton(event) ? colorPaletteModel.getPrimaryColor() : colorPaletteModel.getSecondaryColor());
        tempPencilStroke.addPoint(new Point2D(event.getX(), event.getY()));
        canvasView2D.repaint();
    }

    private void pencilDrawModeMouseReleased() {
        canvasView2D.addDrawable(tempPencilStroke);
        tempPencilStroke = null;
    }

    //Seed fill mode
    private void seedFillModeMousePressed(MouseEvent event) {
        if (mainView2D.isSeedFillBackgroundSelected()) {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView2D.seedFillBackground(event.getX(), event.getY(), colorPaletteModel.getPrimaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView2D.seedFillBackground(event.getX(), event.getY(), colorPaletteModel.getSecondaryColor());
            canvasView2D.repaint();
        } else {
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView2D.seedFillBoundary(event.getX(), event.getY(), colorPaletteModel.getPrimaryColor(), colorPaletteModel.getSecondaryColor());
            else if (SwingUtilities.isRightMouseButton(event))
                canvasView2D.seedFillBoundary(event.getX(), event.getY(), colorPaletteModel.getSecondaryColor(), colorPaletteModel.getPrimaryColor());
            canvasView2D.repaint();
        }
    }

    //Rectangle mode
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
                canvasView2D.clearTemporaryLine();
                canvasView2D.addDrawable(tempRectangle);
                tempRectangle = null;
                rectangleBaseStartPoint = rectangleBaseEndPoint = rectangleAltitude = null;
                toolbarView.setLocked(false);
                canvasView2D.drawAll();
            }
        }
    }

    private void rectangleModeMouseDragged(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            rectangleBaseEndPoint = event.isShiftDown() ? snapToFixedAngle(rectangleBaseStartPoint, new Point2D(event.getX(), event.getY())) : new Point2D(event.getX(), event.getY());
            if (SwingUtilities.isLeftMouseButton(event))
                canvasView2D.setTemporaryLine(rectangleBaseStartPoint, rectangleBaseEndPoint, colorPaletteModel.getPrimaryColor());
            canvasView2D.repaint();
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

    //Polygon Intersection mode
    private void polygonIntersectionModeMousePressed(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            toolbarView.setLocked(true);
            if (polygonStartPoint == null) {
                tempPolygon = new Polygon(colorPaletteModel.getPrimaryColor());
                polygonStartPoint = polygonCurrentPoint = new Point2D(event.getX(), event.getY());
                tempPolygon.addPoint(polygonStartPoint);
                canvasView2D.setPolygonStart(polygonStartPoint);
            } else {
                Point2D polygonModeNextPoint = new Point2D(event.getX(), event.getY());
                if (Math.abs(polygonModeNextPoint.getX() - polygonStartPoint.getX()) <= 5 && Math.abs(polygonModeNextPoint.getY() - polygonStartPoint.getY()) <= 5) {
                    if (tempPolygon.size() > 2) {
                        polygonModeNextPoint = polygonStartPoint;
                        tempPolygon.addPoint(polygonModeNextPoint);
                        canvasView2D.drawLine(polygonCurrentPoint, polygonStartPoint, colorPaletteModel.getPrimaryColor());
                        canvasView2D.addDrawable(tempPolygon);
                        if (tempIntersectionPolygon == null)
                            tempIntersectionPolygon = tempPolygon;
                        else {
                            FilledPolygon filledPolygon = sutherlandHodgmanClipper.intersect(tempIntersectionPolygon, tempPolygon);
                            canvasView2D.addPolygonIntersection(filledPolygon);
                            tempIntersectionPolygon = null;
                        }
                    }
                    canvasView2D.clearPolygonStart();
                    polygonStartPoint = polygonCurrentPoint = null;
                    toolbarView.setLocked(false);
                    canvasView2D.drawAll();
                } else {
                    tempPolygon.addPoint(polygonModeNextPoint);
                    canvasView2D.drawLine(polygonCurrentPoint, polygonModeNextPoint, colorPaletteModel.getPrimaryColor());
                    polygonCurrentPoint = polygonModeNextPoint;
                }
            }
            canvasView2D.repaint();
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

    private void setKeyBinds(KeyEvent event) {
        if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_Z) {
            canvasView2D.undo();
        } else if (event.isControlDown() && event.getKeyCode() == KeyEvent.VK_Y) {
            canvasView2D.redo();
        } else if (event.getKeyCode() == KeyEvent.VK_C) {
            canvasView2D.clearDrawables();
            canvasView2D.clearBufferedImage();
        }
    }
}
