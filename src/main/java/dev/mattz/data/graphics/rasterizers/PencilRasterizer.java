package dev.mattz.data.graphics.rasterizers;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PencilRasterizer {
    public void draw(int x, int y, Color color, BufferedImage image) {
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) image.setRGB(x, y, color.getRGB());
    }
}
