package dev.mattz.data.graphics;

import dev.mattz.data.graphics.drawable_objects.Dash;
import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.graphics.drawable_objects.Polygon;
import dev.mattz.data.graphics.rasterizers.LineRasterizer;
import dev.mattz.data.graphics.rasterizers.LineRasterizerBresenham;
import dev.mattz.data.graphics.rasterizers.PencilRasterizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RasterizedBufferImage {
    private final BufferedImage bufferedImage;
    private final LineRasterizer lineRasterizer = new LineRasterizerBresenham();
    private final PencilRasterizer pencilRasterizer = new PencilRasterizer();

    private final ArrayList<Drawable> drawableObjects;

    private Polygon polygon = new Polygon();

    public RasterizedBufferImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        drawableObjects = new ArrayList<>();
    }

    public void lineMode(Point2D start, Point2D end, Color color1, Color color2) {
        drawableObjects.add(new Dash(start, end));
        lineRasterizer.draw(start, end, color1, color2, bufferedImage);
    }

    public void polygonMode(Point2D current, Point2D newPoint, Color color, boolean closing) {

    }

    public void pencilMode(Point2D point, Color color) {
        pencilRasterizer.draw(point.getX(), point.getY(), color, bufferedImage);
    }
}

