package org.fdev.business_layer.edge_detection;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.business_layer.ImageProcessType;

public abstract class EdgeDetectionBaseProcessor implements BaseProcessor {
    @Override
    public ImageProcessType tpye() {
        return ImageProcessType.TYPE_EDGE_DETECTION;
    }
}
