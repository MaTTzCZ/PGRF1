package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Drawable;

import java.awt.image.BufferedImage;

public interface Rasterizer {
    void draw(Drawable drawable, BufferedImage bufferedImage);
}
