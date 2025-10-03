package org.example.metrics;


public class PerformanceTracker {
    private long comparisons =0;
    private long swaps =0;
    private long arrayAccesses=0;
    private long memoryAllocations=0;

    public void incrementComparisons(long cont){comparisons+=cont;}
    public void incrementSwaps(long cont){swaps+=cont;}
    public void incrementArrayAccesses(long cont){arrayAccesses+=cont;}
    public void incrementMemoryAllocations(long cont){memoryAllocations+=cont;}

    public long getComparisons(){return comparisons;}
    public long getSwaps(){return swaps;}
    public long getArrayAccesses(){return arrayAccesses;}
    public long getMemoryAllocations(){return memoryAllocations;}

    @Override
    public String toString() {
        return "Comparisons: " + comparisons + ", Swaps: " + swaps +
                ", Array Accesses: " + arrayAccesses + ", Memory Allocations: " + memoryAllocations;
    }

    public String toCSV() {
        return comparisons + "," + swaps + "," + arrayAccesses + "," + memoryAllocations;
    }

}
