package daa;

public class Metrics {
    private long comparisons;
    private int depth;
    private int maxDepth;
    private long startNanos;
    private long elapsedNanos;

    public void compare() {
        comparisons++;
    }

    public void enter() {
        depth++;
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void exit() {
        depth--;
    }

    public void start() {
        startNanos = System.nanoTime();
    }

    public void stop() {
        elapsedNanos = System.nanoTime() - startNanos;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public long getElapsedNanos() {
        return elapsedNanos;
    }

    public double getElapsedMillis() {
        return elapsedNanos / 1_000_000.0;
    }
}
