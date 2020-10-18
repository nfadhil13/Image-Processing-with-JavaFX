package org.fdev.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.fdev.App;
import org.fdev.business_layer.FilterInteractors;
import org.fdev.utiil.FilterCallback;
import org.fdev.utiil.ImageFilterResponse;

public class PrimaryController implements Initializable, FilterCallback {

    public Menu menuFile;
    public ImageView imagePreview;
    public Button testButton;
    public FilterInteractors filterInteractors;


    private String currentFile = "";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUI();
    }

    private void initUI() {
        initMenu();
        initListener();
        initObserver();
    }

    private void initObserver() {
        filterInteractors = new FilterInteractors(this);
    }

    private void initListener() {
        testButton.setOnAction(e -> {
            filterInteractors.filterImage(currentFile, FilterInteractors.MEDIAN_BLUR);
        });
    }

    private void initMenu() {
        MenuItem menuItemAddFile = new MenuItem("Open Image");

        menuItemAddFile.setOnAction(e -> {
            openFileExplorer();
        });
        menuFile.getItems().add(menuItemAddFile);

    }

    private void openFileExplorer() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("png", "*.png")
        );
        try {
            File file = fc.showOpenDialog(null);
            if (file != null) {
                currentFile = file.getAbsolutePath();
                Image image = new Image(file.toURI().toString());
                imagePreview.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void imageFilterStateChange(ImageFilterResponse response) {
        switch (response.getStatus()) {
            case SUCCESS:
                App.println("success");
                if(response.getData() instanceof ByteArrayInputStream){
                    imagePreview.setImage(new Image((ByteArrayInputStream)response.getData()));
                }
                break;
            case ERROR:
                App.println(response.getMessage());
                break;
            case LOADING:
                App.println("Loading");
                break;
        }
    }
}
