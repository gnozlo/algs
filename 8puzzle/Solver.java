import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private final Board board;
    private final Stack<Board> result = new Stack<>();
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("argument is null");
        }
        this.board = initial;
        minPQ();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }


    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {


        return isSolvable ? result.size() - 1 : -1;
    }


    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        return isSolvable ? result : null;

    }

/*    private class HamPriority implements Comparator<SolverBoard> {
        @Override
        public int compare(SolverBoard b1, SolverBoard b2) {
            return b1.ham + b1.move - b2.ham - b2.move;
        }
    }*/

    private class ManPriority implements Comparator<SolverBoard> {
        @Override
        public int compare(SolverBoard b1, SolverBoard b2) {
            if (b1.man + b1.move < b2.man + b2.move) {
                return -1;
            }

            if (b1.man + b1.move > b2.man + b2.move) {
                return 1;
            }

            if (b1.move < b2.man) {
                return -1;
            }

            if (b1.man > b2.man) {
                return 1;
            }

            return 0;

            // return b1.man + b1.move - b2.man - b2.move;
        }

    }

    private void minPQ() {
        int moves = 0;
        MinPQ<SolverBoard> pq1 = new MinPQ<>(new ManPriority());
        MinPQ<SolverBoard> pq2 = new MinPQ<>(new ManPriority());
        SolverBoard sBoard = new SolverBoard(board, moves, null);
        SolverBoard twinsBoard = new SolverBoard(board.twin(), moves, null);
        pq1.insert(sBoard);
        pq2.insert(twinsBoard);
        SolverBoard bMin1 = sBoard;
        SolverBoard bMin2 = twinsBoard;
        int vc = 0;
        int vc2 = 0;
        while (!bMin1.getBoard().isGoal()) {

            bMin1 = pq1.delMin();
            bMin2 = pq2.delMin();
            for (Board b1 : bMin1.getBoard().neighbors()) {
                if (bMin1.previousSB == null || !b1.equals(bMin1.previousSB.board)) {
                    pq1.insert(new SolverBoard(b1, bMin1.move + 1, bMin1));
                    vc++;
                }
            }

            for (Board b2 : bMin2.getBoard().neighbors()) {
                if (bMin2.previousSB == null || !b2.equals(bMin2.previousSB.board)) {
                    pq2.insert(new SolverBoard(b2, bMin2.move + 1, bMin2));
                    vc2++;
                }
            }

            if (bMin2.getBoard().isGoal() && !bMin1.getBoard().isGoal()) {
                isSolvable = false;

                return;
            }

            // System.out.println(moves);
            // System.out.println("tet board " + bMin1.getBoard() + " man priority " + bMin1.man + " move " + bMin1.move);
        }

        isSolvable = true;
        listOfGoal(bMin1);
    }

    private final class SolverBoard {
        private final Board board;
        private final int move;
        private final int man;
        // private final int ham;
        private final SolverBoard previousSB;


        public SolverBoard(Board board, int move, SolverBoard previousSB) {
            this.board = board;
            this.move = move;
            this.previousSB = previousSB;
            man = board.manhattan();
            //   ham = board.hamming();
        }

        public Board getBoard() {
            return board;
        }

    }

    private void listOfGoal(SolverBoard sb) {
        while (sb.previousSB != null) {
            result.push(sb.board);
            sb = sb.previousSB;
        }
        result.push(sb.board);
    }


    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

