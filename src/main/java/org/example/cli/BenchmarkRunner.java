package org.example.cli;

import org.openjdk.jmh.Main;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java -cp target/classes org.example.cli.BenchmarkRunner <size> <distribution> [jmh-options]");
            System.err.println("Example: java -cp target/classes org.example.cli.BenchmarkRunner 1000 random -f 1 -wi 3 -i 5");
            System.err.println("Distributions: random, sorted, reverse, nearly-sorted");
            System.exit(1);
            return;
        }

        String size = args[0];
        String distribution = args[1];


        String[] jmhArgs = {
                "-p", "size=" + size,
                "-p", "distribution=" + distribution,
                "-f", "1",
                "-wi", "3",
                "-i", "5",
                "-r", "1s",
                "-bm", "avgt",
                "-tu", "us"
        };

        if (args.length > 2) {
            String[] extraArgs = new String[jmhArgs.length + (args.length - 2)];
            System.arraycopy(jmhArgs, 0, extraArgs, 0, jmhArgs.length);
            System.arraycopy(args, 2, extraArgs, jmhArgs.length, args.length - 2);
            jmhArgs = extraArgs;
        }

        Main.main(jmhArgs);
    }
}