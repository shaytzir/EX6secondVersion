package myapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Created by shaytzir on 13/01/2018.
 */
public class BoardController extends GridPane {
    int size;
    private Board board;
    private GameFlow gameFlow;
    private StackPane panes[][];
    private ArrayList<ArrayList<Character>> matrix;
    private String color1;
    private String color2;
    private char player1Color;
    private char player2Color;
    private Vector<Button> options;
    private int cellHeight;
    private int cellWidth;
    private GameController controller;
    private String move;
   // private GameController gameControl;


    public BoardController(int size, GeneralPlayer player1, GeneralPlayer player2){
        this.size = size;
        this.panes = new StackPane[size][size];
        this.player1Color = player1.getSign();
        this.player2Color = player2.getSign();
        this.color1 = player1.getPlayerColor();
        this.color2 = player2.getPlayerColor();

        this.board = new Board(size, size, player2Color, player1Color);
        this.matrix = this.board.getMatrix();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLBoard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Debug: exepction in boardController");
            throw new RuntimeException(exception);

        }
    }

    public void setGameFlow(GameFlow flow) {
        this.gameFlow = flow;
    }

    public void draw() {

        this.getChildren().clear();

        this.matrix = this.board.getMatrix();
        this.getChildren().clear();
        int height = (int)this.getPrefHeight();
        int width = (int)this.getPrefWidth();
        this.cellHeight = height / matrix.size();
        this.cellWidth = width / matrix.get(0).size();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                Rectangle r = new Rectangle(cellWidth, cellHeight, Color.WHITE);
                r.setStroke(Color.BLACK);
                this.add(r, j, i);
                Circle c = new Circle();
                if (matrix.get(i).get(j).equals(player1Color)) {
                    c.setCenterX(i);
                    c.setCenterY(j);
                    c.setRadius(cellWidth/2);
                    if (color1.toUpperCase().equals("WHITE")) {
                        c.setStroke(Color.BLACK);
                    }
                    c.setFill(Paint.valueOf(color1.toUpperCase()));
                    this.add(c, j, i);
                } else if (matrix.get(i).get(j).equals(player2Color)) {
                    c.setCenterX(i);
                    c.setCenterY(j);
                    c.setRadius(cellWidth/2);
                    if (color2.toUpperCase().equals("WHITE")) {
                        c.setStroke(Color.BLACK);
                    }
                    c.setFill(Paint.valueOf(color2.toUpperCase()));
                    this.add(c, j, i);
                }
            }
        }

        Vector<Cell> movesForCurrentPlayer = this.gameFlow.getLogic().getMovesForCurrentPlayer();
        for (int i = 0; i < movesForCurrentPlayer.size(); i++) {
            final int moveX;
            final int moveY;
            Cell c = movesForCurrentPlayer.get(i);
            Rectangle rec = new Rectangle(this.cellWidth, this.cellHeight, Color.GRAY);
            this.add(rec, c.y, c.x);
        }


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int x = i;
                final int y = j;
                this.panes[i][j] =  new StackPane();
                this.panes[i][j].setOnMouseClicked(event1 -> {
                    this.gameFlow.run(x, y);
                    controller.draw();
                    this.draw();
                });
                add(this.panes[i][j], j, i);
            }
        }
    }


    public String getColor1() {
        return color1;
    }
    public String getColor2() {
        return color2;
    }
    public Board getBoard() { return this.board; }

    public void setGameController(GameController controller) {
        this.controller = controller;
    }

    public String getCurrentPlayerColor() {return this.gameFlow.getCurrentPlayerColor(); }

}
