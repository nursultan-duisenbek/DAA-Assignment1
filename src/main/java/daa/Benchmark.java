package daa;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public final class Benchmark {

    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] INPUTS = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5;

    private interface Algorithm {
        void run(int[] data, Metrics m, Random rnd);
    }

    public static void main(String[] args) throws IOException {
        Path out = Path.of(args.length > 0 ? args[0] : "results.csv");

        Map<String, Algorithm> algorithms = new LinkedHashMap<>();
        algorithms.put("MergeSort", (a, m, r) -> MergeSort.sort(a, m));
        algorithms.put("QuickSort", (a, m, r) -> QuickSort.sort(a, m, r));
        algorithms.put("QuickSelect", (a, m, r) -> QuickSelect.select(a, a.length / 2, m, r));
        algorithms.put("DeterministicSelect", (a, m, r) -> DeterministicSelect.select(a, a.length / 2, m));

        warmUp(algorithms);

        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(out))) {
            w.println("algorithm,input,n,time_ms,comparisons,max_depth");
            for (Map.Entry<String, Algorithm> entry : algorithms.entrySet()) {
                for (String input : INPUTS) {
                    for (int n : SIZES) {
                        int[] base = generate(input, n);
                        Metrics median = measure(entry.getValue(), base);
                        w.printf(Locale.ROOT, "%s,%s,%d,%.3f,%d,%d%n",
                                entry.getKey(), input, n, median.getElapsedMillis(),
                                median.getComparisons(), median.getMaxDepth());
                        System.err.printf(Locale.ROOT, "%s %s %d %.3f ms%n",
                                entry.getKey(), input, n, median.getElapsedMillis());
                    }
                }
            }
        }
        System.err.println("Saved " + out.toAbsolutePath());
    }

    private static void warmUp(Map<String, Algorithm> algorithms) {
        for (String input : INPUTS) {
            int[] base = generate(input, 50_000);
            for (Algorithm algorithm : algorithms.values()) {
                for (int i = 0; i < 5; i++) {
                    algorithm.run(Arrays.copyOf(base, base.length), new Metrics(), new Random(i));
                }
            }
        }
    }

    private static Metrics measure(Algorithm algorithm, int[] base) {
        List<Metrics> runs = new ArrayList<>();
        for (int run = 0; run < RUNS; run++) {
            int[] data = Arrays.copyOf(base, base.length);
            Metrics m = new Metrics();
            algorithm.run(data, m, new Random(1000 + run));
            runs.add(m);
        }
        runs.sort(Comparator.comparingLong(Metrics::getElapsedNanos));
        return runs.get(RUNS / 2);
    }

    static int[] generate(String input, int n) {
        Random rnd = new Random(n * 31L + input.hashCode());
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            switch (input) {
                case "random" -> a[i] = rnd.nextInt();
                case "sorted" -> a[i] = i;
                case "duplicates" -> a[i] = rnd.nextInt(10);
                default -> throw new IllegalArgumentException("Unknown input type: " + input);
            }
        }
        return a;
    }
}
