package org.fdev.business_layer;

import javafx.concurrent.Task;
import org.fdev.utiil.FilterCallback;
import org.fdev.utiil.ImageFilterResponse;

public class FilterInteractors {

    public static final int BILATERAL_BLUR_FILTER = 1;
    public static final int GAUSSIAN_BLUR_FILTER = 2;
    public static final int BLUR_FILTER = 3;
    public static final int COMMON_FILTER = 4;
    public static final int MEDIAN_BLUR = 5;


    private FilterCallback filterCallback;
    private Task<ImageFilterResponse> task;
    private BilateralFilter BilateralFilter = new BilateralFilter();
    private CommonFilter commonFilter = new CommonFilter();
    private BlurFilter blurFilter = new BlurFilter();
    private GaussianBlurFilter gaussianBlurFilter = new GaussianBlurFilter();
    private MedianBlur medianBlur = new MedianBlur();

    public FilterInteractors(FilterCallback filterCallback) {
        this.filterCallback = filterCallback;
        initDummyTask();
    }


    public void filterImage(String filtepath, int mode) {
        if(!isTaskActive()){
            switch (mode) {
                case BILATERAL_BLUR_FILTER:
                    doFilter(BilateralFilter, filtepath);
                    break;
                case GAUSSIAN_BLUR_FILTER:
                    doFilter(gaussianBlurFilter , filtepath);
                    break;
                case COMMON_FILTER:
                    doFilter(commonFilter, filtepath);
                case BLUR_FILTER :
                    doFilter(blurFilter , filtepath);
                case MEDIAN_BLUR :
                    doFilter(medianBlur , filtepath);
            }
        }else{
            filterCallback.imageFilterStateChange(ImageFilterResponse.error("Your filtering still running"));
        }

    }

    private void doFilter(BaseFilter baseFilter , String filepath){
        task = new Task<>() {
            @Override
            protected ImageFilterResponse call() throws Exception {
                filterCallback.imageFilterStateChange(ImageFilterResponse.loading());
                Thread.sleep(3000);
                return baseFilter.filter(filepath);
            }
        };
        task.setOnSucceeded(e -> {
            filterCallback.imageFilterStateChange(task.getValue());
        });
        new Thread(task).start();
    }


    private void initDummyTask() {
        Task<ImageFilterResponse> filterTask = new Task<>() {
            @Override
            protected ImageFilterResponse call() throws Exception {
                return null;
            }
        };
    }

    private Boolean isTaskActive(){
        if(task == null)return false;
        if(task.isRunning()) return true;
        return false;
    }


}
