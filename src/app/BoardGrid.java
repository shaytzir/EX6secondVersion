package myapp;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Created by shaytzir on 15/01/2018.
 */
public class BoardGrid extends StackPane {
    private Point location;
    private String color;
    private Circle circle;
    private char sign;
    private BoardController gui;

    public BoardGrid(int x, int y, String color, BoardController gui) {
        location = new Point();
        location.x = x;
        location.y = y;
        this.color = color;
        this.circle = new Circle();
        this.circle.setFill(Paint.valueOf(color.toUpperCase()));
        this.getChildren().add(this.circle);
        this.sign = color.charAt(0);
        this.gui = gui;
    }

    public void draw() {
        gui.getChildren().add(this);
    }


}
