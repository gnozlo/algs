import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tilesCopy(tiles);

    }

    @Override
    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int maxLength = String.valueOf(tiles.length * tiles[0].length).length();
        sb.append(tiles.length).append("\n");
        for (int[] ints : tiles) {
            for (int anInt : ints) {
                int thisLength = String.valueOf(anInt).length();
                sb.append(separator(maxLength - thisLength)).append(anInt);
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int ham = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int goalVal = goalVal(i, j);
                if (tiles[i][j] != goalVal && tiles[i][j] != 0) {
                    ham++;
                }
            }
        }
        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile goalTile = new Tile(tiles[i][j]);
                if (tiles[i][j] != goalVal(i, j) && tiles[i][j] != 0) {
                    manhattan = manhattan + Math.abs(goalTile.row - i) + Math.abs(goalTile.col - j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.equals(goalBoard());
    }

    @Override
    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;

        Board board1 = (Board) y;

        return Arrays.deepEquals(tiles, board1.tiles);

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int maxNeighbors = 4;
        List<Board> list = new ArrayList<>(maxNeighbors);
        // empty tile up
        if (findEmpty().row != 0) {
            list.add(tileSwap(findEmpty(), new Tile(findEmpty().row - 1, findEmpty().col)));
        }
        // left
        if (findEmpty().col != 0) {
            list.add(tileSwap(findEmpty(), new Tile(findEmpty().row, findEmpty().col - 1)));
        }
        // bottom
        if (findEmpty().row != tiles.length - 1) {
            list.add(tileSwap(findEmpty(), new Tile(findEmpty().row + 1, findEmpty().col)));
        }
        // right
        if (findEmpty().col != tiles[0].length - 1) {
            list.add(tileSwap(findEmpty(), new Tile(findEmpty().row, findEmpty().col + 1)));
        }
        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // random swap
        /* int t1Val = 0;
        int t2Val = 0;
        while (new Tile(t1Val).equals(findEmpty()) || new Tile(t2Val).equals(findEmpty()) || t1Val == t2Val) {
            t1Val = StdRandom.uniform(0, tiles.length * tiles[0].length);
            t2Val = StdRandom.uniform(0, tiles.length * tiles[0].length);
        }
        return tileSwap(new Tile(t1Val), new Tile(t2Val));*/

        if (tiles[0][0] != 0 && tiles[0][1] != 0)
            return tileSwap(new Tile(0, 1), new Tile(0, 0));
        else return tileSwap(new Tile(1, 1), new Tile(1, 0));

    }

    private Board goalBoard() {
        Board gb = new Board(new int[tiles.length][tiles[0].length]);

        for (int i = 0; i < gb.tiles.length; i++) {
            for (int j = 0; j < gb.tiles[i].length; j++) {
                gb.tiles[i][j] = goalVal(i, j);
            }
        }
        // gb.tiles[tiles.length - 1][tiles[0].length - 1] = 0;

        return gb;
    }

    // add spaces toString
    private String separator(int length) {
        return " " + " ".repeat(Math.max(0, length));
    }

    private int[][] tilesCopy(int[][] ts) {
        int[][] target = new int[ts.length][ts[0].length];
        for (int i = 0; i < ts.length; i++) {
            System.arraycopy(ts[i], 0, target[i], 0, ts[i].length);
        }
        return target;
    }

    // swap 2 tiles
    private Board tileSwap(Tile t1, Tile t2) {
        int[][] result = tilesCopy(tiles);
        int temp = result[t1.row][t1.col];
        result[t1.row][t1.col] = result[t2.row][t2.col];
        result[t2.row][t2.col] = temp;
        return new Board(result);
    }

    // find zero int this board
    private class Tile {
        int row;
        int col;

        private Tile(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // tile i ,j  of goal position
        private Tile(int pos) {
            if (pos == 0 || pos == tiles.length * tiles[0].length) {
                this.row = tiles.length - 1;
                this.col = tiles[0].length - 1;
            } else if (pos % tiles.length == 0) {
                this.row = pos / tiles.length - 1;
                this.col = tiles[0].length - 1;
            } else {
                this.row = pos / tiles[0].length;
                this.col = pos % tiles.length - 1;
            }
        }

        @Override
        public boolean equals(Object t) {
            if (this == t) return true;
            if (t == null || getClass() != t.getClass()) return false;

            Tile tile = (Tile) t;

            if (row != tile.row) return false;
            return col == tile.col;
        }
    }

    /* private int valOfTile(Tile t) {
        return tiles[t.col][t.row];
    }*/

    // return goal value of position of tile
    private int goalVal(int row, int col) {
        if ((row == tiles.length - 1) && (col == tiles[0].length - 1)) {
            return 0;
        } else
            return row * tiles[row].length + col + 1;
    }


    private Tile findEmpty() {
        Tile result = null;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == 0) {
                    result = new Tile(i, j);
                }
            }
        }
        return result;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a = new int[3][3];
        Board b = new Board(a);

        int[][] s = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board test = new Board(s);
        System.out.println(test.isGoal());
        System.out.println(test.hamming());
        System.out.println(test.manhattan());

        System.out.println(test);
        System.out.println(test.goalBoard());


       /* for (Board bb : test.neighbors()) {
            System.out.println(bb.toString());
        }*/

    }

}