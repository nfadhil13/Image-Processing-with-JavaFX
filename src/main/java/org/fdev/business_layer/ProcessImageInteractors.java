package org.fdev.business_layer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import org.fdev.App;
import org.fdev.business_layer.filtering.*;
import org.fdev.business_layer.morphology.Closing;
import org.fdev.business_layer.morphology.Dilation;
import org.fdev.business_layer.morphology.Erosion;
import org.fdev.business_layer.morphology.Opening;
import org.fdev.utiil.ImageFilterResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessImageInteractors {

    //Filetring
    public static final int BILATERAL_BLUR_FILTER = 1;
    public static final int GAUSSIAN_BLUR_FILTER = 2;
    public static final int BLUR_FILTER = 3;
    public static final int COMMON_FILTER = 4;
    public static final int MEDIAN_BLUR = 5;

    //Morphology
    public static final int CLOSING_MORPHOLOGY =  21;
    public static final int DILATION_MORPHOLOGY =  22;
    public static final int EROSION_MORPHOLOGY =  23;
    public static final int OPENING_MORPHOLOGY =  24;

    private Task<ImageFilterResponse> task;


    private BilateralFilter bilateralFilter = new BilateralFilter();
    private CommonFilter commonFilter = new CommonFilter();
    private BlurFilter blurFilter = new BlurFilter();
    private GaussianBlurFilter gaussianBlurFilter = new GaussianBlurFilter();
    private MedianBlur medianBlur = new MedianBlur();

    private Closing closingMorphology = new Closing();
    private Dilation dilationMorphology = new Dilation();
    private Erosion erosionMorphology = new Erosion();
    private Opening openingMorphology = new Opening();


    private SimpleStringProperty filterName;
    private SimpleObjectProperty<ImageFilterResponse> imageResponse = new SimpleObjectProperty<>();


    private String currentFile = "";
    private BaseProcessor currentFilter;


    public ProcessImageInteractors() {
        initDummyTask();
        currentFilter = commonFilter;
        filterName = new SimpleStringProperty(currentFilter.name());
    }


    public void filterImage() {
        if (!currentFile.equals("")) {
            if (!isTaskActive()) {
                doFilter(getCurrentFilter());
            } else {
                imageResponse.set(ImageFilterResponse.error("Your filtering still running"));
            }
        } else {
            imageResponse.set(ImageFilterResponse.error("To filter you need to input image"));
        }
    }

    private void doFilter(BaseProcessor baseFilter) {
        task = new Task<>() {
            @Override
            protected ImageFilterResponse call() throws Exception {
                return baseFilter.filter(getCurrentFile());
            }
        };
        task.setOnSucceeded(e -> {
            imageResponse.set(task.getValue());
        });

        imageResponse.set(ImageFilterResponse.loading());
        ExecutorService executorService
                = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();
    }


    private void initDummyTask() {
        Task<ImageFilterResponse> filterTask = new Task<>() {
            @Override
            protected ImageFilterResponse call() throws Exception {
                return null;
            }
        };
    }

    private Boolean isTaskActive() {
        if (task == null) return false;
        if (task.isRunning()) return true;
        return false;
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }

    public BaseProcessor getCurrentFilter() {
        return currentFilter;
    }


    public void setCurrentProcessType(int filterMode) {
        switch (filterMode) {
            case BILATERAL_BLUR_FILTER:
                setCurrentProcessType(bilateralFilter);
                break;
            case GAUSSIAN_BLUR_FILTER:
                setCurrentProcessType(gaussianBlurFilter);
                break;
            case COMMON_FILTER:
                setCurrentProcessType(commonFilter);
                break;
            case BLUR_FILTER:
                setCurrentProcessType(blurFilter);
                break;
            case MEDIAN_BLUR:
                setCurrentProcessType(medianBlur);
                break;
            case CLOSING_MORPHOLOGY:
                setCurrentProcessType(closingMorphology);
                break;
            case OPENING_MORPHOLOGY:
                setCurrentProcessType(openingMorphology);
                break;
            case DILATION_MORPHOLOGY:
                setCurrentProcessType(dilationMorphology);
                break;
            case EROSION_MORPHOLOGY:
                setCurrentProcessType(erosionMorphology);
                break;
        }
    }

    private void setCurrentProcessType(BaseProcessor currentFilter) {
        this.currentFilter = currentFilter;
        App.println(currentFilter.name());
        filterName.set(currentFilter.name());
    }

    public SimpleObjectProperty<ImageFilterResponse> imageResponseProperty() {
        return imageResponse;
    }

    public String getFilterName() {
        return filterName.get();
    }

    public SimpleStringProperty filterNameProperty() {
        return filterName;
    }

    public Task taskProgressPropery(){
        return this.task;
    }
}



