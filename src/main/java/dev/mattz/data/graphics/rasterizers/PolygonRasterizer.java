package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Polygon;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizer;
import dev.mattz.data.graphics.rasterizers.line.LineRasterizerBresenham;

import java.awt.image.BufferedImage;

public class PolygonRasterizer implements Rasterizer {
    LineRasterizer lineRasterizer = new LineRasterizerBresenham();

    @Override
    public void draw(Drawable drawable, BufferedImage bufferedImage) {
        Polygon polygon2D = (Polygon) drawable;
        for (int i = 0; i < polygon2D.size() - 1; i++) {
            lineRasterizer.draw(polygon2D.getPoint(i), polygon2D.getPoint(i + 1), polygon2D.getColor(), bufferedImage);
        }
    }
}
