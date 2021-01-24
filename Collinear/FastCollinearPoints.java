import java.util.Arrays;

public class FastCollinearPoints {
    // finds all line segments containing 4 or more points
    private final Point[] points;
    private int n = 0;  // num of line segments
    private LineSegment[] s = new LineSegment[0];
    private Point[] allReadyCollinear = new Point[0];

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Point = null");
        this.points = new Point[points.length];
        System.arraycopy(points, 0, this.points, 0, points.length);
        checkPoints();
        findCollPoints();
    }

    // the number of line segments
    public int numberOfSegments() {
        return n;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] toReturn = new LineSegment[s.length];
        System.arraycopy(s, 0, toReturn, 0, s.length);
        return toReturn;
    }

    private void findCollPoints() {

        /* for (int i = 0; i < points.length; i++) {
            System.out.print(points[i].toString() + "  ");
        }
        System.out.println();*/
        for (int i = 0; i < points.length - 2; i++) {
            Point p = points[i];
            Point[] temp = new Point[points.length - 1 - i];
            System.arraycopy(points, i + 1, temp, 0, temp.length);
            Arrays.sort(temp, p.slopeOrder());
            for (int j = 0; j < temp.length - 2; j++) {
                if (p.slopeTo(temp[j]) == p.slopeTo(temp[j + 2])) {
                    // System.out.println("equals " + j + " " + temp.length);

                    addToCollector(j, p, temp);
                    break;
                }
            }
        }
    }


    private void pointCollector(Point p1, Point p2) {
        n++;

        LineSegment[] temps = s;
        s = new LineSegment[n];
        if (n - 1 >= 0) System.arraycopy(temps, 0, s, 0, n - 1);

        s[n - 1] = new LineSegment(p1, p2);
    }

    private void checkPoints() {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Null point");
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) throw new IllegalArgumentException("duplicate points");
        }
    }

    private void addToCollector(int i, Point p, Point[] tempPoints) {

        // System.out.println("point for collect" + p);
        Point min = p;
        Point max = p;
        if (min.compareTo(tempPoints[i]) > 0) {
            min = tempPoints[i];
        }
        if (max.compareTo(tempPoints[i]) < 0) {
            max = tempPoints[i];
        }
        while (p.slopeTo(tempPoints[i]) == p.slopeTo(tempPoints[i + 1])) {

            i++;
            if (min.compareTo(tempPoints[i]) > 0) {
                min = tempPoints[i];
            }
            if (max.compareTo(tempPoints[i]) < 0) {
                max = tempPoints[i];
            }
            // System.out.println(i);
            if (i == tempPoints.length - 1) break;
        }
        if (!isInSegments(min, max)) {
            allReadyCollinear[allReadyCollinear.length - 1] = p;
            pointCollector(min, max);
        }


    }

    private boolean isInSegments(Point p1, Point p2) {
        if (allReadyCollinear.length == 0) {
            allReadyCollinear = new Point[n + 1];
            return false;

        } else {
            for (Point p : allReadyCollinear) {
                if (p.slopeTo(p1) == p.slopeTo(p2))
                    return true;
            }
        }
        Point[] temps = allReadyCollinear;
        allReadyCollinear = new Point[n + 1];
        if (n - 1 >= 0) System.arraycopy(temps, 0, allReadyCollinear, 0, n);
        return false;
    }
}