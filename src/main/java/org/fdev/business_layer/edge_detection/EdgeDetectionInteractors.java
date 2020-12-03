package org.fdev.business_layer.edge_detection;

import java.util.ArrayList;
import java.util.List;

public class EdgeDetectionInteractors {

    private Canny cannyEdgeDetection = new Canny();
    private Sobel sobel = new Sobel();
    private List<EdgeDetectionBaseProcessor> edgeDetections = new ArrayList<>();

    public EdgeDetectionInteractors(){
        edgeDetections.add(cannyEdgeDetection);
        edgeDetections.add(sobel);
    }

    public List<EdgeDetectionBaseProcessor> getEdgeDetections() {
        return edgeDetections;
    }
}
