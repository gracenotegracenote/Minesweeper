package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gracenote on 12-Nov-16.
 */
public class Board {
    public static final int BOARD_WIDTH = 9;

    private Tile[][] tiles;

    public Board() {
        tiles = new Tile[BOARD_WIDTH][BOARD_WIDTH];

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                tiles[x][y] = new Tile(x, y, false);
            }
        }

        BombGenerator bombGen = new BombGenerator();
        int[] xCoord = bombGen.getxCoordinates();
        int[] yCoord = bombGen.getyCoordinates();

        for (int i = 0; i < BOARD_WIDTH; i++) {
            tiles[xCoord[i]][yCoord[i]].setBomb(true);
        }
    }

    //TODO: algorithm efficient?
    public List<Tile> getEmptyTilesAround(int x, int y) {
        List<Tile> emptyTilesAround = new ArrayList<>();

        tiles[x][y].setChecked(true);

        //tile is empty when no bombs around
        Tile[] tiles = {getTileBottomLeft(x, y), getTileBottomCenter(x, y), getTileBottomRight(x, y),
                getTileLeft(x, y), getTileRight(x, y), getTileBelowLeft(x, y), getTileBelowCenter(x, y), getTileBelowRight(x, y)};

        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != null) {
                int xIndex = tiles[i].getXIndex();
                int yIndex = tiles[i].getYIndex();
                if (getBombsAround(xIndex, yIndex) == 0 && tiles[i].isChecked() == false) {
                    emptyTilesAround.add(tiles[i]);
                    tiles[i].setChecked(true);
                    List<Tile> list = getEmptyTilesAround(xIndex, yIndex);
                    if (list.size() > 0) emptyTilesAround.addAll(list);
                }
            }
        }

        return emptyTilesAround;
    }

    public int getBombsAround(int x, int y) {
        //exclusion of tiles with bombs
        if (tiles[x][y].hasBomb()) return -1;

        int sum = 0;

        if (getTileBottomLeft(x, y) != null && getTileBottomLeft(x, y).hasBomb()) sum++;
        if (getTileBottomCenter(x, y) != null && getTileBottomCenter(x, y).hasBomb()) sum++;
        if (getTileBottomRight(x, y) != null && getTileBottomRight(x, y).hasBomb()) sum++;
        if (getTileLeft(x, y) != null && getTileLeft(x, y).hasBomb()) sum++;
        if (getTileRight(x, y) != null && getTileRight(x, y).hasBomb()) sum++;
        if (getTileBelowLeft(x, y) != null && getTileBelowLeft(x, y).hasBomb()) sum++;
        if (getTileBelowCenter(x, y) != null && getTileBelowCenter(x, y).hasBomb()) sum++;
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

    private Tile getTileBottomCenter(int x, int y) {
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

    private Tile getTileBelowCenter(int x, int y) {
        if (y == tiles.length - 1) return null;

        return tiles[x][y + 1];
    }

    private Tile getTileBelowRight(int x, int y) {
        if (y == tiles.length - 1 || x == tiles.length - 1) return null;

        return tiles[x + 1][y + 1];
    }
}
