package dev.mattz.data.graphics.rasterizers.fillers;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Point2D;

import java.awt.image.BufferedImage;
import java.util.List;

public class ScanLinePolygonFiller implements ScanLineFiller {

    @Override
    public void draw(Drawable drawable, BufferedImage bufferedImage) {
        List<Point2D> points = drawable.getAllPoints();

        int minY = points.stream().mapToInt(Point2D::getY).min().orElse(0);
        int maxY = points.stream().mapToInt(Point2D::getY).max().orElse(0);

        for (int y = minY; y <= maxY; y++) {
            List<Integer> intersections = getIntersections(points, y);
            for (int i = 0; i < intersections.size() - 1; i += 2) {
                int xStart = intersections.get(i);
                int xEnd = intersections.get(i + 1);

                for (int x = xStart; x <= xEnd; x++) {
                    bufferedImage.setRGB(x, y, drawable.getColor().getRGB());
                }
            }
        }
    }
}
