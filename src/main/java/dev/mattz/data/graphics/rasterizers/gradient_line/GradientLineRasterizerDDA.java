package dev.mattz.data.graphics.rasterizers.gradient_line;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GradientLineRasterizerDDA implements GradientLineRasterizer {
    public GradientLineRasterizerDDA() {
    }

    @Override
    public void draw(int x1, int y1, int x2, int y2, Color color1, Color color2, BufferedImage bufferedImage) {
        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        if (steps == 0) {
            bufferedImage.setRGB(x1, y1, color1.getRGB());
            return;
        }

        float xIncrement = dx / (float) steps;
        float yIncrement = dy / (float) steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            float t = (float) i / steps;
            Color c = interpolateColor(color1, color2, t);

            int xi = Math.round(x);
            int yi = Math.round(y);

            if (xi >= 0 && xi < bufferedImage.getWidth() && yi >= 0 && yi < bufferedImage.getHeight()) {
                bufferedImage.setRGB(xi, yi, c.getRGB());
            }

            x += xIncrement;
            y += yIncrement;
        }
    }
}
