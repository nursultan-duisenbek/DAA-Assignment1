package daa;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class QuickSortTest {

    private static void check(int[] a) {
        int[] expected = a.clone();
        Arrays.sort(expected);
        QuickSort.sort(a);
        assertArrayEquals(expected, a);
    }

    private static int[] sortedArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        return a;
    }

    @Test
    void matchesArraysSortOnRandomArrays() {
        Random rnd = new Random(11);
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
        check(new int[]{-1});
    }

    @Test
    void allEqual() {
        int[] a = new int[500];
        Arrays.fill(a, 8);
        check(a);
    }

    @Test
    void alreadySorted() {
        check(sortedArray(1000));
    }

    @Test
    void reverseSorted() {
        int[] a = sortedArray(1000);
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length - i;
        }
        check(a);
    }

    @Test
    void depthOnSortedArrayIsBounded() {
        int n = 100_000;
        Metrics m = new Metrics();
        QuickSort.sort(sortedArray(n), m);
        double limit = 2 * Math.log(n) / Math.log(2);
        assertTrue(m.getMaxDepth() <= limit,
                "depth " + m.getMaxDepth() + " exceeds " + limit);
    }

    @Test
    void largeSortedArrayDoesNotOverflowStack() {
        assertDoesNotThrow(() -> QuickSort.sort(sortedArray(1_000_000)));
    }

    @Test
    void allEqualValuesStayLinear() {
        int n = 200_000;
        int[] a = new int[n];
        Arrays.fill(a, 5);
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        assertTrue(m.getComparisons() <= 2L * n);
    }

    @Test
    void fewDistinctValuesAreFast() {
        int n = 200_000;
        int[] a = new Random(5).ints(n, 0, 10).toArray();
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        assertTrue(m.getComparisons() < 40L * n);
    }
}
