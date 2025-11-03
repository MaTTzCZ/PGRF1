package dev.mattz.data.graphics.rasterizers.fillers;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.graphics.drawable_objects.Polygon;
import dev.mattz.data.graphics.drawable_objects.PolygonIntersection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ScanLinePolygonIntersectionFiller implements ScanLineFiller {

    @Override
    public void draw(Drawable drawable, BufferedImage bufferedImage) {
        PolygonIntersection polygonIntersection = (PolygonIntersection) drawable;

        int minY = Math.min(
                polygonIntersection.getPolygon1().getAllPoints().stream().mapToInt(Point2D::getY).min().orElse(0),
                polygonIntersection.getPolygon2().getAllPoints().stream().mapToInt(Point2D::getY).min().orElse(0)
        );
        int maxY = Math.max(
                polygonIntersection.getPolygon1().getAllPoints().stream().mapToInt(Point2D::getY).max().orElse(0),
                polygonIntersection.getPolygon2().getAllPoints().stream().mapToInt(Point2D::getY).max().orElse(0)
        );

        for (int y = minY; y <= maxY; y++) {
            List<int[]> intervalsA = getScanlineIntervals(polygonIntersection.getPolygon1(), y);
            List<int[]> intervalsB = getScanlineIntervals(polygonIntersection.getPolygon2(), y);

            for (int[] a : intervalsA) {
                for (int[] b : intervalsB) {
                    int start = Math.max(a[0], b[0]);
                    int end = Math.min(a[1], b[1]);
                    if (start <= end) {
                        for (int x = start; x <= end; x++) {
                            bufferedImage.setRGB(x, y, polygonIntersection.getColor().getRGB());
                        }
                    }
                }
            }
        }
    }

    private List<int[]> getScanlineIntervals(Polygon polygon, int y) {
        List<Point2D> points = polygon.getAllPoints();
        List<Integer> intersections = getIntersections(points, y);
        List<int[]> intervals = new ArrayList<>();
        for (int i = 0; i < intersections.size() - 1; i += 2) {
            intervals.add(new int[]{intersections.get(i), intersections.get(i + 1)});
        }
        return intervals;
    }
}
