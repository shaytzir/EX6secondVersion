package fxml;

import app.Cell;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import app.Board;
import app.GameFlow;
import app.GeneralPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by shaytzir on 13/01/2018.
 */
public class BoardController extends GridPane {
    int size;
    private Board board;
    private GeneralPlayer player1;
    private GeneralPlayer player2;
    private GameFlow gameFlow;
    private ImageView imageBoard[][];
    private GameController controller;
    private Image empty;
    private Image possible;
    private Image firstP;
    private Image secondP;

    public BoardController(int size, GeneralPlayer player1, GeneralPlayer player2){
        this.size = size;
        this.player1 =player1;
        this.player2 = player2;

        this.board = new Board(size, size, player2.getSign(), player1.getSign());
        this.imageBoard = new ImageView[size][size];
        String theme = player1.getPlayerTheme();
        if (theme.equals("Fruit")) {
            this.firstP = new Image(getClass().getClassLoader().getResourceAsStream("images/" + player1.getPlayerColor() + "2.jpg"));
            this.secondP = new Image(getClass().getClassLoader().getResourceAsStream("images/" + player2.getPlayerColor() + "2.jpg"));
            this.possible = new Image(getClass().getClassLoader().getResourceAsStream("images/Possible.jpg"));
        } else {
            this.firstP = new Image(getClass().getClassLoader().getResourceAsStream("images/" + player1.getPlayerColor() + ".jpg"));
            this.secondP = new Image(getClass().getClassLoader().getResourceAsStream("images/" + player2.getPlayerColor() + ".jpg"));
            this.possible = new Image(getClass().getClassLoader().getResourceAsStream("images/Possible.jpg"));
        }
        this.empty = new Image(getClass().getClassLoader().getResourceAsStream("images/Empty2.jpg"));
        // allocate each cell with image and set mouse click
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                imageBoard[i][j] = new ImageView();
                // set mouse click and set an event
                imageBoard[i][j].setOnMouseClicked(event -> {
                    Node source = (Node) event.getSource();
                    Integer colIndex = GridPane.getColumnIndex(source);
                    Integer rowIndex = GridPane.getRowIndex(source);
                    // for a click call runGame in gameFlow
                    gameFlow.run(rowIndex, colIndex);
                });
            }
        }

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
        ArrayList<ArrayList<Character>> matrix = this.board.getMatrix();
        this.getChildren().clear();
        int height = (int)this.getPrefHeight();
        int width = (int)this.getPrefWidth();
        int cellHeight = height / matrix.size();
        int cellWidth = width / matrix.get(0).size();
        Vector<Cell> moves = gameFlow.getLogic().getMovesForCurrentPlayer();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char player = board.getSign(i,j);
                // if the type is the first player type than set the cell image
                // with the first player image
                if (player == player1.getSign()) {
                    imageBoard[i][j].setImage(this.firstP);
                    this.add(imageBoard[i][j], j, i);
                    // if the type is the second player type than set the cell
                    // image with the second player image
                } else if (player == player2.getSign()) {
                    imageBoard[i][j].setImage(this.secondP);
                    this.add(imageBoard[i][j], j, i);
                } else {
                    boolean possible = false;
                    for (int l = 0; l < moves.size(); l++) {
                        if ((moves.get(l).x == i) && (moves.get(l).y == j)) {
                            possible = true;
                        }
                    }
                    if (possible) {
                        imageBoard[i][j].setImage(this.possible);
                    } else {
                        imageBoard[i][j].setImage(this.empty);
                    }
                    this.add(imageBoard[i][j],j ,i);
                }
                /* else { // check if the cell should be drawn as bolt
                    boolean bolt = false;
                    // if the cell should be bolt set the correct image
                    for (int t = 0; t < list.size(); t++) {
                        if (list.get(t).getX() == i && list.get(t).getY() == j) {
                            imageBoardMatrix[i][j].setImage(boltCell);
                            bolt = true;
                        }
                    } // if the cell should not be bolt
                    if (!bolt) {
                        imageBoardMatrix[i][j].setImage(emptyCell);
                    } // add the cell as child
                    this.add(imageBoardMatrix[i][j], j, i);
                }*/
                imageBoard[i][j].setFitWidth(cellWidth);
                imageBoard[i][j].setFitHeight(cellHeight);
            }
        }



      /*  for (int i = 0; i < matrix.size(); i++) {
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
        }*/
    }


    public String getColor1() {
        return player1.getPlayerColor();
    }
    public String getColor2() {
        return player2.getPlayerColor();
    }
    public Board getBoard() { return this.board; }

    public void setGameController(GameController controller) {
        this.controller = controller;
    }

    public String getCurrentPlayerColor() {return this.gameFlow.getCurrentPlayerColor(); }

}
