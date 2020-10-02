package org.fdev.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class PrimaryController implements Initializable {

    public Menu menuFile;
    public ImageView imagePreview;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUI();
    }

    private void initUI() {
        initMenu();
    }

    private void initMenu() {
        MenuItem menuItemAddFile = new MenuItem("Open Image");

        menuItemAddFile.setOnAction(e -> {
            openFileExplorer();
            testOpenCV();
        });
        menuFile.getItems().add(menuItemAddFile);

    }

    private void openFileExplorer() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg" , "*.jpg")
        );
        try {
            File file = fc.showOpenDialog(null);
            if(file != null){
                Image image = new Image(file.toURI().toString());
                imagePreview.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testOpenCV(){
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("mat = " + mat.dump());
    }
}
