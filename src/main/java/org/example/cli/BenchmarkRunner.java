package org.example.cli;
import org.example.algoritms.SelectionSort;
import org.example.metrics.PerformanceTracker;
import java.util.Arrays;
import java.util.Random;

/**
 * CLI: java cli.BenchmarkRunner <size> <type> [algorithm=selection]
 * type: random, sorted, reverse, nearly
 * Output: CSV for plots (n,type,algorithm,time_ms,comparisons,swaps,array_accesses,memory_allocations,is_correct).
 * Example: mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner" -Dexec.args="1000 random selection"
 */
public class BenchmarkRunner {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: <size> <type> [algorithm=selection]");
            return;
        }
        int n = Integer.parseInt(args[0]);
        String type = args[1];
        String algo = (args.length > 2) ? args[2] : "selection";
        int[] arr = generateArray(n, type);
        int[] copy = arr.clone();
        PerformanceTracker tracker = new PerformanceTracker();
        long start = System.nanoTime();
        if (algo.equals("selection")) {
            new SelectionSort(tracker).sort(arr);
        } else {
            throw new IllegalArgumentException("Unknown algorithm: " + algo);
        }
        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        Arrays.sort(copy);
        boolean isCorrect = Arrays.equals(arr, copy);

        System.out.println("n,type,algorithm,time_ms,comparisons,swaps,array_accesses,memory_allocations,is_correct");
        System.out.println(n + "," + type + "," + algo + "," + timeMs + "," + tracker.toCSV() + "," + isCorrect);
    }

    private static int[] generateArray(int n, String type) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(100_000);
        }
        if (type.equals("sorted")) {
            Arrays.sort(arr);
        } else if (type.equals("reverse")) {
            Arrays.sort(arr);
            reverse(arr);
        } else if (type.equals("nearly")) {
            Arrays.sort(arr);
            for (int i = 0; i < n / 10; i++) {
                int a = rand.nextInt(n);
                int b = rand.nextInt(n);
                swap(arr, a, b);
            }
        }
        return arr;
    }

    private static void reverse(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            swap(arr, i, arr.length - i - 1);
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}