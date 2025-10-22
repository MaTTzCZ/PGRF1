package dev.mattz.data.gui.views;

import dev.mattz.data.Mode;
import dev.mattz.data.graphics.drawable_objects.*;
import dev.mattz.data.graphics.drawable_objects.Polygon;
import dev.mattz.data.graphics.rasterizers.PencilRasterizer;
import dev.mattz.data.graphics.rasterizers.PolygonRasterizer;
import dev.mattz.data.graphics.rasterizers.gradient_line.GradientLineRasterizer;
import dev.mattz.data.graphics.rasterizers.gradient_line.GradientLineRasterizerBresenham;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizer;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizerBresenham;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CanvasView extends JPanel {
    private final BufferedImage bufferedImage;
    private Mode currentMode = Mode.MOVE;

    LineRasterizer lineRasterizer = new LineRasterizerBresenham();
    GradientLineRasterizer gradientLineRasterizer = new GradientLineRasterizerBresenham();
    PolygonRasterizer polygonRasterizer = new PolygonRasterizer();
    PencilRasterizer pencilRasterizer = new PencilRasterizer();

    private Point2D tempStart, tempEnd, polygonStart;
    private Color tempColor;

    ArrayList<Drawable> drawables;
    
    public CanvasView(int width, int height) {
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        drawables = new ArrayList<>();
        this.setPreferredSize(new Dimension(width, height));
        clearBufferedImage();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(bufferedImage, 0, 0, null);
        if (currentMode == Mode.MOVE) {
            drawAll();
            for (Drawable drawable : drawables) {
                for (Point2D point : drawable.getAllPoints()) {
                    drawCircle(point.getX(), point.getY());
                }
            }
            repaint();
        } else if (currentMode == Mode.LINE && tempStart != null && tempEnd != null) {
            drawAll();
            lineRasterizer.draw(tempStart, tempEnd, tempColor, bufferedImage);
        } else if (currentMode == Mode.POLYGON && polygonStart != null) {
            drawCircle(polygonStart.getX(), polygonStart.getY());
            repaint();
        }


        g2d.dispose();
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    private void drawAll() {
        clearBufferedImage();
        for (Drawable drawable : drawables) {
            if (drawable instanceof GradientLine) {
                gradientLineRasterizer.draw(drawable, bufferedImage);
            } else if (drawable instanceof Line) {
                System.out.println("Vykreslení čáry");
                lineRasterizer.draw(drawable, bufferedImage);
            } else if (drawable instanceof Polygon) {
                polygonRasterizer.draw(drawable, bufferedImage);
            } else if (drawable instanceof PencilStroke) {
                pencilRasterizer.draw(drawable, bufferedImage);
            }
        }
        repaint();
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }

    public void drawCircle(int x, int y) {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.RED);
        int radius = 5;
        graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        graphics.dispose();
    }

    public void drawPolygonLine(Point2D start, Point2D end, Color color) {
        lineRasterizer.draw(start, end, color, bufferedImage);
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
        drawAll();
    }

    public void clearDrawables() {
        drawables.clear();
        repaint();
    }

    public void clearBufferedImage() {
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g2d.dispose();
    }

    public void setTemporaryLine(Point2D start, Point2D end, Color color) {
        this.tempStart = start;
        this.tempEnd = end;
        this.tempColor = color;
        repaint();
    }

    public void setPolygonStart(Point2D point) {
        this.polygonStart = point;
        repaint();
    }

    public void clearTemporaryLine() {
        this.tempStart = this.tempEnd = null;
        this.tempColor = null;
        drawAll();
    }

    public void clearPolygonStart() {
        polygonStart = null;
        repaint();
    }

    public void setRGB(int x, int y, Color color) {
        pencilRasterizer.draw(x, y, color, bufferedImage);
        repaint();
    }
}

