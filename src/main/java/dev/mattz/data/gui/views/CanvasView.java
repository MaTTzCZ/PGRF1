package dev.mattz.data.gui.views;

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

    LineRasterizer lineRasterizer = new LineRasterizerBresenham();
    GradientLineRasterizer gradientLineRasterizer = new GradientLineRasterizerBresenham();
    PolygonRasterizer polygonRasterizer = new PolygonRasterizer();
    PencilRasterizer pencilRasterizer = new PencilRasterizer();

    private Point2D tempStart, tempEnd, polygonStart;
    private Color tempColor;

    ArrayList<Drawable> drawables;

    public CanvasView(int width, int height) {
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        drawables = new ArrayList<>();
        this.setPreferredSize(new Dimension(width, height));
        clearBufferedImage();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(bufferedImage, 0, 0, null);
        if (tempStart != null && tempEnd != null) {
            drawAll();
            lineRasterizer.draw(tempStart, tempEnd, tempColor, bufferedImage);
        }

        //Polygon start point
        if (polygonStart != null) {
            graphics.setColor(Color.RED);
            int radius = 5;
            g2d.fillOval(polygonStart.x() - radius, polygonStart.y() - radius, radius * 2, radius * 2);
        }
        g2d.dispose();
    }

    private void drawAll() {
        clearBufferedImage();
        for (Drawable drawable : drawables) {
            if (drawable instanceof GradientLine) {
                gradientLineRasterizer.draw(drawable, bufferedImage);
            } else if (drawable instanceof Line) {
                lineRasterizer.draw(drawable, bufferedImage);
            } else if (drawable instanceof Polygon) {
                polygonRasterizer.draw(drawable, bufferedImage);
            } else if (drawable instanceof PencilStroke) {
                pencilRasterizer.draw(drawable, bufferedImage);
            }
        }
    }

    public void drawPolygonLine(Point2D start, Point2D end, Color color) {
        lineRasterizer.draw(start, end, color, bufferedImage);
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
        repaint();
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

