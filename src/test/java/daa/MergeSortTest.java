package daa;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class MergeSortTest {

    private static void check(int[] a) {
        int[] expected = a.clone();
        Arrays.sort(expected);
        MergeSort.sort(a);
        assertArrayEquals(expected, a);
    }

    @Test
    void matchesArraysSortOnRandomArrays() {
        Random rnd = new Random(1);
        for (int t = 0; t < 150; t++) {
            int[] a = new int[rnd.nextInt(2000)];
            for (int i = 0; i < a.length; i++) {
                a[i] = rnd.nextInt(20001) - 10000;
            }
            check(a);
        }
    }

    @Test
    void emptyArray() {
        check(new int[]{});
    }

    @Test
    void singleElement() {
        check(new int[]{42});
    }

    @Test
    void allEqual() {
        int[] a = new int[500];
        Arrays.fill(a, 3);
        check(a);
    }

    @Test
    void alreadySorted() {
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        check(a);
    }

    @Test
    void reverseSorted() {
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length - i;
        }
        check(a);
    }

    @Test
    void fewDistinctValues() {
        Random rnd = new Random(2);
        int[] a = new int[5000];
        for (int i = 0; i < a.length; i++) {
            a[i] = rnd.nextInt(10);
        }
        check(a);
    }

    @Test
    void depthIsLogarithmic() {
        int n = 100_000;
        int[] a = new Random(3).ints(n).toArray();
        Metrics m = new Metrics();
        MergeSort.sort(a, m);
        double limit = Math.log(n / (double) MergeSort.CUTOFF) / Math.log(2) + 3;
        assertTrue(m.getMaxDepth() <= limit);
    }

    @Test
    void countsComparisons() {
        int[] a = new Random(4).ints(1000).toArray();
        Metrics m = new Metrics();
        MergeSort.sort(a, m);
        assertTrue(m.getComparisons() > 0);
    }
}
