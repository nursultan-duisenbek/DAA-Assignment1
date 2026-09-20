package daa;

public final class MergeSort {

    static final int CUTOFF = 15;

    private MergeSort() {
    }

    public static void sort(int[] a) {
        sort(a, new Metrics());
    }

    public static void sort(int[] a, Metrics m) {
        m.start();
        if (a.length > 1) {
            int[] buffer = new int[a.length];
            sort(a, buffer, 0, a.length - 1, m);
        }
        m.stop();
    }

    private static void sort(int[] a, int[] buffer, int lo, int hi, Metrics m) {
        m.enter();
        if (hi - lo + 1 <= CUTOFF) {
            InsertionSort.sort(a, lo, hi, m);
        } else {
            int mid = lo + (hi - lo) / 2;
            sort(a, buffer, lo, mid, m);
            sort(a, buffer, mid + 1, hi, m);
            merge(a, buffer, lo, mid, hi, m);
        }
        m.exit();
    }

    private static void merge(int[] a, int[] buffer, int lo, int mid, int hi, Metrics m) {
        System.arraycopy(a, lo, buffer, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > hi) {
                a[k] = buffer[i++];
            } else {
                m.compare();
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j++];
                } else {
                    a[k] = buffer[i++];
                }
            }
        }
    }
}
