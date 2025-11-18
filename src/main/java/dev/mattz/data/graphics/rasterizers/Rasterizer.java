package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Drawable2D;

import java.awt.image.BufferedImage;

public interface Rasterizer {
    void draw(Drawable2D drawable2D, BufferedImage bufferedImage);
}
