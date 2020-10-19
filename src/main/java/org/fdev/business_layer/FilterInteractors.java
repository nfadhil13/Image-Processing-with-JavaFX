package org.fdev.business_layer;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import org.fdev.App;
import org.fdev.utiil.ImageFilterResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilterInteractors {

    public static final int BILATERAL_BLUR_FILTER = 1;
    public static final int GAUSSIAN_BLUR_FILTER = 2;
    public static final int BLUR_FILTER = 3;
    public static final int COMMON_FILTER = 4;
    public static final int MEDIAN_BLUR = 5;


    private Task<ImageFilterResponse> task;
    private BilateralFilter BilateralFilter = new BilateralFilter();
    private CommonFilter commonFilter = new CommonFilter();
    private BlurFilter blurFilter = new BlurFilter();
    private GaussianBlurFilter gaussianBlurFilter = new GaussianBlurFilter();
    private MedianBlur medianBlur = new MedianBlur();


    private SimpleStringProperty filterName;
    private SimpleObjectProperty<ImageFilterResponse> imageResponse = new SimpleObjectProperty<>();


    private String currentFile = "";
    private BaseFilter currentFilter;

    public FilterInteractors() {
        initDummyTask();
        currentFilter = commonFilter;
        filterName = new SimpleStringProperty(currentFilter.filterName());
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

    private void doFilter(BaseFilter baseFilter) {
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

    public BaseFilter getCurrentFilter() {
        return currentFilter;
    }


    public void setCurrentFilter(int filterMode) {
        switch (filterMode) {
            case BILATERAL_BLUR_FILTER:
                setCurrentFilter(BilateralFilter);
                break;
            case GAUSSIAN_BLUR_FILTER:
                setCurrentFilter(gaussianBlurFilter);
                break;
            case COMMON_FILTER:
                setCurrentFilter(commonFilter);
                break;
            case BLUR_FILTER:
                setCurrentFilter(blurFilter);
                break;
            case MEDIAN_BLUR:
                setCurrentFilter(medianBlur);
                break;
        }
    }

    private void setCurrentFilter(BaseFilter currentFilter) {
        this.currentFilter = currentFilter;
        App.println(currentFilter.filterName());
        App.println(this.currentFilter.filterName() );
        filterName.set(currentFilter.filterName());
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



