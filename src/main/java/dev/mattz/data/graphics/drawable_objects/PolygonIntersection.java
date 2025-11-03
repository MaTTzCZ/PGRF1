package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public class PolygonIntersection extends Polygon {
    private final Polygon polygon1;
    private final Polygon polygon2;

    public PolygonIntersection(Color color, Polygon polygon1, Polygon polygon2) {
        super(color);
        this.polygon1 = polygon1;
        this.polygon2 = polygon2;
    }

    public Polygon getPolygon1() {
        return polygon1;
    }

    public Polygon getPolygon2() {
        return polygon2;
    }

    @Override
    public List<Point2D> getAllPoints() {
        return Stream.concat(polygon1.getAllPoints().stream(), polygon2.getAllPoints().stream()).toList();
    }
}
