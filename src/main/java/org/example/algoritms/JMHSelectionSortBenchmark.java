package org.example.benchmarks;

import org.example.algoritms.SelectionSort;
import org.example.metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * JMH benchmark for Selection Sort with early termination optimization.
 * Measures average time (us/op) and collects metrics via PerformanceTracker.
 * Supports sizes: 100, 1000, 10000, 100000 and distributions: random, sorted, reverse, nearly-sorted.
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class JMHSelectionSortBenchmark {

    @Param({"100", "1000", "10000", "100000"})
    private int size;

    @Param({"random", "sorted", "reverse", "nearly-sorted"})
    private String distribution;

    private int[] array;
    private PerformanceTracker tracker;

    @Setup(Level.Trial)
    public void setup() {
        tracker = new PerformanceTracker();
        array = generateArray(size, distribution);
    }

    @Benchmark
    public void sortBenchmark(Blackhole blackhole) {
        int[] copy = array.clone();
        SelectionSort sorter = new SelectionSort(tracker);
        sorter.sort(copy);
        blackhole.consume(copy);
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        System.out.printf("n,type,algorithm,time_us,comparisons,swaps,array_accesses,memory_allocations%n");
        System.out.printf("%d,%s,selection,%.3f,%d,%d,%d,%d%n",
                size, distribution, 0.0, // Replace with actual JMH time if accessible
                tracker.getComparisons(), tracker.getSwaps(),
                tracker.getArrayAccesses(), tracker.getMemoryAllocations());
        tracker.exportToCSV("benchmarks_" + size + "_" + distribution + ".csv");
    }

    private int[] generateArray(int size, String dist) {
        Random rand = new Random(42);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(100000);
        }
        switch (dist) {
            case "sorted":
                Arrays.sort(arr);
                break;
            case "reverse":
                Arrays.sort(arr);
                reverse(arr);
                break;
            case "nearly-sorted":
                Arrays.sort(arr);
                for (int i = 0; i < size / 10; i++) {
                    int idx = rand.nextInt(size - 1);
                    swap(arr, idx, idx + 1);
                }
                break;
            case "random":
            default:
                break;
        }
        return arr;
    }

    private void reverse(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            swap(arr, i, arr.length - i - 1);
        }
    }

    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    // Optional
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSelectionSortBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}