import java.util.Arrays;

public class SudokuSolver {

    private Board b;
    private static final char EMPTY = '0';
    private int row;
    private int col;

    public SudokuSolver(Board b) {
        this.b = b;
        this.row = 0;
        this.col = 0;
    }

    public Board solve() {
        solveAux();
        return this.b;
    }

    private boolean solveAux() {
        int N = this.b.size();

        // if we have hit the end of the board, then we have succeeded
        if (row >= N) return true;

        // wrap around so that the search space is valid w.r.t the board
        if (col >= N) {
            col = 0;
            row += 1;
            return solveAux();
        }

        if (this.b.get(row, col) != EMPTY) {
            col += 1;
            return solveAux();
        }

        // at the empty cell try each value [1, 9] until something works
        for (int val = 1; val <= N; ++val) {
            // before trying a value save the current row and column and then restore them
            int oldCol = col;
            int oldRow = row;
            // try the value
            this.b.set(row, col, (char) ('0' + val));
            if (validChoice()) {
                col += 1; // go to the next column
                if (solveAux()) {
                    return true;
                }
            }
            // restore the old values of the row and column since it did not work out
            col = oldCol;
            row = oldRow;
            // if it did not work out it should be put back to empty
            this.b.set(row, col, EMPTY);

        }
        // this path down the decision tree did not work out
        return false;
    }

    private boolean validChoice() {
        int N = this.b.size();

        // check the current column
        boolean[] seen = new boolean[N + 1]; //  seen is already set to all false
        for (int i = 0; i < N; ++i) {
            char currChar = this.b.get(row, i);

            // do not consider empty cells
            if (currChar == EMPTY) continue;
            int idx = Character.getNumericValue(currChar);

            if (seen[idx]) {
                return false;
            }
            seen[idx] = true;
        }

        // check the current row and reset the visited array
        Arrays.fill(seen, false);
        for (int i = 0; i < N; ++i) {
            char currChar = this.b.get(i, col);

            if (currChar == EMPTY) continue;
            int idx = Character.getNumericValue(currChar);

            if (seen[idx]) {
                return false;
            }
            seen[idx] = true;
        }

        // reset the visited array
        Arrays.fill(seen, false);

        int row_start = 0, col_start = 0;
        if (row >= 3 && row < 6) {
            row_start = 3;
        } else if (row >= 6) {
            row_start = 6;
        }

        if (col >= 3 && col < 6) {
            col_start = 3;
        } else if (col >= 6) {
            col_start = 6;
        }

        // check if the choice has messed up the block that it is in
        for (int i = row_start; i < row_start + 3; ++i) {
            for (int j = col_start; j < col_start + 3; ++j) {
                char currChar = this.b.get(i, j);

                if (currChar == EMPTY) continue;
                int idx = Character.getNumericValue(currChar);

                if (seen[idx]) {
                    return false;
                }
                seen[idx] = true;
            }
        }

        return true;
    }

    // TODO: add this later so that users can enter their own boards
    public boolean validate() {
        return true;
    }
}


