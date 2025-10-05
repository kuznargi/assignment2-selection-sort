#!/bin/bash
echo "n,type,algorithm,time_ms,comparisons,swaps,array_accesses,memory_allocations,memory_used_kb,is_correct" >  docs/performance-plots/results.csv

sizes=(10 100 1000 10000 100000 1000000)
types=("random" "reverse" "sorted" "nearly-sorted")

for size in "${sizes[@]}"; do
    for type in "${types[@]}"; do
        echo "Running benchmark for size $size and type $type"

         java -jar target/benchmarks.jar "$size" "$type" 2>/dev/null | grep -E "^[0-9]+" >> docs/performance-plots/results.csv
    done
done

echo "Benchmarking complete. Check results.csv"
