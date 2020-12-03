package org.fdev.business_layer.filtering;

import java.util.ArrayList;
import java.util.List;

public class FilterInteractors {

    private BilateralFilter bilateralFilter = new BilateralFilter();
    private Convolution2D convolution2D = new Convolution2D();
    private Averaging averagingFilter = new Averaging();
    private GaussianBlurFilter gaussianBlurFilter = new GaussianBlurFilter();
    private MedianBlur medianBlur = new MedianBlur();
    private Laplacian laplacian = new Laplacian();
    private List<FilterBaseProcessor> filters = new ArrayList<>();


    public FilterInteractors() {
        filters.add(bilateralFilter);
        filters.add(convolution2D);
        filters.add(averagingFilter);
        filters.add(gaussianBlurFilter);
        filters.add(medianBlur);
        filters.add(laplacian);
    }

    public List<FilterBaseProcessor> getFilters() {
        return filters;
    }
}
