/* *****************************************************************************
 *  Name:              Mikhail Polivanov
 *  Coursera User ID:
 *  Last modified:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    //   private boolean[][] sites;
    private boolean[] linearSites;
    private int n;
    private WeightedQuickUnionUF q;
    private int numberOfOpened;
    //  private final boolean topOfGrid = true;     //??
    //  private final boolean bottomOfGrid = true;  //??


    // private int[] array;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (!(n <= 0)) {
            this.n = n;
            this.q = new WeightedQuickUnionUF(n * n + 2);
            //           this.sites = new boolean[n][n];
            this.linearSites = new boolean[n * n + 2];
            this.numberOfOpened = 0;
            //           linearSites[0] = true;      //??
            //          linearSites[n + 1] = true;    //??

        }
        else throw new IllegalArgumentException("n <= 0");

    }

    // opens the site (row, col) if it is not open already

    public void open(int row, int col) {
        siteCheck(row, col);
        if (!isOpen(row, col)) {
            numberOfOpened++;
            linearSites[indexOfLinearArray(row, col)] = true;
            addConnections(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        siteCheck(row, col);
        return linearSites[indexOfLinearArray(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        siteCheck(row, col);
        return q.find(0) == q.find(indexOfLinearArray(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return q.find(0) == q.find(n * n + 1);
    }

    private void addConnections(int row, int col) {

        int indexOfSite = indexOfLinearArray(row, col);
        int indexOfTop = indexOfLinearArray(row - 1, col);
        int indexOfRight = indexOfLinearArray(row, col + 1);
        int indexOfBottom = indexOfLinearArray(row + 1, col);
        int indexOfLeft = indexOfLinearArray(row, col - 1);

        if (row == 1) {
            q.union(indexOfSite, 0);
        }
        if (row == n) {
            q.union(indexOfSite, n * n + 1);
        }

        if (isInGrid(row - 1, col) && linearSites[indexOfTop]) {
            q.union(indexOfSite, indexOfTop);
        }
        if (isInGrid(row, col + 1) && linearSites[indexOfRight]) {
            q.union(indexOfSite, indexOfRight);
        }
        if (isInGrid(row + 1, col) && linearSites[indexOfBottom]) {
            q.union(indexOfSite, indexOfBottom);
        }
        if (isInGrid(row, col - 1) && linearSites[indexOfLeft]) {
            q.union(indexOfSite, indexOfLeft);
        }
    }

    private boolean isInGrid(int row, int col) {
        int rowInArray = row - 1;
        int colInArray = col - 1;
        if (rowInArray < 0 || rowInArray >= n) return false;
        if (colInArray < 0 || colInArray >= n) return false;

        return true;
    }

    private void siteCheck(int row, int col) {
        if (row > n)
            throw new IllegalArgumentException("row > n");
        if (col > n)
            throw new IllegalArgumentException("col > n");
        if (row <= 0)
            throw new IllegalArgumentException("row <= 0");
        if (col <= 0)
            throw new IllegalArgumentException("col <= 0");
    }

    private int indexOfLinearArray(int row, int col) {
        return n * (row - 1) + col;
    }

    public static void main(String[] args) {


        int n = 3;

        Percolation percolation = new Percolation(n);
        percolation.open(1, 1);
        System.out.println("index 1 1 = " + percolation.indexOfLinearArray(1, 1));
        percolation.open(2, 1);
        System.out.println("index 2 1 = " + percolation.indexOfLinearArray(2, 1));
        percolation.open(2, 2);
        System.out.println("index 2 2 = " + percolation.indexOfLinearArray(2, 2));
        percolation.open(3, 2);
        System.out.println("index 3 2 = " + percolation.indexOfLinearArray(3, 2));
        percolation.open(3, 1);
        System.out.println("index 3 1 = " + percolation.indexOfLinearArray(3, 1));
        System.out.println("number of open sites = " + percolation.numberOfOpenSites());


        System.out.println(percolation.q.find(0));
        System.out.println(percolation.q.find(1));
        System.out.println(percolation.q.find(2));
        System.out.println(percolation.q.find(4));
        System.out.println(percolation.q.find(5));
        System.out.println(percolation.percolates());

    }


}