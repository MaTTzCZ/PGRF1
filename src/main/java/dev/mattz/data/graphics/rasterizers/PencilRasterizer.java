package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Drawable2D;
import dev.mattz.data.graphics.drawable_objects.PencilStroke;
import dev.mattz.data.graphics.drawable_objects.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PencilRasterizer implements Rasterizer {
    public void draw(int x, int y, Color color, BufferedImage image) {
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight())
            image.setRGB(x, y, color.getRGB());
    }

    @Override
    public void draw(Drawable2D drawable2D, BufferedImage bufferedImage) {
        PencilStroke pencilStroke = (PencilStroke) drawable2D;
        for(Point2D point : pencilStroke.getAllPoints()){
            draw(point.getX(), point.getY(), pencilStroke.getColor(), bufferedImage);
        }
    }
}
