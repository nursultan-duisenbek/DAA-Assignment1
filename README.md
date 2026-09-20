# DAA Assignment 1: Divide and Conquer

MergeSort, QuickSort and QuickSelect for `int[]` with metrics, a benchmark, JUnit 5 tests and a report.
Bonus tasks are included: deterministic select (median of medians) and closest pair of points.

Author: Nursultan Duisenbekuly

## Requirements

- JDK 17 or newer
- Maven 3.8 or newer
- Python 3 with matplotlib (only for the plots)

## Build

```
mvn clean compile
```

## Run the tests

```
mvn test
```

## Run the benchmark

```
mvn -q compile exec:java
```

This runs every algorithm on n = 1 000, 10 000, 100 000, 1 000 000 and on random, sorted and duplicates input
(5 runs each, median time) and writes `results.csv`. It takes a few seconds.

## Make the plots

```
python3 scripts/plot.py results.csv plots
```

This creates `plots/time_vs_n.png`, `plots/depth_vs_n.png` and `plots/ratio_vs_n.png`.

## Project layout

```
src/main/java/daa
    Metrics.java              comparisons, recursion depth, time
    InsertionSort.java
    MergeSort.java            one buffer, cutoff 15, linear merge
    Partition.java            3-way partition shared by QuickSort and the selects
    QuickSort.java            random pivot, smaller side first
    QuickSelect.java
    DeterministicSelect.java  bonus A
    ClosestPair.java          bonus B
    Benchmark.java
src/test/java/daa           JUnit 5 tests
results.csv                 benchmark output
plots/                      PNG plots
REPORT.md                   report
```

## Notes

- `QuickSelect.select` and `DeterministicSelect.select` rearrange the array they get. Pass a copy if the original order matters.
- Comparisons are counted as one per element visited in a partition (a three-way comparison counts once).
- QuickSelect is iterative, so its recursion depth is always 1. The recursion in `DeterministicSelect` is counted.
