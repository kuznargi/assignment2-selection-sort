"n,type,algorithm,time_ms,comparisons,swaps,array_accesses,memory_allocations,memory_used_kb,is_correct" | Out-File -FilePath "results.csv"


$sizes = @(10, 100, 1000, 10000, 100000, 1000000)
$types = @("random", "reverse", "sorted", "nearly-sorted")


foreach ($size in $sizes) {
    foreach ($type in $types) {
        Write-Host "Running benchmark for size $size and type $type"
        
         $output = java -jar "target/benchmarks.jar" "$size" "$type" 2>$null

        $output | Select-String -Pattern '^\d+' | ForEach-Object { $_.Line } | Out-File -Append -FilePath "results.csv"
    }
}

Write-Host "Benchmarking complete. Check results.csv"
