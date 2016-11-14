package views;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Tile;

/**
 * Created by gracenote on 14-Nov-16.
 */
public class Flag extends ImageView {
    private static final String URL = "images/sapper.png";

    public Flag(boolean visible) {
        super(URL);

        if (visible) {
            setFitWidth(Tile.SIZE - 20);
            setFitHeight(Tile.SIZE - 20);
            setVisible(true);
        } else {
            setVisible(false);
        }
    }
}
