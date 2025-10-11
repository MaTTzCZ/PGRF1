package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRasterizerBresenham implements LineRasterizer {
    public LineRasterizerBresenham() {

    }

    @Override
    public void draw(Point2D start, Point2D end, Color color1, Color color2, BufferedImage bufferedImage) {
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = end.getX();
        int y2 = end.getY();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;

        int steps = Math.max(dx, dy);
        int step = 0;

        while (true) {
            float t = steps == 0 ? 0f : (float) step / steps;
            Color c = interpolateColor(color1, color2, t);

            if (x1 >= 0 && x1 < bufferedImage.getWidth() && y1 >= 0 && y1 < bufferedImage.getHeight()) {
                bufferedImage.setRGB(x1, y1, c.getRGB());
            }

            if (x1 == x2 && y1 == y2)
                break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }

            step++;
        }
    }
}
