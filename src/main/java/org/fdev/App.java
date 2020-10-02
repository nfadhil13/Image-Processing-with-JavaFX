package org.fdev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nu.pattern.OpenCV;

import java.io.IOException;
import java.net.URL;
import java.nio.file.OpenOption;

/**
 * JavaFX App
 */


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("launcher"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL fxmlDestination = App.class.getResource(fxml +".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlDestination);
        return fxmlLoader.load();
    }

    private static void println(String test) {
        System.out.println(test);
    }

    public static void main(String[] args) {
        launch();
    }

}