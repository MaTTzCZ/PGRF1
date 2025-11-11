package dev.mattz.data.D2.graphics.rasterizers.line_clipping;

import dev.mattz.data.D2.graphics.drawable_objects.FilledPolygon;
import dev.mattz.data.D2.graphics.drawable_objects.Point2D;
import dev.mattz.data.D2.graphics.drawable_objects.Polygon;

import java.util.ArrayList;
import java.util.List;

public class SutherlandHodgmanClipper {

    public SutherlandHodgmanClipper() {
    }

    public FilledPolygon intersect(Polygon subjectPolygon, Polygon clipPolygon) {
        List<Point2D> outputList = new ArrayList<>(subjectPolygon.getAllPoints());
        List<Point2D> clipPoints = new ArrayList<>(clipPolygon.getAllPoints());

        if (outputList.size() < 3 || clipPoints.size() < 3)
            return null;

        boolean ccw = isCounterClockwise(clipPoints);

        for (int i = 0; i < clipPoints.size(); i++) {
            Point2D A = clipPoints.get(i);
            Point2D B = clipPoints.get((i + 1) % clipPoints.size());

            List<Point2D> inputList = new ArrayList<>(outputList);
            outputList.clear();

            for (int j = 0; j < inputList.size(); j++) {
                Point2D P = inputList.get(j);
                Point2D Q = inputList.get((j + 1) % inputList.size());

                boolean P_inside = isInside(A, B, P, ccw);
                boolean Q_inside = isInside(A, B, Q, ccw);

                if (P_inside && Q_inside) {
                    outputList.add(Q);
                } else if (P_inside) {
                    Point2D I = intersection(A, B, P, Q);
                    if (I != null) outputList.add(I);
                } else if (Q_inside) {
                    Point2D I = intersection(A, B, P, Q);
                    if (I != null) outputList.add(I);
                    outputList.add(Q);
                }
            }
        }

        if (outputList.isEmpty())
            return null;

        FilledPolygon result = new FilledPolygon(subjectPolygon.getColor());
        for (Point2D p : outputList) {
            result.addPoint(p);
        }

        return result;
    }

    private static boolean isCounterClockwise(List<Point2D> polygon) {
        float sum = 0;
        for (int i = 0; i < polygon.size(); i++) {
            Point2D p1 = polygon.get(i);
            Point2D p2 = polygon.get((i + 1) % polygon.size());
            sum += (p2.getX() - p1.getX()) * (p2.getY() + p1.getY());
        }
        return sum < 0;
    }

    private static boolean isInside(Point2D A, Point2D B, Point2D P, boolean ccw) {
        float cross = (B.getX() - A.getX()) * (P.getY() - A.getY()) -
                (B.getY() - A.getY()) * (P.getX() - A.getX());
        return ccw ? cross >= 0 : cross <= 0;
    }

    private static Point2D intersection(Point2D A, Point2D B, Point2D P, Point2D Q) {
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

        return new Point2D((int) x, (int) y);
    }
}