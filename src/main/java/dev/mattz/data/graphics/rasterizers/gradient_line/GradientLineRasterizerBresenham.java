package dev.mattz.data.graphics.rasterizers.gradient_line;

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
}
