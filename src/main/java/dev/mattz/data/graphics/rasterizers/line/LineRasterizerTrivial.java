package dev.mattz.data.graphics.rasterizers.line;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRasterizerTrivial implements LineRasterizer {
    public LineRasterizerTrivial() {
    }

    @Override
    public void draw(int x1, int y1, int x2, int y2, Color color, BufferedImage bufferedImage) {
        double k = (double) (y2 - y1) / (x2 - x1);
        double q = y1 - k * x1;

        int xStart = Math.min(x1, x2);
        int xEnd = Math.max(x1, x2);
        int length = xEnd - xStart;

        for (int i = 0; i <= length; i++) {
            int x = xStart + i;
            int y = (int) Math.round(k * x + q);
            if (x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight())
                bufferedImage.setRGB(x, y, color.getRGB());
        }
    }
}