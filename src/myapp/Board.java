package myapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

public class Board {
    private int width_;
    private int length_;
    private char verticalSep_;
    private char c;
    private char horizontalSep_;
    private ArrayList<ArrayList<Character>> matrix_;
    private int size;

    public Board(int wid, int len, char first, char second) {
        width_ = wid;
        length_ = len;
        int midRow = width_ / 2;
        int midCol = length_ / 2;
        c = '|';
        horizontalSep_ = '-';
        verticalSep_ = '|';
        //creating a dynamic array
        matrix_ = new ArrayList<>();
        ArrayList<Character> list;
        //which holds arrays
        /*for (int i = 0; i < width_; i++) {
            matrix_.get(i) = new ArrayList<Character>();
        }*/
        //setting all board to be empty disks
        for (int i = 0; i < width_; i++) {
            list = new ArrayList<>();
            for (int j = 0; j < length_; j++) {
                //matrix_[i][j] = ' ';
                if ((j == midRow - 1 && i == midCol - 1) || (j == midRow + 1 - 1 && i == midCol + 1 - 1)) {
                    list.add(first);
                } else if ((j == midRow - 1 && i == midCol + 1 - 1) || (j == midRow + 1 - 1 && i == midCol - 1)) {
                    list.add(second);
                } else {
                    list.add(' ');
                }
            }
            matrix_.add(list);
        }
        //setting start of the game
        /*matrix_.set(midCol - 1, matrix_.get(midRow - 1), 'O');
        matrix_.get(midRow - 1).get(midCol - 1) = 'O';
        matrix_[midRow + 1 - 1 ][midCol + 1 -1] = 'O';
        matrix_[midRow - 1][midCol + 1 - 1] = 'X';
        matrix_[midRow + 1 - 1][midCol - 1] = 'X';
        */
    }

    /*public Board(Board b) {
        width_ = b.width_;
        length_ = b.length_;
        verticalSep_ = b.verticalSep_; //vertical seperator
        horizontalSep_ = b.horizontalSep_; //horizontal
        //creating a dynamic array
        matrix_ = new ArrayList[width_];
        //which holds arrays
        for (int i = 0; i < width_; i++) {
            matrix_[i] = new char[length_];
        }
        for (int i = 0; i < width_; i++) {
            for (int j = 0; j < length_; j++) {
                matrix_[i][j] = b.matrix_[i][j];
            }
        }
        size = b.size;
    }*/

    /*public String printBoard() {
        //creating the upper border of the table
        stringstream boardToShow;
        boardToShow << " curent board is: " << endl;
        for (int i = 0; i < width_; i++) {
            boardToShow << " | " << i+1;
        }
        boardToShow << " " << verticalSep_ << endl;
        for (int i = 0; i < width_; i++) {
            //creating a seperator line of '-'
            for (int j = 0; j < ((width_ * 2) + 1) * 2; j++) {
                boardToShow << horizontalSep_;
            }
            boardToShow << endl;
            for (int c = 0; c < length_; c++) {
                //mark which line it is
                if (c == 0) {
                    boardToShow << i + 1 << verticalSep_;
                    //if the table hold a player - print it's mark
                }
                if (matrix_[i][c] != ' ') {
                    boardToShow << ' ' << matrix_[i][c]
                            << ' ' << verticalSep_;
                } else { //keep clear
                    boardToShow << "   " << verticalSep_;
                }
            }
            boardToShow << endl;
        }
        //adds the last line of '-'
        for (int j = 0; j < ((width_ * 2) + 1) * 2; j++) {
            boardToShow << horizontalSep_;
        }
        boardToShow << endl;
        return boardToShow.str();
    }*/

    public int getWidth() {
        return this.width_;
    }

    public int getHeight() {
        return this.length_;
    }

    public char getSign(int row, int col) {
        return matrix_.get(row).get(col);
    }

    public void setSign(int row, int col, char sign) {
        matrix_.get(row).set(col, sign);
    }

    public boolean isInBorders(int row, int col) {
        if ((!(row < 0)) && (!(row > getHeight() - 1)) && (!(col < 0)) &&
                (!(col > getWidth() - 1))) {
            return true;
        }
        return false;
    }

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

    public ArrayList<ArrayList<Character>> getMatrix() {
        return this.matrix_;
    }

    public void printBoard() {
        //creating the upper border of the table
        System.out.print(" current board is: \n");
        for (int i = 0; i < width_; i++) {
            int j = i + 1;
            System.out.print(" | " + j);
        }
        System.out.print(" " + verticalSep_ + "\n");
        for (int i = 0; i < width_; i++) {
            //creating a seperator line of '-'
            for (int j = 0; j < ((width_ * 2) + 1) * 2; j++) {
                System.out.print(horizontalSep_);
            }
            System.out.print("\n");
            for (int c = 0; c < length_; c++) {
                //mark which line it is
                if (c == 0) {
                    int j = i + 1;
                    System.out.print(j);
                    System.out.print(verticalSep_);
                    //if the table hold a player - print it's mark
                }
                if (matrix_.get(i).get(c) != ' ') {
                    System.out.print(" " + matrix_.get(i).get(c) + " " + verticalSep_);
                } else { //keep clear
                    System.out.print("   " + verticalSep_);
                }
            }
            System.out.print("\n");
        }
        //adds the last line of '-'
        for (int j = 0; j < ((width_ * 2) + 1) * 2; j++) {
            System.out.print(horizontalSep_);
        }
        System.out.print("\n");
    }
}

