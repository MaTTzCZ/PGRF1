package dev.mattz.data.D2.graphics.rasterizers;

import dev.mattz.data.D2.graphics.drawable_objects.Drawable;

import java.awt.image.BufferedImage;

public interface Rasterizer {
    void draw(Drawable drawable, BufferedImage bufferedImage);
}
