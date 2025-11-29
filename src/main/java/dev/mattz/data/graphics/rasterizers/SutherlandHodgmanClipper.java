package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.FilledPolygon;
import dev.mattz.data.graphics.drawable_objects.Point;
import dev.mattz.data.graphics.drawable_objects.Polygon;

import java.util.ArrayList;
import java.util.List;

public class SutherlandHodgmanClipper {

    public SutherlandHodgmanClipper() {
    }

    public FilledPolygon intersect(Polygon subjectPolygon2D, Polygon clipPolygon2D) {
        List<Point> outputList = new ArrayList<>(subjectPolygon2D.getAllPoints());
        List<Point> clipPoints = new ArrayList<>(clipPolygon2D.getAllPoints());

        if (outputList.size() < 3 || clipPoints.size() < 3)
            return null;

        boolean ccw = isCounterClockwise(clipPoints);

        for (int i = 0; i < clipPoints.size(); i++) {
            Point A = clipPoints.get(i);
            Point B = clipPoints.get((i + 1) % clipPoints.size());

            List<Point> inputList = new ArrayList<>(outputList);
            outputList.clear();

            for (int j = 0; j < inputList.size(); j++) {
                Point P = inputList.get(j);
                Point Q = inputList.get((j + 1) % inputList.size());

                boolean P_inside = isInside(A, B, P, ccw);
                boolean Q_inside = isInside(A, B, Q, ccw);

                if (P_inside && Q_inside) {
                    outputList.add(Q);
                } else if (P_inside) {
                    Point I = intersection(A, B, P, Q);
                    if (I != null) outputList.add(I);
                } else if (Q_inside) {
                    Point I = intersection(A, B, P, Q);
                    if (I != null) outputList.add(I);
                    outputList.add(Q);
                }
            }
        }

        if (outputList.isEmpty())
            return null;

        FilledPolygon result = new FilledPolygon(subjectPolygon2D.getColor());
        for (Point p : outputList) {
            result.addPoint(p);
        }

        return result;
    }

    private static boolean isCounterClockwise(List<Point> polygon) {
        float sum = 0;
        for (int i = 0; i < polygon.size(); i++) {
            Point p1 = polygon.get(i);
            Point p2 = polygon.get((i + 1) % polygon.size());
            sum += (p2.getX() - p1.getX()) * (p2.getY() + p1.getY());
        }
        return sum < 0;
    }

    private static boolean isInside(Point A, Point B, Point P, boolean ccw) {
        float cross = (B.getX() - A.getX()) * (P.getY() - A.getY()) -
                (B.getY() - A.getY()) * (P.getX() - A.getX());
        return ccw ? cross >= 0 : cross <= 0;
    }

    private static Point intersection(Point A, Point B, Point P, Point Q) {
        float a1 = B.getY() - A.getY();
        float b1 = A.getX() - B.getX();
        float c1 = a1 * A.getX() + b1 * A.getY();

        float a2 = Q.getY() - P.getY();
        float b2 = P.getX() - Q.getX();
        float c2 = a2 * P.getX() + b2 * P.getY();

        float det = a1 * b2 - a2 * b1;
        if (Math.abs(det) < 1e-6f) return null;

        float x = (b2 * c1 - b1 * c2) / det;
        float y = (a1 * c2 - a2 * c1) / det;

        return new Point((int) x, (int) y);
    }
}