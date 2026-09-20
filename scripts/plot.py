import csv
import math
import sys
from collections import defaultdict

import matplotlib

matplotlib.use("Agg")
import matplotlib.pyplot as plt

src = sys.argv[1] if len(sys.argv) > 1 else "results.csv"
out = sys.argv[2] if len(sys.argv) > 2 else "plots"

rows = list(csv.DictReader(open(src)))
data = defaultdict(list)
for r in rows:
    data[(r["algorithm"], r["input"])].append(
        (int(r["n"]), float(r["time_ms"]), int(r["comparisons"]), int(r["max_depth"]))
    )

algorithms = ["MergeSort", "QuickSort", "QuickSelect", "DeterministicSelect"]
inputs = ["random", "sorted", "duplicates"]
colors = {"MergeSort": "#1f77b4", "QuickSort": "#d62728", "QuickSelect": "#2ca02c", "DeterministicSelect": "#9467bd"}
styles = {"random": "-", "sorted": "--", "duplicates": ":"}
markers = {"random": "o", "sorted": "s", "duplicates": "^"}


def draw(ax, algs, index, ylabel, logy=False, transform=None):
    for alg in algs:
        for inp in inputs:
            pts = sorted(data[(alg, inp)])
            xs = [p[0] for p in pts]
            ys = [transform(alg, p) if transform else p[index] for p in pts]
            ax.plot(xs, ys, styles[inp], marker=markers[inp], color=colors[alg], label=f"{alg} / {inp}", linewidth=1.6, markersize=5)
    ax.set_xscale("log")
    if logy:
        ax.set_yscale("log")
    ax.set_xlabel("n")
    ax.set_ylabel(ylabel)
    ax.grid(True, which="both", alpha=0.3)


fig, ax = plt.subplots(figsize=(9, 6))
draw(ax, algorithms, 1, "time, ms (median of 5)", logy=True)
ax.set_title("Time vs n")
ax.legend(fontsize=7, ncol=2)
fig.tight_layout()
fig.savefig(f"{out}/time_vs_n.png", dpi=150)
plt.close(fig)

fig, ax = plt.subplots(figsize=(9, 6))
draw(ax, algorithms, 3, "max recursion depth")
ax.set_title("Max recursion depth vs n")
ax.legend(fontsize=7, ncol=2)
fig.tight_layout()
fig.savefig(f"{out}/depth_vs_n.png", dpi=150)
plt.close(fig)

fig, (left, right) = plt.subplots(1, 2, figsize=(14, 6))
draw(left, ["MergeSort", "QuickSort"], 2, "comparisons / (n * log2 n)",
     transform=lambda alg, p: p[2] / (p[0] * math.log2(p[0])))
left.set_title("Sorts: comparisons / (n log2 n)")
left.set_ylim(bottom=0)
left.legend(fontsize=8)
draw(right, ["QuickSelect", "DeterministicSelect"], 2, "comparisons / n",
     transform=lambda alg, p: p[2] / p[0])
right.set_title("Selection: comparisons / n")
right.set_ylim(bottom=0)
right.legend(fontsize=8)
fig.tight_layout()
fig.savefig(f"{out}/ratio_vs_n.png", dpi=150)
plt.close(fig)

print("plots saved to", out)
