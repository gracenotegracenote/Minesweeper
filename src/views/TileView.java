package views;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.Tile;


/**
 * Created by gracenote on 12-Nov-16.
 */
public class TileView extends ImageView {
    public static final int SIZE = 100;

    public TileView (double x, double y, int bombsCount) {
        super(getImageURL(bombsCount));
        relocate(x, y);
        setFitWidth(SIZE);
        setFitHeight(SIZE);
    }

    private static String getImageURL (int bombsCount) {
        String imageUrl;
        switch(bombsCount) {
            case 0: imageUrl = "images/0.png"; break;
            case 1: imageUrl = "images/1.png"; break;
            case 2: imageUrl = "images/2.png"; break;
            case 3: imageUrl = "images/3.png"; break;
            case 4: imageUrl = "images/4.png"; break;
            case 5: imageUrl = "images/5.png"; break;
            case 6: imageUrl = "images/6.png"; break;
            case 7: imageUrl = "images/7.png"; break;
            case 8: imageUrl = "images/8.png"; break;
            default: return null;
        }

        return imageUrl;
        //return new ImageView(imageUrl);
    }
}
