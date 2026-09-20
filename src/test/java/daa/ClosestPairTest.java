package daa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import daa.ClosestPair.Point;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ClosestPairTest {

    private static Point[] randomPoints(Random rnd, int n, int range) {
        Point[] p = new Point[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Point(rnd.nextInt(range), rnd.nextInt(range));
        }
        return p;
    }

    @Test
    void matchesBruteForceOnRandomPoints() {
        Random rnd = new Random(41);
        for (int t = 0; t < 100; t++) {
            int n = 2 + rnd.nextInt(2000 - 1);
            Point[] p = randomPoints(rnd, n, 100_000);
            assertEquals(ClosestPair.bruteForce(p), ClosestPair.find(p), 1e-9);
        }
    }

    @Test
    void matchesBruteForceWithManyDuplicatesAndTies() {
        Random rnd = new Random(42);
        for (int t = 0; t < 50; t++) {
            Point[] p = randomPoints(rnd, 300, 30);
            assertEquals(ClosestPair.bruteForce(p), ClosestPair.find(p), 1e-9);
        }
    }

    @Test
    void twoPoints() {
        Point[] p = {new Point(0, 0), new Point(3, 4)};
        assertEquals(5.0, ClosestPair.find(p), 1e-9);
    }

    @Test
    void pointsOnOneVerticalLine() {
        Point[] p = new Point[100];
        for (int i = 0; i < p.length; i++) {
            p[i] = new Point(5, i * i);
        }
        assertEquals(1.0, ClosestPair.find(p), 1e-9);
    }

    @Test
    void tooFewPointsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> ClosestPair.find(new Point[]{new Point(1, 1)}));
        assertThrows(IllegalArgumentException.class, () -> ClosestPair.find(null));
    }
}
