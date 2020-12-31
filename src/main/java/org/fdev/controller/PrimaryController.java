package org.fdev.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;
import org.fdev.App;
import org.fdev.business_layer.ImageProcessType;
import org.fdev.business_layer.ProcessImageInteractors;
import org.fdev.business_layer.SaveImage;
import org.fdev.business_layer.SaveImageCallback;
import org.fdev.business_layer.noise.NoiseBaseProcessor;
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
    public Menu menuNoise;
    public Menu menuEdgeDetection;
    public BorderPane kernelContainer;
    public TextField kernelEditText;
    public Pane intensityContainer;
    public Slider intensitySlider;
    public Text intesityValue;
    public Button changeImage;


    private ProcessImageInteractors imageProcesInteractors = new ProcessImageInteractors();

    private final SaveImage saveImage = new SaveImage(this);



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUI();
        initListener();
    }

    private void initListener() {
        imageProcesInteractors = new ProcessImageInteractors();
        imageProcesInteractors.imageResponseProperty().addListener((observableValue, oldValue, newValue) -> {
            imageFilterStateChange(newValue);
        });
        imageProcesInteractors.observableCurrentFilter().addListener((observableValue, oldValue, newValue) ->{
            filterNameTV.setText(newValue.name());
            if(newValue.tpye() == ImageProcessType.TYPE_FILTER  || newValue.tpye() == ImageProcessType.TYPE_MORPHOLOGY) {
                kernelContainer.setVisible(true);
                intensityContainer.setVisible(false);
            }else if(newValue.tpye() == ImageProcessType.TYPE_NOISE){
                intensitySlider.setValue(((NoiseBaseProcessor)newValue).getIntensity());
                kernelContainer.setVisible(false);
                intensityContainer.setVisible(true);
            }else{
                kernelContainer.setVisible(false);
                intensityContainer.setVisible(false);
            }
        });
        progressIndicator.setVisible(false);
    }

    private void initUI() {
        kernelContainer.setVisible(true);
        intensityContainer.setVisible(false);
        kernelEditText.setText(String.valueOf(3));
        kernelEditText.textProperty().addListener((observableValue, s, newValue) -> {
            try{
                warnText.setVisible(false);
                if(newValue.equals("")){
                    return;
                }
                int kernel = Integer.parseInt(newValue);
                if(kernel%2 != 0 && kernel>-1){
                    imageProcesInteractors.setCurrentKernel(kernel);
                }else{
                    warnText.setVisible(true);
                    warnText.setText("Kernel Value harus ganjil dan positif");
                }
            }catch(NumberFormatException exception){
                kernelEditText.setText(s);
                warnText.setVisible(true);
                warnText.setText("Kernel Value has to be integer");
            }
        });
        initSlider();
        initMenu();
        initEventListener();

    }

    private void initSlider() {
        intensitySlider.setMax(1);
        intensitySlider.setMin(0);
        intensitySlider.setValue(0.05);
        intensitySlider.setShowTickLabels(true);
        intensitySlider.setShowTickMarks(true);
        intensitySlider.setMajorTickUnit(0.5);
        intensitySlider.setBlockIncrement(0.001);
        intensitySlider.valueProperty().addListener((observableValue, number, newValue) -> {
            intesityValue.setText(newValue.toString());
            imageProcesInteractors.setCurrentIntesity(newValue.doubleValue());
        });
    }

    private void initMenu() {
        initFileMenu();
        initInteractorMenu();
    }


    private void initEventListener() {
        warnText.setVisible(false);
        controlButton.setOnAction(e -> {
            warnText.setVisible(false);
            imageProcesInteractors.filterImage();
        });
        
        exportImage.setOnAction(e -> {
            exportImage();
        });

        changeImage.setOnAction(e -> {
            makeAsMainImage();
        });

    }


    private void exportImage() {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("save image");
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("png", "*.png")
        );
        File file = filechooser.showSaveDialog(null);
        if(file != null){
            saveImage.saveImage(imageAfter.getImage() , file);
        }
    }

    private void initInteractorMenu() {
        imageProcesInteractors.getFilters().forEach(item -> {
            MenuItem menu = new MenuItem(item.name());
            menu.setOnAction(e->{
                imageProcesInteractors.setCurrentProcessType(item);
            });
            menuFilter.getItems().add(menu);
        });

        imageProcesInteractors.getNoises().forEach(item -> {
            MenuItem menu = new MenuItem(item.name());
            menu.setOnAction(e->{
                imageProcesInteractors.setCurrentProcessType(item);
            });
            menuNoise.getItems().add(menu);
        });

        imageProcesInteractors.getEdgeDetecions().forEach(item -> {
            MenuItem menu = new MenuItem(item.name());
            menu.setOnAction(e->{
                imageProcesInteractors.setCurrentProcessType(item);
            });
            menuEdgeDetection.getItems().add(menu);
        });

        imageProcesInteractors.getMorphologies().forEach(item -> {
            MenuItem menu = new MenuItem(item.name());
            menu.setOnAction(e->{
                imageProcesInteractors.setCurrentProcessType(item);
            });
            menuMorphology.getItems().add(menu);
        });
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
                new FileChooser.ExtensionFilter("png", "*.png")
        );
        try {
            File file = fc.showOpenDialog(null);
            if (file != null) {
                imageProcesInteractors.setCurrentFile(file.getAbsolutePath());
                Image image = new Image(file.toURI().toString());
                imageBefore.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void makeAsMainImage(){
        if(this.imageAfter != null && this.imageBefore != null){
            this.imageBefore.setImage(this.imageAfter.getImage());
        }else{
            warnText.setText("Please filter some photo first");
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
                progressIndicator.progressProperty().bind(imageProcesInteractors.taskProgressPropery().progressProperty());
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
