package org.example.algoritms;

import org.example.metrics.PerformanceTracker;

/**
 * Implementation of Selection Sort with early termination optimization:
 * - If no swap is needed (minIndex == i) and the suffix [i+1..n-1] is sorted, the algorithm terminates early.
 * - Suffix check (isSuffixSorted) adds O(n-i) comparisons but enables adaptive behavior for nearly-sorted arrays.
 * - Time Complexity: Worst/Average O(n²), Best Ω(n) for already sorted arrays; Space Complexity: O(1).
 * - Metrics tracked: comparisons (including suffix check), swaps, array accesses.
 *
 * @author Student B
 */
public class SelectionSort {
    private final PerformanceTracker tracker;

    /**
     * Constructs a SelectionSort instance with a performance tracker.
     * @param tracker The tracker for logging metrics.
     */
    public SelectionSort(PerformanceTracker tracker) {
        this.tracker = tracker;
    }

    /**
     * Sorts the array in ascending order using selection sort with early termination.
     * @param arr The array to sort (non-null, modifiable).
     * @throws IllegalArgumentException If arr is null.
     * @note Handles edge cases: empty or single-element arrays are returned immediately (no operations logged).
     */
    public void sort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length <= 1) {
            return; // Edge case: already "sorted", no metrics incremented
        }

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                tracker.incrementComparisons(1);
                tracker.incrementArrayAccesses(2); // arr[j] and arr[minIndex]
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                swap(arr, i, minIndex);
                tracker.incrementSwaps(1);
                tracker.incrementArrayAccesses(4); // 2 reads + 2 writes in swap
            } else if (i < n - 2 && isSuffixSorted(arr, i + 1, n)) {
                break; // Early termination if suffix is sorted and not last iteration
            }
        }
    }

    /**
     * Checks if the suffix [start..end-1] is sorted in non-decreasing order.
     * @param arr The array to check.
     * @param start Starting index (inclusive).
     * @param end Ending index (exclusive).
     * @return true if the suffix is sorted, false otherwise.
     */
    private boolean isSuffixSorted(int[] arr, int start, int end) {
        if (start >= end - 1) return true; // 0 or 1 element — always sorted
        for (int k = start + 1; k < end; k++) {
            tracker.incrementComparisons(1);
            tracker.incrementArrayAccesses(2); // arr[k] and arr[k-1]
            if (arr[k] < arr[k - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Swaps two elements in the array.
     * @param arr The array.
     * @param i First index.
     * @param j Second index.
     */
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}