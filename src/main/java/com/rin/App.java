package com.rin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {
    protected static Scene scene;
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage stage) {
        String fxmlFileName = "main";
        try {
            scene = new Scene(loadFXML(fxmlFileName));
        } catch (IOException e) {
            logger.error("FXML file " + fxmlFileName + " not found", e);
        }
        stage.setScene(scene);
        stage.setTitle("2048 Bikes");
        try {
            stage.getIcons().add(new Image(("file:src/main/resources/com/rin/icons/title-icon.png")));
        } catch (Exception e) {
            logger.error("Icon not found", e);
        }
        stage.show();
        logger.info("Application started!");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        Parent parent = null;
        FXMLLoader fxmlLoader = null;
        URL fxmlPath = App.class.getResource("/com/rin/" + fxml + ".fxml");
        if (fxmlPath != null) {
            fxmlLoader = new FXMLLoader(fxmlPath);
        } else {
            logger.error(fxml + ".fxml not found");
        }
        parent = fxmlLoader.load();

        return parent;
    }

    public static void main(String[] args) {
        launch();
    }
}