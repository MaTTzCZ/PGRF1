package dev.mattz.data.graphics.rasterizers.fillers;

import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.graphics.rasterizers.Rasterizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ScanLineFiller extends Rasterizer {
    default List<Integer> getIntersections(List<Point2D> points, int y){
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
        Collections.sort(intersections);
        return intersections;
    }
}
