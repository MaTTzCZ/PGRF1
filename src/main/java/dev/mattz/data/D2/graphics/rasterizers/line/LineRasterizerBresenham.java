package dev.mattz.data.D2.graphics.rasterizers.line;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRasterizerBresenham implements LineRasterizer {
    public LineRasterizerBresenham() {
    }

    @Override
    public void draw(int x1, int y1, int x2, int y2, Color color, BufferedImage bufferedImage) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;
        while (true) {
            if (x1 >= 0 && x1 < bufferedImage.getWidth() && y1 >= 0 && y1 < bufferedImage.getHeight()) {
                bufferedImage.setRGB(x1, y1, color.getRGB());
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
        }
    }
}

