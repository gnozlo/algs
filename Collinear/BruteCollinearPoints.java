import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
    private int n = 0;  // num of line segments
    private LineSegment[] s = new LineSegment[0];


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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


    private void checkPoints() {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Null point");
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) throw new IllegalArgumentException("duplicate points");
        }
    }

    private void findCollPoints() {
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l1 = k + 1; l1 < points.length; l1++) {
                        if ((points[i].slopeTo(points[j]))
                                == (points[j].slopeTo(points[k]))
                                && (points[j].slopeTo(points[k]))
                                == (points[k].slopeTo(points[l1]))) {
                            n++;
                            pointCollector(points[i], points[j], points[k], points[l1]);
                        }
                    }
                }
            }
        }
    }

    private void pointCollector(Point p1, Point p2, Point p3, Point p4) {
        Point[] arrayOf4Points = new Point[4];
        arrayOf4Points[0] = p1;
        arrayOf4Points[1] = p2;
        arrayOf4Points[2] = p3;
        arrayOf4Points[3] = p4;
        Arrays.sort(arrayOf4Points);

        LineSegment[] temps = s;
        s = new LineSegment[n];
        if (n - 1 >= 0) System.arraycopy(temps, 0, s, 0, n - 1);

        s[n - 1] = new LineSegment(arrayOf4Points[0], arrayOf4Points[3]);

    }

}
