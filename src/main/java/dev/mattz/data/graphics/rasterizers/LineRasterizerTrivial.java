package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRasterizerTrivial implements LineRasterizer {
    @Override
    public void draw(Point2D start, Point2D end, Color color1, Color color2, BufferedImage bufferedImage) {
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = end.getX();
        int y2 = end.getY();

        double k = (double) (y2 - y1) / (x2 - x1);
        double q = y1 - k * x1;

        int xStart = Math.min(x1, x2);
        int xEnd = Math.max(x1, x2);
        int length = xEnd - xStart;

        for (int i = 0; i <= length; i++) {
            float t = (float) i / length;
            Color c = interpolateColor(color1, color2, t);
            int x = xStart + i;
            int y = (int) Math.round(k * x + q);
            if (x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight())
                bufferedImage.setRGB(x, y, c.getRGB());
        }
    }
}