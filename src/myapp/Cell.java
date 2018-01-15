package myapp;

import java.util.*;
public class Cell {
    //Column.
    public int x;
    //Row.
    public int y;
    //All the cells that the player flips if he choose this cell.
    Vector <Point> flip;
    public Cell() {
        this.flip = new Vector<>();
    }
}
