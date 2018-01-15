package myapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    ChoiceBox<String> choiceBox1;
    @FXML
    ChoiceBox<String> choiceBox2;
    @FXML
    ChoiceBox<String> choiceBox3;

    /**
     * not sure if needed, maybe the choiceboxes are enough already
     */
    @FXML
    String player1C;
    @FXML
    String player2C;
    @FXML
    int size;


    @FXML
    protected void save() {
        String player1Color = choiceBox1.getSelectionModel().getSelectedItem();
        String player2Color = choiceBox2.getSelectionModel().getSelectedItem();
        String size = choiceBox3.getSelectionModel().getSelectedItem();
        if (player1Color.equals(player2Color)) {
            messageText.setText("You need to choose different colors!");
            messageText.setFill(Color.BLUE);
            return;
        }



        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/settings.txt"));
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
     * or called from the save function after submitting changes
     */
    protected void back() {
        try {

            Stage stage = (Stage)choiceBox1.getScene().getWindow();
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
            Scene scene = new Scene(root, 400, 350);
            stage.setTitle("Reversi");
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex) {

        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int i;
        try {
            //reader initialize all choice boxes to hold the last settings that were edited
            BufferedReader reader = new BufferedReader(new FileReader("src/settings.txt"));
            String line = reader.readLine();
            if (line == null) {
                throw new Exception("Empty File");
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
            System.out.println("couldn't read Settings file.");
        }
    }
}
