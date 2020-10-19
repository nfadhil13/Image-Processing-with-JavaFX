package org.fdev.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.fdev.App;
import org.fdev.business_layer.FilterInteractors;
import org.fdev.utiil.FilterCallback;
import org.fdev.utiil.ImageFilterResponse;

public class PrimaryController implements Initializable {

    public Menu menuFile;
    public ProgressIndicator progressIndicator;
    public ImageView imageBefore;
    public ImageView imageAfter;
    public Text filterNameTV;
    public Menu menuFilter;
    public Button controlButton;
    public Text warnText;

    private MenuItem bilateralFilter,blurFilter,commonFIlter,gaussianBlur,medianBlur;

    private FilterInteractors filterInteractors;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUI();
        initListener();
    }

    private void initListener() {
        filterInteractors = new FilterInteractors();
        filterInteractors.imageResponseProperty().addListener((observableValue, oldValue, newValue) -> {
            imageFilterStateChange(newValue);
        });
        filterInteractors.filterNameProperty().addListener(((observableValue, oldValue, newValue) -> {
            filterNameTV.setText(newValue);
        }));
        progressIndicator.setVisible(false);
    }

    private void initUI() {
        initMenu();
        initEventListener();

    }




    private void initMenu() {
        initFileMenu();
        initFilterMenu();
    }

    private void initEventListener() {
        controlButton.setOnAction(e -> {
            warnText.setVisible(false);
            filterInteractors.filterImage();
        });
    }

    private void initFilterMenu() {
        //Bilateral Menu
        bilateralFilter = new MenuItem("Bilateral Blur");
        bilateralFilter.setOnAction(e -> {
            filterInteractors.setCurrentFilter(FilterInteractors.BILATERAL_BLUR_FILTER);
            App.println("bilateralFilter clicked");
        });


        //Blur filter menu
        blurFilter = new MenuItem("Blur Filter");
        blurFilter.setOnAction(e -> {
            filterInteractors.setCurrentFilter(FilterInteractors.BLUR_FILTER);
            App.println("blurFilter clicked");
        });



        //Common filter
        commonFIlter = new MenuItem("Common Filter");
        commonFIlter.setOnAction(e-> {
            filterInteractors.setCurrentFilter(FilterInteractors.COMMON_FILTER);
            App.println("common filter clicked");
        });


        //Gaussian Filter
        gaussianBlur = new MenuItem("Gaussian Blur ");
        gaussianBlur.setOnAction(e -> {
            filterInteractors.setCurrentFilter(FilterInteractors.GAUSSIAN_BLUR_FILTER);
            App.println("gaussianBlur clicked");
        });


        //Median filter
        medianBlur = new MenuItem("Median Blur");
        medianBlur.setOnAction(e -> {
            filterInteractors.setCurrentFilter(FilterInteractors.MEDIAN_BLUR);
            App.println("medianBlur clicked");
        });
        menuFilter.getItems().add(blurFilter);
        menuFilter.getItems().add(commonFIlter);
        menuFilter.getItems().add(gaussianBlur);
        menuFilter.getItems().add(bilateralFilter);
        menuFilter.getItems().add(medianBlur);
    }

    private void initFileMenu() {
        MenuItem menuItemAddFile = new MenuItem("Open Image");
        menuItemAddFile.setOnAction(e -> {
            openFileExplorer();
        });
        menuFile.getItems().add(menuItemAddFile);
    }

    private void openFileExplorer() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg", "*.jpg")
        );
        try {
            File file = fc.showOpenDialog(null);
            if (file != null) {
                filterInteractors.setCurrentFile(file.getAbsolutePath());
                Image image = new Image(file.toURI().toString());
                imageBefore.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void imageFilterStateChange(ImageFilterResponse response) {
        switch (response.getStatus()) {
            case SUCCESS:
                App.println("success");
                progressIndicator.setVisible(false);
                if(response.getData() instanceof ByteArrayInputStream){
                    imageAfter.setImage(new Image((ByteArrayInputStream)response.getData()));
                }
                break;
            case ERROR:
                warnText.setVisible(true);
                warnText.setText(response.getMessage());
                progressIndicator.setVisible(false);
                App.println(response.getMessage());
                break;
            case LOADING:
                App.println("Loading");
                progressIndicator.setVisible(true);
                progressIndicator.progressProperty().bind(filterInteractors.taskProgressPropery().progressProperty());
                break;
        }
    }


}
