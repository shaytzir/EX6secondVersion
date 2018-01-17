package fxml;

import app.Cell;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import app.Board;
import app.GameFlow;
import app.GeneralPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

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

    /**
     * construcor.
     * @param size size of the board.
     * @param player1 first player
     * @param player2 second player
     */
    public BoardController(int size, GeneralPlayer player1, GeneralPlayer player2){
        this.size = size;
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board(size, size, player2.getSign(), player1.getSign());
        this.imageBoard = new ImageView[size][size];

        String theme = player1.getPlayerTheme();
        if (theme.equals("Fruit")) {
            this.firstP = new Image(getClass().getClassLoader().
                    getResourceAsStream("images/" + player1.getPlayerColor() + "2.jpg"));
            this.secondP = new Image(getClass().getClassLoader().
                    getResourceAsStream("images/" + player2.getPlayerColor() + "2.jpg"));
        } else {
            this.firstP = new Image(getClass().getClassLoader().
                    getResourceAsStream("images/" + player1.getPlayerColor() + ".jpg"));
            this.secondP = new Image(getClass().getClassLoader().
                    getResourceAsStream("images/" + player2.getPlayerColor() + ".jpg"));
        }
        this.possible = new Image(getClass().getClassLoader().
                getResourceAsStream("images/Possible.jpg"));
        this.empty = new Image(getClass().getClassLoader().
                getResourceAsStream("images/Empty.jpg"));
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
                    controller.draw();
                });
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLBoard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * set the GameFlow the board.
     * @param flow GameFlow
     */
    public void setGameFlow(GameFlow flow) {
        this.gameFlow = flow;
    }

    /**
     * draw this gui board.
     */
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
                // if the player on the board is the first player, set his image
                if (player == player1.getSign()) {
                    imageBoard[i][j].setImage(this.firstP);
                    this.add(imageBoard[i][j], j, i);
                    // if the player on the board is the second player, set his image
                } else if (player == player2.getSign()) {
                    imageBoard[i][j].setImage(this.secondP);
                    this.add(imageBoard[i][j], j, i);
                } else {
                    //check if the cell is a possible move of the current player
                    boolean possible = false;
                    for (int l = 0; l < moves.size(); l++) {
                        if ((moves.get(l).x == i) && (moves.get(l).y == j)) {
                            possible = true;
                        }
                    }
                    //if it is, set the board with the "possible" image
                    if (possible) {
                        imageBoard[i][j].setImage(this.possible);
                        //if its not, set the empty cell image
                    } else {
                        imageBoard[i][j].setImage(this.empty);
                    }
                    //add the set image
                    this.add(imageBoard[i][j],j ,i);
                }
                //update size by settings
                imageBoard[i][j].setFitWidth(cellWidth);
                imageBoard[i][j].setFitHeight(cellHeight);
            }
        }
    }

    /**
     *
     * @return the actual board of the boardController
     */
    public Board getBoard() { return this.board; }

    /**
     * set the game controller.
     * @param controller Game Controller
     */
    public void setGameController(GameController controller) {
        this.controller = controller;
    }
}
