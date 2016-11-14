package sample;

import javafx.scene.input.MouseButton;
import models.Board;
import models.Tile;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import views.BoardView;
import views.Flag;
import views.TileView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Application {
    //TODO: configuration data into txt file or JSON
    public static final String GAME_NAME = "Minesweeper";
    public static final String GAME_OVER = "Game over!";

    public static final int BOARD_WIDTH = 10;
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
        Scene scene = new Scene(createContent());
        this.primaryStage = primaryStage;
        primaryStage.setTitle(GAME_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    //TODO: resizable frame
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
                Button btn = btnBoard[x][y];
                final boolean[] rightClick = {false};
                btn.setOnMouseClicked((event) -> {
                    MouseButton mouseBtn = event.getButton();

                    //left click
                    if (mouseBtn == MouseButton.PRIMARY) {
                        int xPos = (int) btn.getLayoutX();
                        int yPos = (int) btn.getLayoutY();
                        int xInd = xPos / Tile.SIZE;
                        int yInd = yPos / Tile.SIZE;
                        Tile tile = tileBoard[xInd][yInd];

                        if (tile.hasBomb()) {
                            group.getChildren().add(new TileView(xPos, yPos));
                            gameOver();
                        } else {
                            int bombsAround = board.getBombsAround(xInd, yInd);
                            TileView tileView = new TileView(xPos, yPos, bombsAround);
                            group.getChildren().add(tileView);

                            //open all empty tiles around
                            if (bombsAround == 0) {
                                List<Tile> emptyTiles = board.getEmptyTilesAround(xInd, yInd);
                                Set<TileView> tileViews = new HashSet<>();
                                for (Tile t : emptyTiles) { //collect call impossible because of different <types>
                                    tileViews.add(new TileView(t.getXIndex() * TileView.SIZE, t.getYIndex() * TileView.SIZE,
                                            board.getBombsAround(t.getXIndex(), t.getYIndex())));
                                }
                                group.getChildren().addAll(tileViews);
                            }
                        } //right click
                    } else if (mouseBtn == MouseButton.SECONDARY) {
                        Flag flag;
                        if (rightClick[0] == true) {    //value in array because rightClick is final
                            rightClick[0] = false;
                            flag = new Flag(false);
                        } else {
                            rightClick[0] = true;
                            flag = new Flag(true);
                        }

                        btn.setGraphic(flag);
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
        newGameBtn.setOnMouseClicked((event) -> {
            try {
                gameOverStage.close();
                start(primaryStage);
            } catch (Exception e) {
                System.out.println("Exception in gameOver()");
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setLayoutX(GAMEOVER_BOARD_WIDTH / 2 + 50);
        exitBtn.setLayoutY(GAMEOVER_BOARD_HEIGHT / 2 - 20);
        exitBtn.setPrefWidth(BTN_WIDTH);
        exitBtn.setOnMouseClicked((event) -> System.exit(0));

        gameOverRoot.getChildren().add(newGameBtn);
        gameOverRoot.getChildren().add(exitBtn);

        Scene gameOverScene = new Scene(gameOverRoot);

        gameOverStage.setTitle(GAME_OVER);
        gameOverStage.setScene(gameOverScene);
        gameOverStage.show();
    }
}
