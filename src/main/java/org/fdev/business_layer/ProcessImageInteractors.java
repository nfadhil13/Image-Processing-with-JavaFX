package org.fdev.business_layer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import org.fdev.business_layer.edge_detection.EdgeDetectionBaseProcessor;
import org.fdev.business_layer.edge_detection.EdgeDetectionInteractors;
import org.fdev.business_layer.filtering.FilterBaseProcessor;
import org.fdev.business_layer.filtering.FilterInteractors;
import org.fdev.business_layer.morphology.MorphologyBaseProcessor;
import org.fdev.business_layer.morphology.MorphologyInteractors;
import org.fdev.business_layer.noise.NoiseBaseProcessor;
import org.fdev.business_layer.noise.NoiseInteractors;
import org.fdev.utiil.ImageFilterResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.fdev.business_layer.ImageProcessType.TYPE_NOISE;

public class ProcessImageInteractors {

    private Task<ImageFilterResponse> task;

    private String currentFile = "";

    private double currentIntesity;
    private int currentKernel;

    private final NoiseInteractors noiseInteractors = new NoiseInteractors();
    private final MorphologyInteractors morphologyInteractors = new MorphologyInteractors();
    private final FilterInteractors filterInteractors = new FilterInteractors();
    private final EdgeDetectionInteractors edgeDetectionInteractors = new EdgeDetectionInteractors();


    private final SimpleObjectProperty<ImageFilterResponse> imageResponse = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<BaseProcessor> currentFilter = new SimpleObjectProperty<>();

    public ProcessImageInteractors() {
        initDummyTask();
        setCurrentProcessType(filterInteractors.getFilters().get(0));

    }



    public void filterImage() {
        if (!currentFile.equals("")) {
            if (!isTaskActive()) {
                switch (getCurrentFilter().tpye()){
                    case TYPE_NOISE :
                        ((NoiseBaseProcessor)getCurrentFilter()).setIntensity(currentIntesity);
                        break;
                    case TYPE_FILTER:
                        ((FilterBaseProcessor)getCurrentFilter()).setKernelSize(currentKernel);
                        break;
                    case TYPE_MORPHOLOGY:
                        ((MorphologyBaseProcessor)getCurrentFilter()).setKernelSize(currentKernel);
                        break;
                }
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
        return task.isRunning();
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(String currentFile) {

        this.currentFile = currentFile;
    }

    public BaseProcessor getCurrentFilter() {
        return this.currentFilter.getValue();
    }

    public void setCurrentIntesity(){

    }

    public void setCurrentIntesity(double currentIntesity) {
        this.currentIntesity = currentIntesity;
    }

    public void setCurrentProcessType(BaseProcessor currentFilter) {
        if(getCurrentFilter() != null){
            switch (getCurrentFilter().tpye()){
                case TYPE_NOISE :
                    currentIntesity = ((NoiseBaseProcessor)getCurrentFilter()).getIntensity();
                    break;
                case TYPE_FILTER:
                    currentKernel = ((FilterBaseProcessor)getCurrentFilter()).getKernelSize();
                    break;
                case TYPE_MORPHOLOGY:
                    currentKernel = ((MorphologyBaseProcessor)getCurrentFilter()).getKernelSize();
                    break;
            }
        }
        this.currentFilter.set(currentFilter);
    }

    public SimpleObjectProperty<ImageFilterResponse> imageResponseProperty() {
        return imageResponse;
    }


    public SimpleObjectProperty<BaseProcessor> observableCurrentFilter() {
        return this.currentFilter;
    }

    public Task taskProgressPropery(){
        return this.task;
    }

    public void setCurrentKernel(int currentKernel) {
        this.currentKernel = currentKernel;
    }

    public List<FilterBaseProcessor> getFilters(){
        return filterInteractors.getFilters();
    }

    public List<NoiseBaseProcessor> getNoises(){
        return noiseInteractors.getNoises();
    }

    public List<MorphologyBaseProcessor> getMorphologies(){
        return morphologyInteractors.getMorphologies();
    }
    public List<EdgeDetectionBaseProcessor> getEdgeDetecions(){
        return edgeDetectionInteractors.getEdgeDetections();
    }
}



