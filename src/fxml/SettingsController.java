package fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    Text messageText;
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    ChoiceBox<String> choiceBox1;
    @FXML
    ChoiceBox<String> choiceBox2;
    @FXML
    ChoiceBox<String> choiceBox3;

    /**
     * not sure if needed, maybe the choiceboxes are enough already
     */
    @FXML
    String gameTheme;
    @FXML
    String player1C;
    @FXML
    String player2C;
    @FXML
    int size;


    /**
     * dealing with clicking the "save" button, writing to file the updated settings
     */
    @FXML
    protected void save() {
        //extrcat the input settings of the user
        String theme = choiceBox.getSelectionModel().getSelectedItem();
        String player1Color = choiceBox1.getSelectionModel().getSelectedItem();
        String player2Color = choiceBox2.getSelectionModel().getSelectedItem();
        String size = choiceBox3.getSelectionModel().getSelectedItem();
        if (theme == null) {
            Alert themeAlert = new Alert(Alert.AlertType.ERROR);
            themeAlert.setContentText
                    ("You Have To Choose A Theme!");
            themeAlert.showAndWait();
            return;
        }

        if ((player1Color == null) || (player2Color == null)) {
            Alert colorAlert = new Alert(Alert.AlertType.ERROR);
            colorAlert.setContentText
                    ("Missing Color For Players");
            colorAlert.showAndWait();
            return;
        }

        if (size == null ) {
            Alert sizeAlert = new Alert(Alert.AlertType.ERROR);
            sizeAlert.setContentText
                    ("You Have To Choose Size For The Board");
            sizeAlert.showAndWait();
            return;
        }

        //if both players chose the same color, pop an alert
        if (player1Color.equals(player2Color)) {
            Alert sameColors = new Alert(Alert.AlertType.ERROR);
            sameColors.setContentText
                    ("Both Players Can't Use The Same Color\n change one of them/The game will use last Settings");
            sameColors.showAndWait();
            return;
        }
        //update the settings file with the new settings
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("settings.txt"));
            writer.write(theme);
            writer.newLine();
            writer.write(player1Color);
            writer.newLine();
            writer.write(player2Color);
            writer.newLine();
            writer.write(size);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * going back to the main menu - can be called if the player doesnt want to edit the settings
     * or after saving new settings
     */
    protected void back() {
        try {
            Stage stage = (Stage)choiceBox1.getScene().getWindow();
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
            Scene scene = new Scene(root, 600, 500);
            stage.setTitle("Reversi");
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex) {

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String fileName = "settings.txt";
        readFile(fileName);
        save();
    }


    /**
     * trying to read settings from file, if they dont exists, setting default settings.
     * @param name name of the settings file
     */
    public void readFile(String name) {
        File settings = new File(name);
        if (settings.exists()) {
            try {
                //reader initialize all choice boxes to hold the last settings that were edited
                BufferedReader reader = new BufferedReader(new FileReader("settings.txt"));
                String line = reader.readLine();
                if (line == null) {
                    throw new Exception("Empty File");
                }
                this.gameTheme = line;
                choiceBox.setValue(line);
                line = reader.readLine();
                if (line == null) {
                    throw new Exception("Settings aren't valid");
                }
                player1C = line;
                choiceBox1.setValue(line);
                line = reader.readLine();
                if (line == null) {
                    throw new Exception("Settings aren't valid");
                }
                player2C = line;
                choiceBox2.setValue(line);
                line = reader.readLine();
                if (line == null) {
                    throw new Exception("Settings aren't valid");
                }
                size = Integer.parseInt(line);
                choiceBox3.setValue(line);
                reader.close();
            } catch (Exception e) {
                System.out.println("Exception in SettingsController");
            }

        } else {
            this.gameTheme = "Donut";
            this.choiceBox.setValue(gameTheme);
            this.player1C = "Black";
            this.choiceBox1.setValue(player1C);
            this.player2C = "White";
            this.choiceBox2.setValue(player2C);
            this.size = 8;
            this.choiceBox3.setValue(Integer.toString(size));
        }
    }




    /**
     *
     * @return the size of the board
     */
    public int getSize(){
        return this.size;
    }

    /**
     *
     * @return color of the first player
     */
    public String getFirstColor() {
        return this.player1C;
    }

    /**
     *
     * @return color of the second player
     */
    public String getSecondColor() {return this.player2C; }

    /**
     *
     * @return the theme of the game
     */
    public String getGameTheme() {return this.gameTheme; }

}
