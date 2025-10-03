# Assignment 2: Selection Sort Implementation

## Overview
This repository implements **Selection Sort** (Student B from Pair 1: Basic Quadratic Sorts) as part of the Algorithmic Analysis and Peer Code Review assignment. The implementation includes:

- **Core Algorithm**: In-place Selection Sort with optimizations for early termination (no-swap check + suffix sorted validation for nearly-sorted data).
- **Optimizations**: Adaptive behavior — breaks early if the unsorted suffix is already sorted, reducing passes for nearly-sorted inputs.
- **Metrics Tracking**: Counts comparisons, swaps, array accesses, and memory allocations/usage.
- **Testing**: Comprehensive unit tests (edge cases, property-based random inputs, cross-validation with `Arrays.sort`).
- **Benchmarking**: CLI tool for performance measurement across input sizes (n=100 to 100000) and distributions (random, sorted, reverse, nearly-sorted).
- **Learning Goals Alignment**: Focuses on Big-O/Θ/Ω analysis, empirical validation, peer review preparation, and clean Git workflow.

Theoretical Background: Selection Sort repeatedly finds the minimum element from the unsorted portion and places it at the beginning. It is stable in swaps (~n worst-case) but performs ~n(n-1)/2 comparisons always (without opts).

**Author**: Nargiza, Student B (Pair 1). Partner: Aibek, Student A (Insertion Sort).

## Complexity Analysis
- **Time Complexity**:
    - Best Case: Ω(n) — for already sorted data (early termination after 1 pass + O(n) suffix check).
    - Worst/Average Case: Θ(n²) — full n-1 passes with ~n(n-1)/2 comparisons + O(n²) suffix checks (rarely triggered).
    - Recurrence: T(n) = T(n-1) + Θ(n) + O(n) (check), solved as T(n) = Θ(n²) via summation.
- **Space Complexity**: O(1) auxiliary (in-place; only constant extra space for temp in swap).
- **Optimizations Impact**: Reduces passes for nearly-sorted (e.g., 10% inversions: ~20–50% fewer comparisons vs. baseline).
- **Comparison with Partner's Algorithm (Insertion Sort)**: Selection has fewer swaps (~n) but more comparisons (~n²/2); Insertion better for nearly-sorted (adaptive shifts).

See `docs/analysis-report.pdf` for full peer analysis of Insertion Sort.

## Setup and Usage
### Prerequisites
- Java 11+ (tested on OpenJDK 17).
- Maven 3.6+ (for build/test/exec).

### Build and Test
```bash
# Clone repo
git clone https://github.com/yourusername/assignment2-selection-sort.git
cd assignment2-selection-sort

# Compile
mvn clean compile

# Run unit tests (covers edges: empty, single, duplicates, sorted/reverse/nearly/random)
mvn test
# Expected: BUILD SUCCESS, Tests run: 8, Failures: 0

# Single run (outputs CSV line)
mvn exec:java -Dexec.mainClass="org.example.cli.BenchmarkRunner" -Dexec.args="1000 random selection"

# Full benchmarks script (creates all_benchmarks.csv)
chmod +x run_benchmarks.sh  # If using the script
./run_benchmarks.sh

```
## Git Workflow

Branches: main (releases), feature/algorithm (impl), feature/metrics (tracking), feature/testing (tests), feature/cli (bench), feature/optimization (early termination), feature/docs (readme).
Commits: Prefix (init/feat/test/fix/docs/perf/release) + description.
History (git log --oneline): Clean storyline, 8-10 commits.
Tags: v1.0 (final).

## Repository Structure
assignment2-selection-sort/
├── src/main/java/org/example/
│   ├── algoritms/SelectionSort.java
│   ├── metrics/PerformanceTracker.java
│   └── cli/BenchmarkRunner.java
├── src/test/java/org/example/algoritms/
│   └── SelectionSortTest.java
├── docs/
│   ├── analysis-report.pdf
│   └── performance-plots/  # CSV, PNG plots
├── README.md
└── pom.xml

## Testing Requirements

Correctness: Unit (edges/duplicates/sorted/reverse/nearly), property-based (random), cross (Arrays.sort).
Performance: Scalability (10²-10⁵), distributions, memory (0 KB aux).
Peer: Integration (compile partner's code), benchmark reproduction, opt validation.

