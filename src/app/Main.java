package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            GridPane root = FXMLLoader.load(getClass().getResource("../fxml/FXMLMenu.fxml"));
            Scene scene = new Scene(root,600,500);
            primaryStage.setTitle("Reversi");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("something;s fucked up");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
