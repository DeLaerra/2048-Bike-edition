package com.rin;

import com.rin.controller.WinMessageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    protected static Scene scene;
    private static final Logger logger = LoggerFactory.getLogger(App.class);


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"));
        stage.setScene(scene);
        stage.setTitle("2048 Bikes");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/rin/icons/title-icon.png")));
        stage.show();

        logger.info("Application started!");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}