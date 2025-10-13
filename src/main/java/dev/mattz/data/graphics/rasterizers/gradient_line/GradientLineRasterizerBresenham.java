
package dev.mattz.data.graphics.rasterizers.gradient_line;

import dev.mattz.data.graphics.drawable_objects.GradientLine;
import dev.mattz.data.graphics.drawable_objects.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GradientLineRasterizerBresenham implements GradientLineRasterizer {
    public GradientLineRasterizerBresenham() {
    }

    @Override
    public void draw(int x1, int y1, int x2, int y2, Color color1, Color color2, BufferedImage bufferedImage) {
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

    @Override
    public void draw(Point2D start, Point2D end, Color color1, Color color2, BufferedImage bufferedImage) {
        draw(start.getX(), start.getY(), end.getX(), end.getY(), color1, color2, bufferedImage);
    }

    @Override
    public void draw(GradientLine line, BufferedImage bufferedImage) {
        draw(line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor(), line.getColor2(), bufferedImage);
    }
}
