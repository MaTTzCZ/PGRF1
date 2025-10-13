package dev.mattz.data.graphics;

import dev.mattz.data.graphics.drawable_objects.Line;
import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.graphics.drawable_objects.Polygon;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizer;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizerBresenham;
import dev.mattz.data.graphics.rasterizers.PencilRasterizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RasterImage implements Raster {
    private final BufferedImage bufferedImage;
    private final LineRasterizer lineRasterizer = new LineRasterizerBresenham();
    private final PencilRasterizer pencilRasterizer = new PencilRasterizer();

    private final ArrayList<Drawable> drawableObjects;

    private Polygon polygon = new Polygon();

    public RasterImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        drawableObjects = new ArrayList<>();
    }

    public void lineMode(Point2D start, Point2D end, Color color1, Color color2) {
        drawableObjects.add(new Line(start, end));
        lineRasterizer.draw(start, end, color1, color2, bufferedImage);
    }

    public void polygonMode(Point2D current, Point2D newPoint, Color color, boolean closing) {

    }

    public void pencilMode(Point2D point, Color color) {
        pencilRasterizer.draw(point.getX(), point.getY(), color, bufferedImage);
    }

    @Override
    public void setPixel(int x, int y, int color) {
        bufferedImage.setRGB(x, y, color);
    }

    @Override
    public int getPixel(int x, int y) {
        return bufferedImage.getRGB(x, y);
    }

    @Override
    public void clear() {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics.dispose();
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public void update(int newWidth, int newHeight) {

    }
}

