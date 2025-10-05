#!/bin/bash

# Создаем файл с заголовком
echo "n,type,algorithm,time_ms,comparisons,swaps,array_accesses,memory_allocations,memory_used_kb,is_correct" > docs/performance-plots/jmh_benchmarks.csv

# Массивы размеров и типов
sizes=(100 1000 10000 100000)
types=(random sorted reverse nearly)

# Цикл по размерам и типам
for size in "${sizes[@]}"; do
    for type in "${types[@]}"; do
        echo "Running n=$size type=$type"
        # Запуск JMH напрямую с параметрами
        java -jar target/benchmarks.jar -p size=$size -p distribution=$type -rf csv -rff docs/performance-plots/jmh_benchmarks_$size_$type.csv
        # Объединяем результаты в один файл (извлекаем данные)
        tail -n +2 docs/performance-plots/jmh_benchmarks_$size_$type.csv | sed "s/,.*,//" | awk -v n=$size -v t=$type -v a="selection" '{print n","t","a","$0}' >> docs/performance-plots/jmh_benchmarks.csv
        # Удаляем временный файл
        rm docs/performance-plots/jmh_benchmarks_$size_$type.csv
    done
done

echo "Done! Check jmh_benchmarks.csv"