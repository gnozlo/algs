import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;


public class KdTree {

    private static final int MAX_X = 1;
    private static final int MAX_Y = 1;
    private Node root;
    // limits of coordinates
    private ArrayList<Point2D> list;
    private Point2D nearestPoint;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        isNull(p);
        if (!this.contains(p)) {
            root = put(root, p, isXOrY(root));
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        isNull(p);
        Node x = root;

        while (x != null) {
            boolean cmp;
            if (x.point.equals(p)) return true;
            if (!x.xOrY) {
                cmp = p.x() < x.point.x();
            } else {
                cmp = p.y() < x.point.y();
            }
            if (cmp) x = x.left;
            if (!cmp) x = x.right;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 0, MAX_X, 0, MAX_Y);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        isNull(rect);
        list = new ArrayList<>();
        rangeSearch(root, rect);
        return list;
    }

    // a nearest neighbor in the set to point p; null if the set is empty

    public Point2D nearest(Point2D p) {
        isNull(p);
        if (root == null) {
            return null;
        }
        nearestPoint = root.point;

        nearestSearch(root, p);


        return nearestPoint;
    }

    private <P> void isNull(P p) {
        if (p == null) {
            throw new IllegalArgumentException("Argument is null");
        }
    }

    private Node put(Node x, Point2D p, boolean xOrY) {
        if (x == null) return new Node(p, 1, xOrY);

        if (!x.xOrY) {
            boolean cmp = p.x() < x.point.x();
            if (cmp) x.left = put(x.left, p, true);
            if (!cmp) x.right = put(x.right, p, true);
        }

        if (x.xOrY) {
            boolean cmp = p.y() < x.point.y();
            if (cmp) x.left = put(x.left, p, false);
            if (!cmp) x.right = put(x.right, p, false);
        }

        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    private void rangeSearch(Node x, RectHV rect) {
        if (x == null) return;
        if (rect.contains(x.point)) {
            list.add(x.point);
            rangeSearch(x.left, rect);
            rangeSearch(x.right, rect);
        }
        if (!rect.contains(x.point)) {
            if (!x.xOrY) {
                if (x.point.x() > rect.xmin() && x.point.x() > rect.xmax()) {
                    rangeSearch(x.left, rect);
                } else if (x.point.x() < rect.xmin() && x.point.x() < rect.xmax()) {
                    rangeSearch(x.right, rect);
                } else {
                    rangeSearch(x.left, rect);
                    rangeSearch(x.right, rect);
                }
            }
            if (x.xOrY) {
                if (x.point.y() > rect.ymin() && x.point.y() > rect.ymax()) {
                    rangeSearch(x.left, rect);
                } else if (x.point.y() < rect.ymin() && x.point.y() < rect.ymax()) {
                    rangeSearch(x.right, rect);
                } else {
                    rangeSearch(x.left, rect);
                    rangeSearch(x.right, rect);
                }
            }
        }
    }

    private void nearestSearch(Node x, Point2D point) {
        if (x == null) return;
        double minDistance = nearestPoint.distanceSquaredTo(point);
        if (x.point.distanceSquaredTo(point) < minDistance) {
            nearestPoint = x.point;
            minDistance = nearestPoint.distanceSquaredTo(point);
        }
        if (!x.xOrY) {
            Point2D p1 = new Point2D(x.point.x(), point.y());
            double minDistanceToRect = point.distanceSquaredTo(p1);
            if (x.point.x() > point.x()) {
                nearestSearch(x.left, point);
                if (minDistance > minDistanceToRect)
                    nearestSearch(x.right, point);
            } else {
                nearestSearch(x.right, point);
                if (minDistance > minDistanceToRect)
                    nearestSearch(x.left, point);
            }
        }
        if (x.xOrY) {
            Point2D p2 = new Point2D(point.x(), x.point.y());
            double minDistanceToRect = point.distanceSquaredTo(p2);
            if (x.point.y() > point.y()) {
                nearestSearch(x.left, point);
                if (minDistance > minDistanceToRect)
                    nearestSearch(x.right, point);
            } else {
                nearestSearch(x.right, point);
                if (minDistance > minDistanceToRect)
                    nearestSearch(x.left, point);
            }
        }

    }

    private final class Node {
        private final Point2D point;
        private int count;
        private boolean xOrY; // false compare x (root), true compare y
        private Node left, right;

        public Node(Point2D point) {
            this.point = point;
        }

        public Node(Point2D point, int count, boolean xOrY) {
            this(point);
            this.xOrY = xOrY;
            this.count = count;
        }

    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    private boolean isXOrY(Node x) {
        if (x == null) return false;
        return x.xOrY;
    }

    private void draw(Node x, double limitX1, double limitX2, double limitY1, double limitY2) {
        if (x == null) return;
        if (!x.xOrY) {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.point.x(), limitY1, x.point.x(), limitY2);
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(x.point.x(), x.point.y());
            draw(x.left, limitX1, x.point.x(), limitY1, limitY2);
            draw(x.right, x.point.x(), limitX2, limitY1, limitY2);

        }
        if (x.xOrY) {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(limitX1, x.point.y(), limitX2, x.point.y());
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(x.point.x(), x.point.y());
            draw(x.left, limitX1, limitX2, limitY1, x.point.y());
            draw(x.right, limitX1, limitX2, x.point.y(), limitY2);
        }
    }


    public static void main(String[] args) {
        KdTree PS = new KdTree();
        PS.insert(new Point2D(0.7, 0.2));
        PS.insert(new Point2D(0.5, 0.4));
        PS.insert(new Point2D(0.2, 0.3));
        PS.insert(new Point2D(0.4, 0.7));
        PS.insert(new Point2D(0.9, 0.6));
        /* PS.insert(new Point2D(0.0, 0.125));
        PS.insert(new Point2D(0.625, 0.125));
        PS.insert(new Point2D(0.5, 1.0));
        PS.insert(new Point2D(1.0, 0.125));
        PS.insert(new Point2D(0.625, 0.625));
        PS.insert(new Point2D(0.125, 0.0));
        PS.insert(new Point2D(0.375, 0.75));
        PS.insert(new Point2D(0.75, 1.0));
        PS.insert(new Point2D(0.125, 0.25));*/
        // PS.insert(new Point2D(0.6, 0.5));
        PS.draw();
        // System.out.println(PS.contains(new Point2D(0.125, 0.25)));
        // System.out.println(PS.nearest());


        Point2D p1 = new Point2D(0.709, 1);
        System.out.println(p1.distanceSquaredTo(new Point2D(0.9, 0.6)));
        System.out.println(p1.distanceSquaredTo(new Point2D(0.4, 0.7)));
        Point2D p2 = PS.nearest(p1);
        System.out.println(p2);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());



        /* RectHV rect1 = new RectHV(0.1, 0.0, 0.5, 0.6);
        rect1.draw();
        for (Point2D p : PS.range(rect1)) {
            System.out.println(p);
        }
        System.out.println(rect1.contains(new Point2D(0.2, 0.3)));*/


    }             // unit testing of the methods (optional)


}