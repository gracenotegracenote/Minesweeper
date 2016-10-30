package views;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by gracenote on 29-Oct-16.
 */
public class Tile extends Rectangle {
    public static final int SIZE = 100;

    private boolean hasBomb;

    public Tile (int x, int y, boolean hasBomb) {
        setWidth(SIZE);
        setHeight(SIZE);
        relocate(x * SIZE, y * SIZE);
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        setStrokeWidth(SIZE * 0.03);
        this.hasBomb = hasBomb;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void setBomb(boolean b) {
        hasBomb = b;
    }
}
