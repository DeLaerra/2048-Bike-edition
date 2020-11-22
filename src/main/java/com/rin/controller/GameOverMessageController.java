package com.rin.controller;

import com.rin.App;
import com.rin.model.Game2048;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

public class GameOverMessageController {
    protected static Scene scene;
    private final Game2048 newGame = Game2048.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(GameOverMessageController.class);

    @FXML
    Button tryAgainButton;

    @FXML
    private void tryAgain() {
        tryAgainButton.getScene().getWindow().hide();
    }

    @FXML
    public void start(Stage stage) {
        String fxmlFileName = "game-over-message";
        try {
            scene = new Scene(App.loadFXML("game-over-message"));
        } catch (IOException e) {
            logger.error("FXML file " + fxmlFileName + " not found", e);
        }
        stage.setScene(scene);
        stage.setTitle("2048 Bikes");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/rin/icons/title-icon.png")));
        stage.show();
    }

    @FXML
    void gameOver() {
        newGame.setGameStopped(true);
        start(new Stage());
    }
}
