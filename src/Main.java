public class Main {

    private static final String boardsPath = "C:\\Users\\antho\\Desktop\\Side_Projects\\Sudoku\\boards\\";
    private static final String board = "board1.txt";

    public static void main(String[] args) {
        int WIDTH = 700;
        int HEIGHT = 700;
        new Sudoku(WIDTH, HEIGHT, 1);
    }
}
