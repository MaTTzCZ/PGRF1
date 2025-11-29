package dev.mattz.data.graphics.rasterizers.fillers;

import dev.mattz.data.graphics.drawable_objects.Point;
import dev.mattz.data.graphics.rasterizers.Rasterizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public interface SeedFiller extends Rasterizer {
    default void spread(int x, int y, Color fillColor, BufferedImage bufferedImage, Stack<Point> pointsStack) {
        bufferedImage.setRGB(x, y, fillColor.getRGB());
        pointsStack.push(new Point(x - 1, y));
        pointsStack.push(new Point(x + 1, y));
        pointsStack.push(new Point(x, y - 1));
        pointsStack.push(new Point(x, y + 1));
    }
}
