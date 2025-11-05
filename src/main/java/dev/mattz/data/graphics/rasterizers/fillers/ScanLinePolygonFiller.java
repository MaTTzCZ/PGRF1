package dev.mattz.data.graphics.rasterizers.fillers;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.graphics.rasterizers.PencilRasterizer;
import dev.mattz.data.graphics.rasterizers.Rasterizer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLinePolygonFiller implements Rasterizer {
    PencilRasterizer pencilRasterizer = new PencilRasterizer();

    @Override
    public void draw(Drawable drawable, BufferedImage bufferedImage) {
        List<Point2D> points = drawable.getAllPoints();

        int minY = points.stream().mapToInt(Point2D::getY).min().orElse(0);
        int maxY = points.stream().mapToInt(Point2D::getY).max().orElse(0);

        for (int y = minY; y <= maxY; y++) {
            List<Integer> intersections = getIntersections(points, y);
            Collections.sort(intersections);
            for (int i = 0; i < intersections.size() - 1; i += 2) {
                int xStart = intersections.get(i);
                int xEnd = intersections.get(i + 1);

                for (int x = xStart; x <= xEnd; x++) {
                    pencilRasterizer.draw(x, y, drawable.getColor(), bufferedImage);
                }
            }
        }
    }

    private static List<Integer> getIntersections(List<Point2D> points, int y) {
        List<Integer> intersections = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point2D p1 = points.get(i);
            Point2D p2 = points.get((i + 1) % points.size());

            int y1 = p1.getY();
            int y2 = p2.getY();

            if ((y >= Math.min(y1, y2)) && (y < Math.max(y1, y2))) {
                float x = p1.getX() + (float) (y - y1) * (p2.getX() - p1.getX()) / (float) (y2 - y1);
                intersections.add(Math.round(x));
            }
        }
        return intersections;
    }
}
