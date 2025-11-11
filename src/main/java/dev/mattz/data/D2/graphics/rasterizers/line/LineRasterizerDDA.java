package dev.mattz.data.D2.graphics.rasterizers.line;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRasterizerDDA implements LineRasterizer {
    public LineRasterizerDDA() {
    }

    @Override
    public void draw(int x1, int y1, int x2, int y2, Color color, BufferedImage bufferedImage) {
        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        if (steps == 0) {
            bufferedImage.setRGB(x1, y1, color.getRGB());
            return;
        }

        float xIncrement = dx / (float) steps;
        float yIncrement = dy / (float) steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            int xi = Math.round(x);
            int yi = Math.round(y);

            if (xi >= 0 && xi < bufferedImage.getWidth() && yi >= 0 && yi < bufferedImage.getHeight()) {
                bufferedImage.setRGB(xi, yi, color.getRGB());
            }
            x += xIncrement;
            y += yIncrement;
        }
    }
}
