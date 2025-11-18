package dev.mattz.data.gui.views;

import dev.mattz.data.Mode3D;
import dev.mattz.data.graphics.drawable_objects.*;
import dev.mattz.data.graphics.rasterizers.FilledPolygonRasterizer;
import dev.mattz.data.graphics.rasterizers.PencilRasterizer;
import dev.mattz.data.graphics.rasterizers.PolygonRasterizer;
import dev.mattz.data.graphics.rasterizers.fillers.SeedFillerBackground;
import dev.mattz.data.graphics.rasterizers.fillers.SeedFillerBoundary;
import dev.mattz.data.graphics.rasterizers.gradient_line.GradientLineRasterizer;
import dev.mattz.data.graphics.rasterizers.gradient_line.GradientLineRasterizerBresenham;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizer;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizerBresenham;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Stack;

public class CanvasView3D extends JPanel {
    private final BufferedImage bufferedImage;
    ArrayDeque<Drawable2D> drawable2DS;
    Stack<Drawable2D> drawablesRedo;

    LineRasterizer lineRasterizer = new LineRasterizerBresenham();
    GradientLineRasterizer gradientLineRasterizer = new GradientLineRasterizerBresenham();
    PolygonRasterizer polygonRasterizer = new PolygonRasterizer();
    FilledPolygonRasterizer filledPolygonRasterizer = new FilledPolygonRasterizer();
    PencilRasterizer pencilRasterizer = new PencilRasterizer();
    SeedFillerBackground seedFillerBackground = new SeedFillerBackground();
    SeedFillerBoundary seedFillerBoundary = new SeedFillerBoundary();

    private Point2D lineStart, lineEnd, polygonStart;
    private Color tempColorPrimary, tempColorSecondary;

    private Mode3D currentMode = Mode3D.MOVEMENT;

    public CanvasView3D(int width, int height) {
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        drawable2DS = new ArrayDeque<>();
        drawablesRedo = new Stack<>();
        this.setPreferredSize(new Dimension(width, height));
        clearBufferedImage();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
    }

    public ArrayDeque<Drawable2D> getDrawables() {
        return drawable2DS;
    }

    public void drawAll() {
        clearBufferedImage();
        for (Drawable2D drawable2D : drawable2DS) {
        }
        repaint();
    }

    public void seedFillBackground(int x, int y, Color fillColor) {
        drawable2DS.add(new SeedFillerBackgroundBasePoint(x, y, fillColor, new Color(bufferedImage.getRGB(x, y))));
        seedFillerBackground.fill(x, y, fillColor, new Color(bufferedImage.getRGB(x, y)), bufferedImage);
    }

    public void seedFillBoundary(int x, int y, Color fillColor, Color boundaryColor) {
        drawable2DS.add(new SeedFillerBoundaryBasePoint(x, y, fillColor, boundaryColor));
        seedFillerBoundary.fill(x, y, fillColor, boundaryColor, bufferedImage);
    }

    public Mode3D getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode3D currentMode) {
        this.currentMode = currentMode;
    }

    public void drawCircle(int x, int y) {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.RED);
        int radius = 5;
        graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        graphics.dispose();
    }

    public void drawLine(Point2D start, Point2D end, Color color) {
        lineRasterizer.draw(start, end, color, bufferedImage);
    }

    public void addDrawable(Drawable2D drawable2D) {
        drawable2DS.add(drawable2D);
    }

    public void addPolygonIntersection(FilledPolygon filledPolygon) {
        drawable2DS.pollLast();
        drawable2DS.pollLast();
        drawable2DS.add(filledPolygon);
    }

    public void clearDrawables() {
        drawable2DS.clear();
        drawablesRedo.clear();
        repaint();
    }

    public void clearBufferedImage() {
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g2d.dispose();
    }

    public void undo() {
        if (!drawable2DS.isEmpty()) {
            drawablesRedo.push(drawable2DS.pollLast());
            drawAll();
        }
    }

    public void redo() {
        if (!drawablesRedo.isEmpty()) {
            drawable2DS.addLast(drawablesRedo.pop());
            drawAll();
        }
    }

    public void setTemporaryLine(Point2D start, Point2D end, Color color) {
        this.lineStart = start;
        this.lineEnd = end;
        this.tempColorPrimary = color;
    }

    public void setTemporaryLine(Point2D start, Point2D end, Color color1, Color color2) {
        this.lineStart = start;
        this.lineEnd = end;
        this.tempColorPrimary = color1;
        this.tempColorSecondary = color2;
    }

    public void setPolygonStart(Point2D point) {
        this.polygonStart = point;
    }

    public void clearTemporaryLine() {
        this.lineStart = this.lineEnd = null;
        this.tempColorPrimary = this.tempColorSecondary = null;
    }

    public void clearPolygonStart() {
        polygonStart = null;
    }

    public void setRGB(int x, int y, Color color) {
        pencilRasterizer.draw(x, y, color, bufferedImage);
    }
}

