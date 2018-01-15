package fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import app.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by shaytzir on 14/01/2018.
 */
public class GameController implements Initializable {
    private GeneralPlayer player1;
    private GeneralPlayer player2;
    private Board board;
    private GameFlow flow;
    private ReversiRules logic;
    @FXML
    private HBox root;
    @FXML
    private Label curP;
    @FXML
    private Label player1Score;
    @FXML
    private Label player2Score;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSettings.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SettingsController setControl = loader.getController();
        int size = setControl.getSize();
        String player1Color = setControl.getFirstColor();
        String player2Color = setControl.getSecondColor();

        this.player1 = new HumanP(player1Color);
        this.player2 = new HumanP(player2Color);

        BoardController guiBoard = new BoardController(size, player1, player2);
        guiBoard.setPrefWidth(300);
        guiBoard.setPrefHeight(350);
        root.getChildren().add(0, guiBoard);

        this.logic = new ReversiRules(guiBoard, player1, player2);
        this.flow = new GameFlow(logic, this, guiBoard);

        this.draw();

        guiBoard.setGameFlow(flow);
        guiBoard.setGameController(this);

        guiBoard.draw();
    }

    public void gameOver(String winner) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("GAME OVER");
        String message;
        if (winner.equals("Tie")) {
            message="It's A Tie";
        } else {
            message= "The winner is " + winner;
        }
        alert.setContentText(message);
        alert.showAndWait();
        return;
    }

    public void noMovesForCurrentPlayer() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Moves");
        alert.setContentText("No Moves For " + flow.getCurrentPlayerColor());
        alert.showAndWait();
        return;

    }


    public void draw() {
        curP.setText("curent player is " + flow.getCurrentPlayerColor());
        player1Score.setText(player1.getPlayerColor()+" Player Score: " + player1.getScore());
        player2Score.setText(player2.getPlayerColor() +" Player Score: " + player2.getScore());

    }

    @FXML
    protected void endGame(){

        Stage s = (Stage)root.getScene().getWindow();
        s.close();
    }
}
