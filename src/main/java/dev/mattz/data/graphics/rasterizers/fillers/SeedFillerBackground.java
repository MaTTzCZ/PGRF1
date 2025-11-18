package dev.mattz.data.graphics.rasterizers.fillers;

import dev.mattz.data.graphics.drawable_objects.Drawable2D;
import dev.mattz.data.graphics.drawable_objects.SeedFillerBackgroundBasePoint;
import dev.mattz.data.graphics.drawable_objects.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class SeedFillerBackground implements SeedFiller {

    public SeedFillerBackground() {
    }

    public void fill(int x, int y, Color fillColor, Color backgroundColor, BufferedImage bufferedImage) {
        Stack<Point2D> pointsStack = new Stack<>();
        pointsStack.add(new Point2D(x, y));
        while (!pointsStack.empty()) {
            Point2D currentPoint = pointsStack.pop();
            x = currentPoint.getX();
            y = currentPoint.getY();
            if (x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight()) {
                if (bufferedImage.getRGB(x, y) == backgroundColor.getRGB() && backgroundColor.getRGB() != fillColor.getRGB()) {
                    spread(x, y, fillColor, bufferedImage, pointsStack);
                }
            }
        }
    }

    @Override
    public void draw(Drawable2D drawable2D, BufferedImage bufferedImage) {
        SeedFillerBackgroundBasePoint seedFillerBackgroundBasePoint = (SeedFillerBackgroundBasePoint) drawable2D;
        fill(
                seedFillerBackgroundBasePoint.getX(),
                seedFillerBackgroundBasePoint.getY(),
                seedFillerBackgroundBasePoint.getFillColor(),
                seedFillerBackgroundBasePoint.getBackgroundColor(),
                bufferedImage
        );
    }
}
