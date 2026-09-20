package daa;

public final class DeterministicSelect {

    private static final int GROUP = 5;

    private DeterministicSelect() {
    }

    public static int select(int[] a, int k) {
        return select(a, k, new Metrics());
    }

    public static int select(int[] a, int k, Metrics m) {
        QuickSelect.check(a, k);
        m.start();
        int result = select(a, 0, a.length - 1, k, m, new Partition());
        m.stop();
        return result;
    }

    private static int select(int[] a, int lo, int hi, int k, Metrics m, Partition p) {
        m.enter();
        int result;
        while (true) {
            if (hi - lo + 1 <= GROUP) {
                InsertionSort.sort(a, lo, hi, m);
                result = a[k];
                break;
            }
            int pivot = medianOfMedians(a, lo, hi, m, p);
            p.split(a, lo, hi, pivot, m);
            if (k < p.lt) {
                hi = p.lt - 1;
            } else if (k > p.gt) {
                lo = p.gt + 1;
            } else {
                result = a[k];
                break;
            }
        }
        m.exit();
        return result;
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m, Partition p) {
        int groups = 0;
        for (int start = lo; start <= hi; start += GROUP) {
            int end = Math.min(start + GROUP - 1, hi);
            InsertionSort.sort(a, start, end, m);
            int median = start + (end - start) / 2;
            int t = a[lo + groups];
            a[lo + groups] = a[median];
            a[median] = t;
            groups++;
        }
        return select(a, lo, lo + groups - 1, lo + (groups - 1) / 2, m, p);
    }
}
