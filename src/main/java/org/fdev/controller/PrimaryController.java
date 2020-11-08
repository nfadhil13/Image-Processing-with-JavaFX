package org.fdev.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
import org.fdev.business_layer.ProcessImageInteractors;
import org.fdev.business_layer.SaveImage;
import org.fdev.business_layer.SaveImageCallback;
import org.fdev.utiil.ImageFilterResponse;

public class PrimaryController implements Initializable, SaveImageCallback {

    public Menu menuFile;
    public ProgressIndicator progressIndicator;
    public ImageView imageBefore;


    public ImageView imageAfter;

    public Text filterNameTV;
    public Menu menuFilter;
    public Button controlButton;
    public Text warnText;
    public Menu menuMorphology;
    public Button exportImage;

    private MenuItem bilateralFilter,blurFilter,commonFIlter,gaussianBlur,medianBlur, closingMorph , dilationMorph , erosionMorph , openingMorph;


    private ProcessImageInteractors filterInteractors;


    private SaveImage saveImage = new SaveImage(this);



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUI();
        initListener();
    }

    private void initListener() {
        filterInteractors = new ProcessImageInteractors();
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
        initMorphologyMenu();
    }

    private void initEventListener() {
        controlButton.setOnAction(e -> {
            warnText.setVisible(false);
            filterInteractors.filterImage();
        });
        
        exportImage.setOnAction(e -> {
            exportImage();
        });

    }

    private void exportImage() {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("save image");
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg", "*.jpg")
        );
        File file = filechooser.showSaveDialog(null);
        if(file != null){
            saveImage.saveImage(imageAfter.getImage() , file);
        }
    }

    private void initFilterMenu() {
        //Bilateral Menu
        bilateralFilter = new MenuItem("Bilateral Blur");
        bilateralFilter.setOnAction(e -> {
            filterInteractors.setCurrentProcessType(ProcessImageInteractors.BILATERAL_BLUR_FILTER);
            App.println("bilateralFilter clicked");
        });


        //Blur filter menu
        blurFilter = new MenuItem("Blur Filter");
        blurFilter.setOnAction(e -> {
            filterInteractors.setCurrentProcessType(ProcessImageInteractors.BLUR_FILTER);
            App.println("blurFilter clicked");
        });



        //Common filter
        commonFIlter = new MenuItem("Common Filter");
        commonFIlter.setOnAction(e-> {
            filterInteractors.setCurrentProcessType(ProcessImageInteractors.COMMON_FILTER);
            App.println("common filter clicked");
        });


        //Gaussian Filter
        gaussianBlur = new MenuItem("Gaussian Blur ");
        gaussianBlur.setOnAction(e -> {
            filterInteractors.setCurrentProcessType(ProcessImageInteractors.GAUSSIAN_BLUR_FILTER);
            App.println("gaussianBlur clicked");
        });


        //Median filter
        medianBlur = new MenuItem("Median Blur");
        medianBlur.setOnAction(e -> {
            filterInteractors.setCurrentProcessType(ProcessImageInteractors.MEDIAN_BLUR);
            App.println("medianBlur clicked");
        });
        menuFilter.getItems().add(blurFilter);
        menuFilter.getItems().add(commonFIlter);
        menuFilter.getItems().add(gaussianBlur);
        menuFilter.getItems().add(bilateralFilter);
        menuFilter.getItems().add(medianBlur);
    }

    private void initMorphologyMenu(){
        //Bilateral Menu
        closingMorph = new MenuItem("Closing");
        closingMorph.setOnAction(e -> {
            filterInteractors.setCurrentProcessType(ProcessImageInteractors.CLOSING_MORPHOLOGY);
        });

        openingMorph = new MenuItem("Opening");
        openingMorph.setOnAction(e -> {
            filterInteractors.setCurrentProcessType(ProcessImageInteractors.OPENING_MORPHOLOGY);
        });

        dilationMorph = new MenuItem("Dilation");
        dilationMorph.setOnAction(e -> {
            filterInteractors.setCurrentProcessType(ProcessImageInteractors.DILATION_MORPHOLOGY);
        });

        erosionMorph = new MenuItem("Erosion");
        erosionMorph.setOnAction(e -> {
            filterInteractors.setCurrentProcessType(ProcessImageInteractors.EROSION_MORPHOLOGY);
        });

        menuMorphology.getItems().add(closingMorph);
        menuMorphology.getItems().add(dilationMorph);
        menuMorphology.getItems().add(erosionMorph);
        menuMorphology.getItems().add(openingMorph);
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
        fc.setTitle("Open Image");
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
                imageAfter.setImage(new Image(response.getData()));
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


    @Override
    public void onSaveSuccess(String message) {
        App.println(message);
    }

    @Override
    public void onSaveError(String message) {
        App.println(message);
    }
}
