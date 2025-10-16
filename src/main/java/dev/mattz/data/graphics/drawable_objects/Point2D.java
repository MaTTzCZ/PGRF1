package dev.mattz.data.graphics.drawable_objects;

import java.util.List;

public record Point2D(int x, int y) implements Drawable {

    @Override
    public List<Point2D> getAllPoints() {
        return List.of(this);
    }
}
