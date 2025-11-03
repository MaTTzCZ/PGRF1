package dev.mattz.data.graphics.rasterizers.fillers;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.graphics.drawable_objects.SeedFillerBackgroundBasePoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class SeedFillerBoundary implements SeedFiller {

    public SeedFillerBoundary() {
    }

    public void fill(int x, int y, Color fillColor, Color boundaryColor, BufferedImage bufferedImage) {
        Stack<Point2D> pointsStack = new Stack<>();
        pointsStack.add(new Point2D(x, y));
        while (!pointsStack.empty()) {
            Point2D currentPoint = pointsStack.pop();
            x = currentPoint.getX();
            y = currentPoint.getY();
            if (x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight()) {
                if (bufferedImage.getRGB(x, y) != boundaryColor.getRGB() && bufferedImage.getRGB(x, y) != fillColor.getRGB()) {
                    spread(x, y, fillColor, bufferedImage, pointsStack);
                }
            }
        }
    }

    @Override
    public void draw(Drawable drawable, BufferedImage bufferedImage) {
        SeedFillerBackgroundBasePoint seedFillerBackgroundBasePoint = (SeedFillerBackgroundBasePoint) drawable;
        fill(
                seedFillerBackgroundBasePoint.getX(),
                seedFillerBackgroundBasePoint.getY(),
                seedFillerBackgroundBasePoint.getBackgroundColor(),
                seedFillerBackgroundBasePoint.getFillColor(),
                bufferedImage
        );
    }
}
