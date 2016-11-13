package sample;

import javafx.scene.image.ImageView;
import models.Board;
import models.BombGenerator;
import models.Tile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import views.BoardView;
import views.TileView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Application {
    public static final String GAME_NAME = "Minesweeper";
    public static final String GAME_OVER = "Game over!";

    public static final int BOARD_WIDTH = 9;
    public static final int GAMEOVER_BOARD_WIDTH = 500;
    public static final int GAMEOVER_BOARD_HEIGHT = 100;
    public static final int BTN_WIDTH = 100;

    public Tile[][] tileBoard = new Tile[BOARD_WIDTH][BOARD_WIDTH];
    public Button[][] btnBoard = new Button[BOARD_WIDTH][BOARD_WIDTH];
    public Group group = new Group();
    public Stage primaryStage = new Stage();
    public Stage gameOverStage = new Stage();


    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(createContent());

        this.primaryStage = primaryStage;
        primaryStage.setTitle(GAME_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(Tile.SIZE * BOARD_WIDTH, Tile.SIZE * BOARD_WIDTH);
        root.getChildren().add(group);

        Board board = new Board();
        tileBoard = board.getTiles();

        BoardView boardView = new BoardView();
        btnBoard = boardView.getButtons();

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                //TODO: stronger different btw x/y in array and layoutX/layoutY
                Button btn = btnBoard[x][y];
                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int xPos = (int) btn.getLayoutX();
                        int yPos = (int) btn.getLayoutY();
                        int x = xPos / Tile.SIZE;
                        int y = yPos / Tile.SIZE;
                        Tile tile = tileBoard[x][y];

                        //TODO: replace repeated code to [tile] view class
                        if (tile.hasBomb()) {
                            ImageView img = new ImageView(BombGenerator.IMAGE_URL);
                            img.setLayoutX(xPos);
                            img.setLayoutY(yPos);
                            img.setFitWidth(Tile.SIZE);
                            img.setFitHeight(Tile.SIZE);

                            group.getChildren().add(img);

                            gameOver();
                        } else {
                            int bombsAround = board.getBombsAround(x, y);
                            TileView tileView = new TileView(xPos, yPos, bombsAround);
                            group.getChildren().add(tileView);

                            //open all empty tiles around
                            if (bombsAround == 0) {
                                //getEmptyTilesAround() belongs to model or view??
                                List<Tile> emptyTiles = board.getEmptyTilesAround(x, y);
                                Set<TileView> tileViews = new HashSet<>();
                                for (Tile t : emptyTiles) {
                                    tileViews.add(new TileView(t.getXIndex() * TileView.SIZE, t.getYIndex() * TileView.SIZE,
                                            board.getBombsAround(t.getXIndex(), t.getYIndex())));
                                }
                                group.getChildren().addAll(tileViews);
                            }
                        }
                    }
                });

                group.getChildren().add(btn);
            }
        }

        return root;
    }

    //TODO: close and exit btns -> exit game
    public void gameOver() {
        Pane gameOverRoot = new Pane();
        gameOverRoot.setPrefWidth(GAMEOVER_BOARD_WIDTH);
        gameOverRoot.setPrefHeight(GAMEOVER_BOARD_HEIGHT);

        Button newGameBtn = new Button("New Game");
        newGameBtn.setLayoutX(GAMEOVER_BOARD_WIDTH / 2 - 100);
        newGameBtn.setLayoutY(GAMEOVER_BOARD_HEIGHT / 2 - 20);
        newGameBtn.setPrefWidth(BTN_WIDTH);
        newGameBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    gameOverStage.close();
                    start(primaryStage);
                } catch (Exception e) {
                    System.out.println("Exception in gameOver()");
                }
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setLayoutX(GAMEOVER_BOARD_WIDTH / 2 + 50);
        exitBtn.setLayoutY(GAMEOVER_BOARD_HEIGHT / 2 - 20);
        exitBtn.setPrefWidth(BTN_WIDTH);
        exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });

        gameOverRoot.getChildren().add(newGameBtn);
        gameOverRoot.getChildren().add(exitBtn);

        Scene gameOverScene = new Scene(gameOverRoot);

        gameOverStage.setTitle(GAME_OVER);
        gameOverStage.setScene(gameOverScene);
        gameOverStage.show();
    }

    //pereneseno
    /*private ImageView getImageView(int x, int y) {
        int indexX = x / Tile.SIZE;
        int indexY = y / Tile.SIZE;
        Tile tile = tileBoard[indexX][indexY];

        int bombsCount = getBombsCount(tile);

        if (bombsCount <= 0) return null;

        String imageUrl;
        switch(bombsCount) {
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

        ImageView img = new ImageView(imageUrl);
        img.setLayoutX(x);
        img.setLayoutY(y);
        img.setFitWidth(Tile.SIZE);
        img.setFitHeight(Tile.SIZE);

        return img;
    }*/

    //pereneseno
    /*private int getBombsCount(Tile t) {
        //exclusion of tiles with bombs
        if (t.hasBomb()) return -1;

        int x = (int) t.getLayoutX() / Tile.SIZE;
        int y = (int) t.getLayoutY() / Tile.SIZE;
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

    private Tile getTileBottomLeft(int x, int y) {
        if (y == 0 || x == 0) return null;

        return tileBoard[x - 1][y - 1];
    }

    private Tile getTileBottomCenter(int x, int y) {
        if (y == 0) return null;

        return tileBoard[x][y - 1];
    }

    private Tile getTileBottomRight(int x, int y) {
        if (y == 0 || x == tileBoard[0].length - 1) return null;

        return tileBoard[x + 1][y - 1];
    }

    private Tile getTileLeft(int x, int y) {
        if (x == 0) {
            return null;
        }

        return tileBoard[x - 1][y];
    }

    private Tile getTileRight(int x, int y) {
        if (x == tileBoard[0].length - 1) {
            return null;
        }

        return tileBoard[x + 1][y];
    }

    private Tile getTileBelowLeft(int x, int y) {
        if (y == tileBoard.length - 1 || x == 0) return null;

        return tileBoard[x - 1][y + 1];
    }

    private Tile getTileBelowCenter(int x, int y) {
        if (y == tileBoard.length - 1) return null;

        return tileBoard[x][y + 1];
    }

    private Tile getTileBelowRight(int x, int y) {
        if (y == tileBoard.length - 1 || x == tileBoard.length - 1) return null;

        return tileBoard[x + 1][y + 1];
    }*/
}
