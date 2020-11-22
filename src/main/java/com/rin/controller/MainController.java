package com.rin.controller;

import com.rin.App;
import com.rin.model.Game2048;
import com.rin.model.Keys;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MainController {
    private final Game2048 newGame = Game2048.getInstance();

    @FXML
    private Label scoreLabel;

    @FXML
    private Label maxScoreLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane grid;

    @FXML
    public void start() throws IOException {
        initialize();
        App.setRoot("main");
    }

    @FXML
    private void initialize() {
        showScore(0);
        newGame.createGame();
        drawScene();
        anchorPane.requestFocus();
        anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPress);
    }

    @FXML
    public void showScore(int scoreValue) {
        scoreLabel.setText(Integer.toString(scoreValue));
    }

    @FXML
    public void showMaxScore(int maxScoreValue) {
        maxScoreLabel.setText(Integer.toString(maxScoreValue));
    }

    @FXML
    public void setCellColor(int x, int y, Image image) {
        if (image != null) {
            ImageView pic = new ImageView();
            pic.setFitWidth(110);
            pic.setFitHeight(110);
            pic.setImage(image);
            grid.add(pic, x, y);
        }
    }

    public void drawScene() {
        int side = newGame.getSide();
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                Image image = newGame.getImageByValue(newGame.getGameField()[j][i]);
                if (image != null) {
                    setCellColor(i, j, image);
                }
            }
        }
    }

    @FXML
    public void onKeyPress(KeyEvent key) {
        if (newGame.getMaxTileValue() == 2048) {
            newGame.setGameStopped(true);
            WinMessageController winMessageController = new WinMessageController();
            winMessageController.win();
            return;
        } else if (!newGame.canUserMove()) {
            GameOverMessageController gameOverMessageController = new GameOverMessageController();
            gameOverMessageController.gameOver();
            return;
        }

        if (!newGame.isGameStopped()) {
            if (key.getCode().getName().equals(Keys.LEFT.key)) {
                newGame.moveLeft();
            } else if (key.getCode().getName().equals(Keys.RIGHT.key)) {
                newGame.moveRight();
            } else if (key.getCode().getName().equals(Keys.UP.key)) {
                newGame.moveUp();
            } else if (key.getCode().getName().equals(Keys.DOWN.key)) {
                newGame.moveDown();
            }
            drawScene();
            showScore(newGame.getScore());
        }
    }

    @FXML
    protected void startNewGame() {
        newGame.setGameStopped(false);
        compareMaxScore();
        showScore(0);
        newGame.setScore(0);
        newGame.createGame();
        drawScene();
    }

    private void compareMaxScore() {
        int maxScore = newGame.getMaxScore();
        int score = newGame.getScore();

        if (score > maxScore) {
            newGame.setMaxScore(score);
        }
        if (newGame.getMaxScore() != 0) {
            showMaxScore(newGame.getMaxScore());
        }
    }
}
