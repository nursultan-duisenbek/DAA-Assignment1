package daa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class QuickSelectTest {

    @Test
    void matchesSortedArrayOnRandomInput() {
        Random rnd = new Random(21);
        for (int t = 0; t < 200; t++) {
            int[] a = new int[1 + rnd.nextInt(1000)];
            for (int i = 0; i < a.length; i++) {
                a[i] = rnd.nextInt(2001) - 1000;
            }
            int[] sorted = a.clone();
            Arrays.sort(sorted);
            int k = rnd.nextInt(a.length);
            assertEquals(sorted[k], QuickSelect.select(a.clone(), k));
        }
    }

    @Test
    void everyPositionOfSmallArray() {
        int[] a = {9, 1, 8, 2, 7, 3, 6, 4, 5, 0};
        for (int k = 0; k < a.length; k++) {
            assertEquals(k, QuickSelect.select(a.clone(), k));
        }
    }

    @Test
    void singleElement() {
        assertEquals(4, QuickSelect.select(new int[]{4}, 0));
    }

    @Test
    void allEqual() {
        int[] a = new int[100];
        Arrays.fill(a, 6);
        assertEquals(6, QuickSelect.select(a, 57));
    }

    @Test
    void sortedInput() {
        int[] a = new int[10_000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        assertEquals(2500, QuickSelect.select(a, 2500));
    }

    @Test
    void emptyArrayIsRejected() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{}, 0));
        assertTrue(e.getMessage().contains("empty"));
    }

    @Test
    void outOfRangeKIsRejected() {
        int[] a = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a.clone(), -1));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a.clone(), 3));
    }

    @Test
    void nullArrayIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(null, 0));
    }

    @Test
    void comparisonsGrowLinearly() {
        int n = 100_000;
        int[] a = new Random(9).ints(n).toArray();
        Metrics m = new Metrics();
        QuickSelect.select(a, n / 2, m);
        assertTrue(m.getComparisons() < 10L * n);
    }
}
