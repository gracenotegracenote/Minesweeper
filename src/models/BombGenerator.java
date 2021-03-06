package models;

import sample.Main;

import java.util.Random;

/**
 * Created by gracenote on 30-Oct-16.
 */
public class BombGenerator {
    public static final String IMAGE_URL = "images/bomb.jpg"; //TODO: to view class

    private int bombCount;
    private int[] xCoordinates;
    private int[] yCoordinates;

    public BombGenerator() {
        bombCount = getBombCount();
        xCoordinates = new int[bombCount];
        yCoordinates = new int[bombCount];
        generateBombs();
    }

    private void generateBombs() {
        Random generator = new Random();

        for (int i = 0; i < getBombCount(); i++) {
            int x = generator.nextInt(Main.BOARD_WIDTH);
            int y = generator.nextInt(Main.BOARD_WIDTH);

            xCoordinates[i] = x;
            yCoordinates[i] = y;
        }
    }

    //TODO: to count percentage of bombs
    public int getBombCount() {
        if (Main.BOARD_WIDTH == 9) {
            return 10;
        }

        return 1;
    }

    public int[] getxCoordinates() {
        return xCoordinates;
    }

    public int[] getyCoordinates() {
        return yCoordinates;
    }
}
