package daa;

import java.util.Random;

public final class QuickSort {

    private QuickSort() {
    }

    public static void sort(int[] a) {
        sort(a, new Metrics(), new Random());
    }

    public static void sort(int[] a, Metrics m) {
        sort(a, m, new Random());
    }

    public static void sort(int[] a, Metrics m, Random rnd) {
        m.start();
        sort(a, 0, a.length - 1, m, rnd, new Partition());
        m.stop();
    }

    private static void sort(int[] a, int lo, int hi, Metrics m, Random rnd, Partition p) {
        m.enter();
        while (lo < hi) {
            p.splitRandom(a, lo, hi, rnd, m);
            int lt = p.lt;
            int gt = p.gt;
            if (lt - lo < hi - gt) {
                sort(a, lo, lt - 1, m, rnd, p);
                lo = gt + 1;
            } else {
                sort(a, gt + 1, hi, m, rnd, p);
                hi = lt - 1;
            }
        }
        m.exit();
    }
}
