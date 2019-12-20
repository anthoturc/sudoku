public class Main {
    private static final String boardsPath = "C:\\Users\\antho\\Desktop\\Side_Projects\\Sudoku\\boards\\";
    private static final String board = "board1.txt";

    public static void main(String[] args) {
        Board b = new Board();
        b.setBoard(boardsPath + board);

        SudokuSolver sudoku = new SudokuSolver(b);
        Board solvedB = sudoku.solve();

        System.out.println(solvedB);
    }
}
