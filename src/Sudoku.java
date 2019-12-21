import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Sudoku extends JFrame {
    private static final boolean DEBUG = false;

    private static final String errorLog = "." + File.separator + "logs" + File.separator;

    private static final String numericHelperMessage = "All inputs should be in the range [1, 9]";
    private static final String gameName = "Sudoku";
    private static final String boardsPath = "." + File.separator + "boards" + File.separator;

    private static final int gridSz = 9;
    private static final int cellSz = 60;
    private static final boolean notResizable = false;

    private GameTimer sudokuTimer;
    private int numEmptyCells;

    public Sudoku(int mode) {


        super(gameName);

        Container gameContainer = getContentPane();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(gridSz, gridSz));
        mainPanel.setPreferredSize(new Dimension(gridSz * cellSz, gridSz * cellSz));

        this.sudokuTimer = new GameTimer();

        gameContainer.setLayout(new BorderLayout());
        gameContainer.add(mainPanel, BorderLayout.CENTER);
        gameContainer.add(sudokuTimer, BorderLayout.PAGE_END);

        String board = getBoardName(mode);
        if (board.length() == 0) {
            // TODO: make logging class so that these objects can all log errors?
        }

        Board gameBoard = new Board();
        gameBoard.setBoard(boardsPath + board);
        this.numEmptyCells = gameBoard.getNumEmptyCells();

        Board solutionBoard = (new SudokuSolver(new Board(gameBoard))).solve();
        if (DEBUG) {
            System.out.println(solutionBoard);
        }

        for (int i = 0; i < gridSz; ++i) {
            for (int j = 0; j < gridSz; ++j) {
                char gameBoardVal = gameBoard.get(i, j);
                SudokuCell cell = new SudokuCell(solutionBoard.get(i, j));

                makeCell(cell, gameBoardVal);
                mainPanel.add(cell);
            }
        }

        pack();

        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(notResizable);
        this.setVisible(true);

        this.sudokuTimer.startTimer();

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

    private void makeCell(SudokuCell cell, char val) {
        if (val != Board.EMPTY) {
            cell.setText("" + val);
            cell.setEditable(false);
            cell.setBackground(new Color(240, 240, 240));
        } else {
            cell.setBackground(Color.PINK);
            cell.addActionListener(e -> {
                String res = cell.getText();
                // validate the input
                if (!isDigit(res)) {
                    cell.setText("");
                    popUp(numericHelperMessage);
                    return;
                }
                // check if match on solution
                if (res.compareTo(cell.getSolution()) == 0) {
                    cell.setBackground(Color.GREEN);
                    cell.setEditable(false);
                    cell.setText("" + cell.getSolution());
                    --numEmptyCells;
                    if (numEmptyCells == 0) {
                        stopGame();
                    }
                }
            });
        }
    }

    private void stopGame() {
        this.sudokuTimer.stopTimer();
        String winningMsg = "Congratulations! Total time for this board: " + this.sudokuTimer.totalTime();
        popUp(winningMsg);
    }

    private boolean isDigit(String s) {
        return s.chars().allMatch(Character::isDigit);
    }

    private void popUp(String msg) {
        JOptionPane.showOptionDialog(
                null,
                msg,
                "Error",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{},
                null
        );
    }
}
