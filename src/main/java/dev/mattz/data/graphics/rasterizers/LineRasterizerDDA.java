package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRasterizerDDA implements LineRasterizer {
    public LineRasterizerDDA() {
    }

    @Override
    public void draw(Point2D start, Point2D end, Color color1, Color color2, BufferedImage bufferedImage) {
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = end.getX();
        int y2 = end.getY();

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
