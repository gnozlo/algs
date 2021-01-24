import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private static final int MAX_X = 1;
    private static final int MAX_Y = 1;
    private final Set<Point2D> mainSet;
    // limited coordinate


    // construct an empty set of points
    public PointSET() {
        this.mainSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return mainSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return mainSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        isNull(p);
        mainSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        isNull(p);
        return mainSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Point2D p : mainSet) {
            StdDraw.point(p.x(), p.y());
        }


    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        isNull(rect);
        List<Point2D> l1 = new ArrayList<>();
        for (Point2D p : mainSet) {
            if (rect.contains(p)) {
                l1.add(p);
            }
        }

        return l1;
    }

    // a nearest neighbor in the set to point p; null if the set is empty

    public Point2D nearest(Point2D p) {
        isNull(p);
        if (mainSet.isEmpty()) {
            return null;
        }

        Point2D nearestPoint = null;
        // double distance = Math.sqrt(MAX_X * MAX_X + MAX_Y * MAX_Y);
        double distance = new Point2D(0, 0).distanceSquaredTo(new Point2D(MAX_X, MAX_Y));
        for (Point2D point : mainSet) {
            if (distance >= p.distanceSquaredTo(point)) {
                distance = p.distanceSquaredTo(point);
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    private <P> void isNull(P p) {
        if (p == null) {
            throw new IllegalArgumentException("Argument is null");
        }
    }

    public static void main(String[] args) {
        PointSET PS = new PointSET();
        PS.insert(new Point2D(0.1, 0.2));
        PS.insert(new Point2D(0.2, 0.3));
        PS.insert(new Point2D(0.3, 0.7));
        PS.insert(new Point2D(0.1, 0.1));
        PS.draw();

    }             // unit testing of the methods (optional)


}