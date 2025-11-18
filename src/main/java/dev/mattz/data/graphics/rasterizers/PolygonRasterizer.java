package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Drawable2D;
import dev.mattz.data.graphics.drawable_objects.Polygon;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizer;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizerBresenham;

import java.awt.image.BufferedImage;

public class PolygonRasterizer implements Rasterizer {
    LineRasterizer lineRasterizer = new LineRasterizerBresenham();

    @Override
    public void draw(Drawable2D drawable2D, BufferedImage bufferedImage) {
        Polygon polygon = (Polygon) drawable2D;
        for (int i = 0; i < polygon.size() - 1; i++) {
            lineRasterizer.draw(polygon.getPoint(i), polygon.getPoint(i + 1), polygon.getColor(), bufferedImage);
        }
    }
}
