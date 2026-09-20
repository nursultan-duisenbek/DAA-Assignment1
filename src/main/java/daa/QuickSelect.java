package daa;

import java.util.Random;

public final class QuickSelect {

    private QuickSelect() {
    }

    public static int select(int[] a, int k) {
        return select(a, k, new Metrics(), new Random());
    }

    public static int select(int[] a, int k, Metrics m) {
        return select(a, k, m, new Random());
    }

    public static int select(int[] a, int k, Metrics m, Random rnd) {
        check(a, k);
        m.start();
        m.enter();
        Partition p = new Partition();
        int lo = 0;
        int hi = a.length - 1;
        while (lo < hi) {
            p.splitRandom(a, lo, hi, rnd, m);
            if (k < p.lt) {
                hi = p.lt - 1;
            } else if (k > p.gt) {
                lo = p.gt + 1;
            } else {
                break;
            }
        }
        m.exit();
        m.stop();
        return a[k];
    }

    static void check(int[] a, int k) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException(
                    "k must be between 0 and " + (a.length - 1) + " but was " + k);
        }
    }
}
