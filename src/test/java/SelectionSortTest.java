import org.example.algoritms.SelectionSort;
import org.example.metrics.PerformanceTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class SelectionSortTest {

    private SelectionSort sorter;
    private PerformanceTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new PerformanceTracker();
        sorter = new SelectionSort(tracker);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        sorter.sort(arr);
        assertArrayEquals(new int[]{}, arr);
        assertEquals(0, tracker.getComparisons()); // Нет операций
    }

    @Test
    void testSingleElement() {
        int[] arr = {5};
        sorter.sort(arr);
        assertArrayEquals(new int[]{5}, arr);
        assertEquals(0, tracker.getComparisons()); // Нет inner loop
    }

    @Test
    void testDuplicates() {
        int[] arr = {3, 1, 3, 2};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 3}, arr);
    }

    @Test
    void testSortedWithEarlyTermination() {
        int[] arr = {1, 2, 3, 4, 5};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
        // Для sorted: base 4 comps + suffix check ~3 = ~7, но <=10
        assertTrue(tracker.getComparisons() <= 10, "Early termination после i=0");
    }

    @Test
    void testReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
        // Full: base 10 comps + checks ~10 = ~20
        assertTrue(tracker.getComparisons() >= 10, "Worst case: full проходы");
    }

    @Test
    void testNearlySortedWithPartialEarlyTermination() {
        int[] arr = {1, 3, 2, 4, 5}; // Одна инверсия — sort после i=1, break
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
        // Base ~7 comps + checks ~5 = ~12
        assertTrue(tracker.getComparisons() <= 15, "Оптимизация сокращает после i=1");
    }

    @Test
    void testNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> sorter.sort(null));
    }

    // Property-based: Тест на случайные данные
    @Test
    void testRandomInputs() {
        Random rand = new Random();  // Без seed — OK для CI
        for (int size = 1; size <= 100; size++) {
            int[] arr = generateRandom(size, rand);
            int[] copy = arr.clone();
            sorter.sort(arr);
            assertTrue(isSorted(arr), "Random input size " + size + " should sort");
            // Cross-validation
            Arrays.sort(copy);
            assertArrayEquals(copy, arr);
        }
    }

    private int[] generateRandom(int size, Random rand) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1000);
        }
        return arr;
    }

    private boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }
}