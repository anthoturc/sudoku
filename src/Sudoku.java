import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Sudoku extends JFrame {

    private static final String gameName = "Sudoku";
    private static final String boardsPath = "C:\\Users\\antho\\Desktop\\Side_Projects\\Sudoku\\boards\\";

    private static final int gridSz = 9;
    private static final int cellSz = 60;
    private static final boolean notResizable = false;

    public Sudoku(int mode) {


        super(gameName);

        Container gameContainer = getContentPane();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(gridSz, gridSz));
        mainPanel.setPreferredSize(new Dimension(gridSz * cellSz, gridSz * cellSz));

        JPanel timerPanel = new JPanel();
        timerPanel.add(new GameTimer());

        gameContainer.setLayout(new BorderLayout());
        gameContainer.add(mainPanel, BorderLayout.CENTER);
        gameContainer.add(timerPanel, BorderLayout.PAGE_END);

        String board = getBoardName(mode);
        if (board.length() == 0) return;

        Board gameBoard = new Board();
        gameBoard.setBoard(boardsPath + board);

        Board solutionBoard = (new SudokuSolver(new Board(gameBoard))).solve();

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
                if (res.compareTo(cell.getSolution()) != 0) {
                    cell.setBackground(Color.RED);
                } else {
                    cell.setBackground(Color.GREEN);
                    cell.setEditable(false);
                    cell.setText("" + cell.getSolution());
                }
            });
        }
    }
}
