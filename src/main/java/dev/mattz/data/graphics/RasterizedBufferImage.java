package dev.mattz.data.graphics;

import java.awt.image.BufferedImage;

public class RasterizedBufferImage  extends BufferedImage {
    public RasterizedBufferImage(int width, int height) {
        super(width, height, TYPE_INT_RGB);
    }
}
