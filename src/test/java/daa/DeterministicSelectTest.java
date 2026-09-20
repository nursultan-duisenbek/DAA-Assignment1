package daa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class DeterministicSelectTest {

    @Test
    void matchesSortedArrayOnRandomInput() {
        Random rnd = new Random(31);
        for (int t = 0; t < 200; t++) {
            int[] a = new int[1 + rnd.nextInt(1500)];
            for (int i = 0; i < a.length; i++) {
                a[i] = rnd.nextInt(501) - 250;
            }
            int[] sorted = a.clone();
            Arrays.sort(sorted);
            int k = rnd.nextInt(a.length);
            assertEquals(sorted[k], DeterministicSelect.select(a.clone(), k));
        }
    }

    @Test
    void sortedAndAllEqualInput() {
        int[] sorted = new int[5000];
        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = i;
        }
        assertEquals(1234, DeterministicSelect.select(sorted, 1234));
        int[] equal = new int[5000];
        Arrays.fill(equal, 2);
        assertEquals(2, DeterministicSelect.select(equal, 4000));
    }

    @Test
    void invalidInput() {
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{}, 0));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1, 2}, 2));
    }

    @Test
    void worstCaseComparisonsStayLinear() {
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        Metrics m = new Metrics();
        DeterministicSelect.select(a, n / 2, m);
        assertTrue(m.getComparisons() < 40L * n);
    }
}
