package org.example.algoritms;

import org.example.metrics.PerformanceTracker;

/**
 * Implementation of Selection Sort with early termination optimization:
 * - If in a pass minIndex == i (no swap) and the suffix [i+1..n] is sorted — break (adaptively for nearly-sorted arrays).
 * - Suffix check: O(n-i) comparisons (extra cost, but safe).
 * - Time: Worst/Avg Θ(n²), Best Ω(n log n) worst due to check, but Ω(n) for sorted arrays; Space: O(1).
 * - Tracking metrics: comparisons (including check), swaps, arrayAccesses.
 */

public class SelectionSort {
    private final PerformanceTracker tracker;

    public SelectionSort(PerformanceTracker tracker) {
        this.tracker = tracker;
    }

    /**
     * Sorts the array in-place.
     * @param arr The array to be sorted (not null).
     * @throws IllegalArgumentException If arr == null.
     */
    public void sort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length <= 1) {
            return;
        }

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                tracker.incrementComparisons(1);
                tracker.incrementArrayAccesses(2);
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }


            if (minIndex != i) {
                swap(arr, i, minIndex);
                tracker.incrementSwaps(1);
                tracker.incrementArrayAccesses(4); // 2 чтения + 2 записи в swap
            } else {
                if (isSuffixSorted(arr, i + 1, n)) {
                    break;
                }
            }
        }
    }

    /**
     * Checks if the suffix [start..end-1] is sorted.
     * @return true if sorted (non-decreasing).
     */
    private boolean isSuffixSorted(int[] arr, int start, int end) {
        if (start >= end - 1) return true; // 0 или 1 элемент — sorted
        for (int k = start + 1; k < end; k++) {
            tracker.incrementComparisons(1);
            tracker.incrementArrayAccesses(2); // arr[k] и arr[k-1]
            if (arr[k] < arr[k - 1]) {
                return false;
            }
        }
        return true;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}