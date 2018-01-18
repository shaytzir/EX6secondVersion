package fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * MenuController.
 * runs all actions from Menu FXML
 */
public class MenuController {

    @FXML
    private Button btnStart;

    @FXML
    private Button btnSetting;


    /**
     * dealing with clicking on the "start game" button
     */
    @FXML
    protected void startBtnClicked() {
        try {
           Stage stage = (Stage) btnStart.getScene().getWindow();
            HBox root = (HBox)FXMLLoader.load(getClass().getResource("FXMLGame.fxml"));
            Scene scene = new Scene(root,600,500);
            stage.setTitle("Reversi game");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {

        }
    }

    /**
     * dealing with clicking on the setting button
     */
    @FXML
    protected void settingsBtnClicked() {
        try {
            Stage stage = (Stage)btnSetting.getScene().getWindow();
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("FXMLSettings.fxml"));
            Scene scene = new Scene(root, 600, 500);
            stage.setTitle("Reversi");
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex) {

        }
    }

}