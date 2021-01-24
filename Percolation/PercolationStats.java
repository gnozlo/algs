/* *****************************************************************************
 *  Name:              Mikhail Polivanov
 *  Coursera User ID:
 *  Last modified:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private final int n;
    private final int trials;
    // private int[] arrayOfOpenSites;
    private double[] arrayOfThreshold;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException("n <= 0");
        if (trials <= 0) throw new IllegalArgumentException("trials <= 0");
        this.n = n;
        this.trials = trials;
        fullTrials();
    }

    // sample mean of percolation threshold
    public double mean() {

        return StdStats.mean(arrayOfThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arrayOfThreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return this.mean()-(1.96*this.stddev())/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean()+(1.96*this.stddev())/Math.sqrt(trials);
    }

    private int trial() {

        Percolation p = new Percolation(n);
        boolean check = false;
        while (!check) {
            //            int row = StdRandom.uniform(1, n + 1);
            //            int col = StdRandom.uniform(1, n + 1);
            //            System.out.println(" " + row + " " + col);
            p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));


            check = p.percolates();
        }
        // System.out.println(p.numberOfOpenSites());
        return p.numberOfOpenSites();

    }

    private void fullTrials() {
        // arrayOfOpenSites = new int[trials];
        arrayOfThreshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            int k = trial();
            // arrayOfOpenSites[i] = k;
            arrayOfThreshold[i] = ((double) k) / (n * n);

        }
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        PercolationStats ps = new PercolationStats(30, 20);
        System.out.println(ps.trial());
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLo());
        System.out.println(ps.confidenceHi());
        System.out.println(sw.elapsedTime());

    }
}
