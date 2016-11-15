package models;

import sample.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gracenote on 12-Nov-16.
 */
public class Board {
    private Tile[][] tiles;

    public Board() {
        tiles = new Tile[Main.BOARD_WIDTH][Main.BOARD_WIDTH];

        for (int x = 0; x < Main.BOARD_WIDTH; x++) {
            for (int y = 0; y < Main.BOARD_WIDTH; y++) {
                tiles[x][y] = new Tile(x, y, false);
            }
        }

        BombGenerator bombGen = new BombGenerator();
        int[] xCoord = bombGen.getxCoordinates();
        int[] yCoord = bombGen.getyCoordinates();

        for (int i = 0; i < xCoord.length; i++) {
            tiles[xCoord[i]][yCoord[i]].setBomb(true);
        }
    }


    int callCount = 0;
    //TODO: algorithm efficient?
    public List<Tile> getEmptyTilesAround(int x, int y) {
        List<Tile> emptyTilesAround = new ArrayList<>();

        if (tiles[x][y].isChecked()) {
            return new ArrayList<>();
        }

        tiles[x][y].setChecked(true);

        //tile is empty when no bombs around
        Tile[] neighbours = {getTileDown(x, y), getTileLeft(x, y), getTileRight(x, y), getTileUp(x, y)};

        //tiles[i] global, not local
        for (Tile neighbour : neighbours) {
            if (neighbour != null) {
                int xIndex = neighbour.getXIndex();
                int yIndex = neighbour.getYIndex();

                if (!tiles[xIndex][yIndex].isChecked()) {
                    emptyTilesAround.add(neighbour);
                    if (getBombsAround(xIndex, yIndex) == 0) {
                        emptyTilesAround.addAll(getEmptyTilesAround(xIndex, yIndex));
                    }
                }
            }
        }

        return emptyTilesAround;
    }

    public void resetChecked() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].setChecked(false);
            }
        }
    }

    public int getBombsAround(int x, int y) {
        //exclusion of tiles with bombs
        if (tiles[x][y].hasBomb()) return -1;

        int sum = 0;

        if (getTileBottomLeft(x, y) != null && getTileBottomLeft(x, y).hasBomb()) sum++;
        if (getTileDown(x, y) != null && getTileDown(x, y).hasBomb()) sum++;
        if (getTileBottomRight(x, y) != null && getTileBottomRight(x, y).hasBomb()) sum++;
        if (getTileLeft(x, y) != null && getTileLeft(x, y).hasBomb()) sum++;
        if (getTileRight(x, y) != null && getTileRight(x, y).hasBomb()) sum++;
        if (getTileBelowLeft(x, y) != null && getTileBelowLeft(x, y).hasBomb()) sum++;
        if (getTileUp(x, y) != null && getTileUp(x, y).hasBomb()) sum++;
        if (getTileBelowRight(x, y) != null && getTileBelowRight(x, y).hasBomb()) sum++;

        return sum;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    private Tile getTileBottomLeft(int x, int y) {
        if (y == 0 || x == 0) return null;

        return tiles[x - 1][y - 1];
    }

    private Tile getTileDown(int x, int y) {
        if (y == 0) return null;

        return tiles[x][y - 1];
    }

    private Tile getTileBottomRight(int x, int y) {
        if (y == 0 || x == tiles[0].length - 1) return null;

        return tiles[x + 1][y - 1];
    }

    private Tile getTileLeft(int x, int y) {
        if (x == 0) {
            return null;
        }

        return tiles[x - 1][y];
    }

    private Tile getTileRight(int x, int y) {
        if (x == tiles[0].length - 1) {
            return null;
        }

        return tiles[x + 1][y];
    }

    private Tile getTileBelowLeft(int x, int y) {
        if (y == tiles.length - 1 || x == 0) return null;

        return tiles[x - 1][y + 1];
    }

    private Tile getTileUp(int x, int y) {
        if (y == tiles.length - 1) return null;

        return tiles[x][y + 1];
    }

    private Tile getTileBelowRight(int x, int y) {
        if (y == tiles.length - 1 || x == tiles.length - 1) return null;

        return tiles[x + 1][y + 1];
    }
}
