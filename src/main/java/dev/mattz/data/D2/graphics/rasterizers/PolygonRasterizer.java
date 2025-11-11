package dev.mattz.data.D2.graphics.rasterizers;

import dev.mattz.data.D2.graphics.drawable_objects.Drawable;
import dev.mattz.data.D2.graphics.drawable_objects.Polygon;
import dev.mattz.data.D2.graphics.rasterizers.line.LineRasterizer;
import dev.mattz.data.D2.graphics.rasterizers.line.LineRasterizerBresenham;

import java.awt.image.BufferedImage;

public class PolygonRasterizer implements Rasterizer {
    LineRasterizer lineRasterizer = new LineRasterizerBresenham();

    @Override
    public void draw(Drawable drawable, BufferedImage bufferedImage) {
        Polygon polygon = (Polygon) drawable;
        for (int i = 0; i < polygon.size() - 1; i++) {
            lineRasterizer.draw(polygon.getPoint(i), polygon.getPoint(i + 1), polygon.getColor(), bufferedImage);
        }
    }
}
