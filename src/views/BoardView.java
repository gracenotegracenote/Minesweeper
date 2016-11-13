package views;

import javafx.scene.control.Button;
import models.Tile;

/**
 * Created by gracenote on 12-Nov-16.
 */
public class BoardView {
    public static final int TILE_SIZE = 100;
    public static final int BOARD_WIDTH = 9;

    private Button[][] tiles;

    public BoardView() {
        tiles = new Button[BOARD_WIDTH][BOARD_WIDTH];

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                Button btn = new Button();
                btn.setLayoutX(x * TILE_SIZE);
                btn.setLayoutY(y * TILE_SIZE);
                btn.setPrefWidth(TILE_SIZE);
                btn.setPrefHeight(TILE_SIZE);

                tiles[x][y] = btn;
            }
        }
    }

    public Button[][] getButtons() {
        return tiles;
    }
}
