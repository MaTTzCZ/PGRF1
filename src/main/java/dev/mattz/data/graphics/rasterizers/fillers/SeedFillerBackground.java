package dev.mattz.data.graphics.rasterizers.fillers;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.FillerBackgroundBasePoint;
import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.graphics.rasterizers.PencilRasterizer;
import dev.mattz.data.graphics.rasterizers.Rasterizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

public class SeedFillerBackground implements Rasterizer {
    PencilRasterizer pencilRasterizer;

    public SeedFillerBackground() {
        pencilRasterizer = new PencilRasterizer();
    }

    //Rekurze, neefektivní
//    public void fill(int x, int y, Color backgroundColor, Color fillColor, BufferedImage bufferedImage) {
//        if (x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight()) {
//            if (bufferedImage.getRGB(x, y) != backgroundColor.getRGB() || backgroundColor.getRGB() == fillColor.getRGB()) return;
//            bufferedImage.setRGB(x, y, fillColor.getRGB());
//            fill(x - 1, y, backgroundColor, fillColor, bufferedImage);
//            fill(x + 1, y, backgroundColor, fillColor, bufferedImage);
//            fill(x, y - 1, backgroundColor, fillColor, bufferedImage);
//            fill(x, y + 1, backgroundColor, fillColor, bufferedImage);
//        }
//    }

    public void fill(int x, int y, Color backgroundColor, Color fillColor, BufferedImage bufferedImage) {
        Stack<Point2D> pointsStack = new Stack<>();
        pointsStack.add(new Point2D(x, y));
        while (!pointsStack.empty()) {
            Point2D currentPoint = pointsStack.pop();
            x = currentPoint.getX();
            y = currentPoint.getY();
            if (x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight()) {
                if (bufferedImage.getRGB(x, y) == backgroundColor.getRGB() && backgroundColor.getRGB() != fillColor.getRGB()) {
                    bufferedImage.setRGB(x, y, fillColor.getRGB());
                    pointsStack.push(new Point2D(x - 1, y));
                    pointsStack.push(new Point2D(x + 1, y));
                    pointsStack.push(new Point2D(x, y - 1));
                    pointsStack.push(new Point2D(x, y + 1));
                }
            }
        }
    }

    @Override
    public void draw(Drawable drawable, BufferedImage bufferedImage) {
        FillerBackgroundBasePoint fillerBackgroundBasePoint = (FillerBackgroundBasePoint) drawable;
        fill(
                fillerBackgroundBasePoint.getX(),
                fillerBackgroundBasePoint.getY(),
                fillerBackgroundBasePoint.getBackgroundColor(),
                fillerBackgroundBasePoint.getFillColor(),
                bufferedImage
        );
    }
}
