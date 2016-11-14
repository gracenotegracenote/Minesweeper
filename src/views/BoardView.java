package views;

import javafx.scene.control.Button;
import sample.Main;

/**
 * Created by gracenote on 12-Nov-16.
 */
public class BoardView {
    public static final int TILE_SIZE = 100; //!!!

    private Button[][] tiles;

    public BoardView() {
        tiles = new Button[Main.BOARD_WIDTH][Main.BOARD_WIDTH];

        for (int x = 0; x < Main.BOARD_WIDTH; x++) {
            for (int y = 0; y < Main.BOARD_WIDTH; y++) {
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
