import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Sudoku extends JFrame {

    private static final String gameName = "Sudoku";
    private static final String boardsPath = "C:\\Users\\antho\\Desktop\\Side_Projects\\Sudoku\\boards\\";

    private static final int defaultW = 500;
    private static final int defaultH = 500;
    private static final int gridSz = 9;
    private static final int cellSz = 60;
    private static final boolean resizable = false;

    private Container gameContainer;

    private SudokuCell[] cells;
    private SudokuSolver solver;
    private Board gameBoard;
    private Board solutionBoard;
    private int w, h;

    public Sudoku(int w, int h, int mode) {


        super(gameName);

        this.gameContainer = getContentPane();
        this.gameContainer.setLayout(new GridLayout(gridSz, gridSz));


        String board = getBoardName(mode);
        if (board.length() == 0) return;

        this.gameBoard = new Board();
        this.gameBoard.setBoard(boardsPath + board);

        this.solutionBoard = new Board(this.gameBoard);

        this.solver = new SudokuSolver(this.solutionBoard);
        this.solutionBoard = this.solver.solve();

        int totalCells = gridSz * gridSz;
        this.cells = new SudokuCell[totalCells];
        for (int i = 0; i < gridSz; ++i) {
            for (int j = 0; j < gridSz; ++j) {
                int idx = i * gridSz + j;
                char gameBoardVal = this.gameBoard.get(i, j);
                this.cells[idx] = new SudokuCell(this.solutionBoard.get(i, j));

                if (gameBoardVal != Board.EMPTY) {
                    this.cells[idx].setText("" + this.gameBoard.get(i, j));
                    this.cells[idx].setEditable(false);
                    this.cells[idx].setBackground(new Color(240, 240, 240));
                } else {
                    this.cells[idx].setBackground(Color.PINK);
                    this.cells[idx].addActionListener(e -> {
                        String res = cells[idx].getText();
                        if (res.compareTo(cells[idx].getSolution()) != 0) {
                            cells[idx].setBackground(Color.RED);
                        } else {
                            cells[idx].setBackground(Color.GREEN);
                            cells[idx].setEditable(false);
                            cells[idx].setText("" + cells[idx].getSolution());
                        }
                    });
                }
                this.gameContainer.add(this.cells[idx]);
            }
        }

        this.gameContainer.setPreferredSize(new Dimension(gridSz * cellSz, gridSz * cellSz));
        pack();

        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(resizable);
        this.setVisible(true);
    }

    private String[] getBoardList() {
        File folder = new File(boardsPath);
        File[] fList = folder.listFiles();
        if (fList != null) {
            int N = fList.length;
            String[] fNames = new String[N];

            for (int i = 0; i < N; ++i) {
                if (fList[i].isFile()) {
                    fNames[i] = fList[i].getName();
                }
            }
            return fNames;
        }
        return null;
    }

    private String getBoardName(int mode) {
        String[] boardsList = getBoardList();
        if (boardsList == null) {
            return "";
        }
        int numBoards = boardsList.length;
        if (mode + 1 > numBoards || mode < 0) {
            mode = 0;
        }

        return boardsList[mode];
    }
}
