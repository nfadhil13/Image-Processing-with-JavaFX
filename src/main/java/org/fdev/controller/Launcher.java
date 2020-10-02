package org.fdev.controller;

import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import nu.pattern.OpenCV;
import org.fdev.App;
import org.fdev.util.TextLoadingAnimation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Launcher implements Initializable {

    private static final String  LOADING_OPENCV = "Loading OpenCV";

    public ProgressIndicator progressIndicator;
    public Label loadingText;


    private TextLoadingAnimation loadingTextAnimation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initOpenCV();
    }

    private void initOpenCV() {
        loadingTextAnimation = new TextLoadingAnimation(loadingText , LOADING_OPENCV);

        Task<Void> loadOpenCV  = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                OpenCV.loadLocally();
                return null;
            }
        };

        loadOpenCV.setOnSucceeded(e-> {
            loadingTextAnimation.stopAnimation();
            navToPrimaryController();
        });
        progressIndicator.progressProperty().bind(loadOpenCV.progressProperty());
        loadingTextAnimation.startLoadingTask();
        new Thread(loadOpenCV).start();
    }

    private void navToPrimaryController(){
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
