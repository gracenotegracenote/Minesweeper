package models;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by gracenote on 29-Oct-16.
 */
public class Tile { //extends Rectangle { implements Comparable<Tile> {
    //TODO: replace to view class/controller
    public static final int SIZE = 100;

    private int x;
    private int y;
    private boolean hasBomb;
    private boolean hasNumber;
    private boolean checked;

    public Tile (int xIndex, int yIndex, boolean hasBomb) {
        this.x = xIndex;
        this.y = yIndex;

        /*setWidth(SIZE);
        setHeight(SIZE);
        relocate(x * SIZE, y * SIZE);
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        setStrokeWidth(SIZE * 0.03);*/

        this.hasBomb = hasBomb;
        hasNumber = false;
        checked = false;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void setBomb(boolean b) {
        hasBomb = b;
    }

    public int getXIndex() {
        return x;
    }

    public int getYIndex() {
        return y;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /*@Override
    public int compareTo(Tile tile) {
        if (this.x == tile.x && this.y == tile.y) return 0;
        return -1;
    }*/
}
