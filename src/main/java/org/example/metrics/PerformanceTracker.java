package org.example.metrics;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Tracks performance metrics for algorithms: comparisons, swaps, array accesses, and memory allocations.
 * Supports CSV export for benchmarking results.
 */
public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long arrayAccesses = 0;
    private long memoryAllocations = 0;

    public void incrementComparisons(long count) { comparisons += count; }
    public void incrementSwaps(long count) { swaps += count; }
    public void incrementArrayAccesses(long count) { arrayAccesses += count; }
    public void incrementMemoryAllocations(long count) { memoryAllocations += count; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getArrayAccesses() { return arrayAccesses; }
    public long getMemoryAllocations() { return memoryAllocations; }

    @Override
    public String toString() {
        return "Comparisons: " + comparisons + ", Swaps: " + swaps +
                ", Array Accesses: " + arrayAccesses + ", Memory Allocations: " + memoryAllocations;
    }

    /**
     * Exports the performance metrics to a CSV file.
     * @param filename The name of the file to write the CSV data to (e.g., "results.csv").
     */
    public void exportToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.append(String.format("%d,%d,%d,%d%n", comparisons, swaps, arrayAccesses, memoryAllocations));
        } catch (IOException e) {
            System.err.println("Error exporting to CSV: " + e.getMessage());
        }
    }
}