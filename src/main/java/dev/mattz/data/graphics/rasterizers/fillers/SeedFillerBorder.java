package dev.mattz.data.graphics.rasterizers.fillers;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.rasterizers.PencilRasterizer;
import dev.mattz.data.graphics.rasterizers.Rasterizer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SeedFillerBorder implements Rasterizer {
    PencilRasterizer pencilRasterizer;

    public SeedFillerBorder() {
        pencilRasterizer = new PencilRasterizer();
    }

    public void fillByBackground(int x, int y, Color backgroundColor, Color fillColor, BufferedImage bufferedImage) {
        if (x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight()){
            if (bufferedImage.getRGB(x, y) != backgroundColor.getRGB() || backgroundColor == fillColor) return;
            bufferedImage.setRGB(x, y, fillColor.getRGB());
            fillByBackground(x - 1, y, backgroundColor, fillColor, bufferedImage);
            fillByBackground(x + 1, y, backgroundColor, fillColor, bufferedImage);
            fillByBackground(x, y - 1, backgroundColor, fillColor, bufferedImage);
            fillByBackground(x, y + 1, backgroundColor, fillColor, bufferedImage);
        }
    }

    @Override
    public void draw(Drawable drawable, BufferedImage bufferedImage) {

    }
}
