package daa;

import java.util.Arrays;
import java.util.Comparator;

public final class ClosestPair {

    public record Point(double x, double y) {
    }

    private static final Comparator<Point> BY_Y = Comparator.comparingDouble(Point::y);

    private ClosestPair() {
    }

    public static double find(Point[] points) {
        check(points);
        Point[] p = points.clone();
        Arrays.sort(p, Comparator.comparingDouble(Point::x));
        return solve(p, new Point[p.length], new Point[p.length], 0, p.length);
    }

    public static double bruteForce(Point[] points) {
        check(points);
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                best = Math.min(best, dist(points[i], points[j]));
            }
        }
        return best;
    }

    private static double solve(Point[] p, Point[] tmp, Point[] strip, int lo, int hi) {
        if (hi - lo <= 3) {
            double d = Double.POSITIVE_INFINITY;
            for (int i = lo; i < hi; i++) {
                for (int j = i + 1; j < hi; j++) {
                    d = Math.min(d, dist(p[i], p[j]));
                }
            }
            Arrays.sort(p, lo, hi, BY_Y);
            return d;
        }
        int mid = (lo + hi) >>> 1;
        double midX = p[mid].x();
        double d = Math.min(solve(p, tmp, strip, lo, mid), solve(p, tmp, strip, mid, hi));

        int i = lo;
        int j = mid;
        for (int k = lo; k < hi; k++) {
            if (i < mid && (j >= hi || p[i].y() <= p[j].y())) {
                tmp[k] = p[i++];
            } else {
                tmp[k] = p[j++];
            }
        }
        System.arraycopy(tmp, lo, p, lo, hi - lo);

        int size = 0;
        for (int k = lo; k < hi; k++) {
            if (Math.abs(p[k].x() - midX) < d) {
                strip[size++] = p[k];
            }
        }
        for (int a = 0; a < size; a++) {
            for (int b = a + 1; b < size && b <= a + 7 && strip[b].y() - strip[a].y() < d; b++) {
                d = Math.min(d, dist(strip[a], strip[b]));
            }
        }
        return d;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static void check(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("At least two points are required");
        }
    }
}
