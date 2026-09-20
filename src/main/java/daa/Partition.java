package daa;

import java.util.Random;

public final class Partition {

    public int lt;
    public int gt;

    public void splitRandom(int[] a, int lo, int hi, Random rnd, Metrics m) {
        int pivot = a[lo + rnd.nextInt(hi - lo + 1)];
        split(a, lo, hi, pivot, m);
    }

    public void split(int[] a, int lo, int hi, int pivot, Metrics m) {
        int l = lo;
        int i = lo;
        int g = hi;
        while (i <= g) {
            m.compare();
            int c = Integer.compare(a[i], pivot);
            if (c < 0) {
                swap(a, l++, i++);
            } else if (c > 0) {
                swap(a, i, g--);
            } else {
                i++;
            }
        }
        lt = l;
        gt = g;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
