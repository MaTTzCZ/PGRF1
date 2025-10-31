package dev.mattz.data.gui.views;

import dev.mattz.data.Mode;
import dev.mattz.data.graphics.drawable_objects.*;
import dev.mattz.data.graphics.drawable_objects.Polygon;
import dev.mattz.data.graphics.rasterizers.PencilRasterizer;
import dev.mattz.data.graphics.rasterizers.PolygonRasterizer;
import dev.mattz.data.graphics.rasterizers.fillers.SeedFillerBackground;
import dev.mattz.data.graphics.rasterizers.gradient_line.GradientLineRasterizer;
import dev.mattz.data.graphics.rasterizers.gradient_line.GradientLineRasterizerBresenham;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizer;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizerBresenham;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class CanvasView extends JPanel {
    private final BufferedImage bufferedImage;
    ArrayDeque<Drawable> drawables;
    Stack<Drawable> drawablesRedo;

    LineRasterizer lineRasterizer = new LineRasterizerBresenham();
    GradientLineRasterizer gradientLineRasterizer = new GradientLineRasterizerBresenham();
    PolygonRasterizer polygonRasterizer = new PolygonRasterizer();
    PencilRasterizer pencilRasterizer = new PencilRasterizer();
    SeedFillerBackground seedFillerBackground = new SeedFillerBackground();

    private Point2D lineStart, lineEnd, polygonStart;
    private Color tempColor1, tempColor2;

    private Mode currentMode = Mode.POINT_MOVE;

    public CanvasView(int width, int height) {
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        drawables = new ArrayDeque<>();
        drawablesRedo = new Stack<>();
        this.setPreferredSize(new Dimension(width, height));
        clearBufferedImage();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(bufferedImage, 0, 0, null);
        if (currentMode == Mode.POINT_MOVE) {
            drawAll();
            for (Drawable drawable : drawables) {
                if (!(drawable instanceof PencilStroke) && !(drawable instanceof FillerBackgroundBasePoint)) {
                    for (Point2D point : drawable.getAllPoints()) {
                        drawCircle(point.getX(), point.getY());
                    }
                }
            }
        } else if (lineStart != null && lineEnd != null) {
            drawAll();
            if (tempColor1 != null && tempColor2 == null)
                lineRasterizer.draw(lineStart, lineEnd, tempColor1, bufferedImage);
            else gradientLineRasterizer.draw(lineStart, lineEnd, tempColor1, tempColor2, bufferedImage);
        } else if (currentMode == Mode.POLYGON && polygonStart != null) {
            drawCircle(polygonStart.getX(), polygonStart.getY());
            repaint();
        }
        g2d.dispose();
    }

    public Queue<Drawable> getDrawables() {
        return drawables;
    }

    public void drawAll() {
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
            } else if (drawable instanceof FillerBackgroundBasePoint fillerBackgroundBasePoint) {
                seedFillerBackground.fill(
                        fillerBackgroundBasePoint.getX(),
                        fillerBackgroundBasePoint.getY(),
                        fillerBackgroundBasePoint.getBackgroundColor(),
                        fillerBackgroundBasePoint.getFillColor(),
                        bufferedImage
                );
            }
        }
        repaint();
    }

    public void seedFill(int x, int y, Color color) {
        drawables.add(new FillerBackgroundBasePoint(x, y, new Color(bufferedImage.getRGB(x, y)), color));
        seedFillerBackground.fill(x, y, new Color(bufferedImage.getRGB(x, y)), color, bufferedImage);
    }

    public Mode getCurrentMode() {
        return currentMode;
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

    public void drawLine(Point2D start, Point2D end, Color color) {
        lineRasterizer.draw(start, end, color, bufferedImage);
    }

    public void addDrawable(Drawable drawable) {
        drawables.addLast(drawable);
    }

    public void clearDrawables() {
        drawables.clear();
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
        if (!drawables.isEmpty()) {
            drawablesRedo.push(drawables.pollLast());
            drawAll();
        }
    }

    public void redo() {
        if (!drawablesRedo.isEmpty()) {
            drawables.addLast(drawablesRedo.pop());
            drawAll();
        }
    }

    public void setTemporaryLine(Point2D start, Point2D end, Color color) {
        this.lineStart = start;
        this.lineEnd = end;
        this.tempColor1 = color;
    }

    public void setTemporaryLine(Point2D start, Point2D end, Color color1, Color color2) {
        this.lineStart = start;
        this.lineEnd = end;
        this.tempColor1 = color1;
        this.tempColor2 = color2;
    }

    public void setPolygonStart(Point2D point) {
        this.polygonStart = point;
    }

    public void clearTemporaryLine() {
        this.lineStart = this.lineEnd = null;
        this.tempColor1 = this.tempColor2 = null;
    }

    public void clearPolygonStart() {
        polygonStart = null;
    }

    public void setRGB(int x, int y, Color color) {
        pencilRasterizer.draw(x, y, color, bufferedImage);
    }
}

