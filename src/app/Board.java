package app;

import java.util.ArrayList;

public class Board {
    private int width_;
    private int length_;
    private ArrayList<ArrayList<Character>> matrix_;

    /**
     * constructor
     * @param wid width of the board
     * @param len length of the board
     * @param second 2nd players char
     * @param first 1st player char
     */
    public Board(int wid, int len, char second, char first) {
        width_ = wid;
        length_ = len;
        int midRow = width_ / 2;
        int midCol = length_ / 2;
        //creating a dynamic array
        matrix_ = new ArrayList<>();
        ArrayList<Character> list;
        //setting the intialized board
        for (int i = 0; i < width_; i++) {
            list = new ArrayList<>();
            for (int j = 0; j < length_; j++) {
                if ((j == midRow - 1 && i == midCol - 1) || (j == midRow + 1 - 1 && i == midCol + 1 - 1)) {
                    list.add(second);
                } else if ((j == midRow - 1 && i == midCol + 1 - 1) || (j == midRow + 1 - 1 && i == midCol - 1)) {
                    list.add(first);
                } else {
                    list.add(' ');
                }
            }
            matrix_.add(list);
        }
    }

    /**
     * getter
     * @return width of board
     */
    public int getWidth() {
        return this.width_;
    }

    /**
     * getter
     * @return length of board
     */
    public int getHeight() {
        return this.length_;
    }

    /**
     * getter
     * @param row row in board
     * @param col col in board
     * @return the sign which is in the (row,col) cell in board
     */
    public char getSign(int row, int col) {
        return matrix_.get(row).get(col);
    }

    /**
     * setter
     * @param row row in board
     * @param col col in board
     * @param sign a sign to set in the (row,col) cell in board
     */
    public void setSign(int row, int col, char sign) {
        matrix_.get(row).set(col, sign);
    }

    /**
     * checks if the input cell exists in the board
     * @param row row of the cell to check
     * @param col col of the cell to chekc
     * @return true if the cell (row,col) is part of the board, else false
     */
    public boolean isInBorders(int row, int col) {
        if ((!(row < 0)) && (!(row > getHeight() - 1)) && (!(col < 0)) &&
                (!(col > getWidth() - 1))) {
            return true;
        }
        return false;
    }

    /**
     * checks if the board is full.
     * @return true if it's full, else false
     */
    public boolean fullBoard() {
        for (int i = 0; i < width_; i++) {
            for (int j = 0; j < length_; j++) {
                if (matrix_.get(i).get(j) == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @return the matrix of the board
     */
    public ArrayList<ArrayList<Character>> getMatrix() {
        return this.matrix_;
    }

}

