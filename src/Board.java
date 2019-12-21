/*
 *  Abstraction of the board class.
 *  Currently, the board is made up of characters
 *  but a later iteration will have the board be made
 *  up of Cell objects so that we can further abstract the board's
 *  functionality into GUI elements.
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Board {

    private static final String inputDelimiter = " ";

    private static int N = 9;
    private char[][] board;
    public static final char EMPTY = '0';
    private int numEmptyCells;

    public Board() {
        this.board = new char[N][N];
        this.numEmptyCells = 0;
    }

    public Board(Board other) {
        this.numEmptyCells = 0;
        this.board = new char[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                this.board[i][j] = other.get(i, j);
                if (this.board[i][j] == EMPTY) {
                    ++numEmptyCells;
                }
            }
        }
    }

    void set(int row, int col, char val) {
        this.board[row][col] = val;
    }

    char get(int row, int col) {
        return this.board[row][col];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (j % 3 == 0 && j != 0) {
                    sb.append("| ");
                }
                sb.append(this.board[i][j]);
                sb.append(' ');
            }
            if ((i + 1) % 3 == 0 && i + 1 != N) {
                sb.append("\n---------------------");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void setBoard(String fName) {
        this.numEmptyCells = 0;
        try {
            Scanner reader = new Scanner(new File(fName));
            int i = 0, j = 0;
            while (reader.hasNextLine()) {
                String currLine = reader.nextLine();
                currLine = currLine.trim();
                String[] rowElements = currLine.split(inputDelimiter);
                for (String el : rowElements) {
                    this.board[i][j] = el.charAt(0);
                    if (this.board[i][j] == EMPTY) {
                        ++this.numEmptyCells;
                    }
                    ++j;
                }
                ++i;
                j = 0;
            }

            reader.close();
//            String s = reader.readLine();
//            int i = 0, j = 0;
//
//            while (s != null) {
//                s = s.trim();
//                String[] rowElements = s.split(" ");
//                for (String el : rowElements) {
//                    this.board[i][j] = el.charAt(0);
//                    if (this.board[i][j] == EMPTY) {
//                        ++this.numEmptyCells;
//                    }
//                    ++j;
//                }
//                s = reader.readLine();
//                ++i;
//                j = 0;
//            }
//
//            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist, please enter a valid file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        return this.board.length;
    }

    public int getNumEmptyCells() {
        return this.numEmptyCells;
    }
}
