package daa;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class InsertionSortTest {

    @Test
    void matchesArraysSortOnRandomArrays() {
        Random rnd = new Random(7);
        for (int t = 0; t < 100; t++) {
            int[] a = new int[rnd.nextInt(200)];
            for (int i = 0; i < a.length; i++) {
                a[i] = rnd.nextInt(1000) - 500;
            }
            int[] expected = a.clone();
            Arrays.sort(expected);
            InsertionSort.sort(a);
            assertArrayEquals(expected, a);
        }
    }

    @Test
    void handlesEmptyAndSingle() {
        int[] empty = {};
        InsertionSort.sort(empty);
        assertArrayEquals(new int[]{}, empty);
        int[] one = {5};
        InsertionSort.sort(one);
        assertArrayEquals(new int[]{5}, one);
    }
}
