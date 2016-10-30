package sample;

import javafx.scene.image.ImageView;
import views.BombGenerator;
import views.Tile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

        //tiles
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                tileBoard[x][y] = new Tile(x, y, false);
            }
        }

        //bombs
        BombGenerator bombGen = new BombGenerator();
        int[] xCoord = bombGen.getxCoordinates();
        int[] yCoord = bombGen.getyCoordinates();

        for (int i = 0; i < Main.BOARD_WIDTH; i++) {
            tileBoard[xCoord[i]][yCoord[i]].setBomb(true);
        }

        //btns
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                Button btn = new Button();
                btn.setLayoutX(x * Tile.SIZE);
                btn.setLayoutY(y * Tile.SIZE);
                btn.setPrefWidth(Tile.SIZE);
                btn.setPrefHeight(Tile.SIZE);

                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int x = (int) btn.getLayoutX();
                        int y = (int) btn.getLayoutY();
                        //Tile tile = new Tile(x, y, false);
                        Tile tile = tileBoard[x / Tile.SIZE][y / Tile.SIZE];
                        if (tile.hasBomb()) {
                            ImageView img = new ImageView(BombGenerator.IMAGE_URL);
                            img.setLayoutX(x);
                            img.setLayoutY(y);
                            img.setFitWidth(Tile.SIZE);
                            img.setFitHeight(Tile.SIZE);

                            group.getChildren().add(img);

                            gameOver();
                        } else {
                            group.getChildren().add(tile);
                        }
                    }
                });

                btnBoard[x][y] = btn;
                group.getChildren().add(btn);
            }
        }

        return root;
    }

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
}
