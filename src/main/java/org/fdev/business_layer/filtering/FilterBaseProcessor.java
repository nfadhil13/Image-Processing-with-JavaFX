package org.fdev.business_layer.filtering;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.business_layer.ImageProcessType;


public abstract class  FilterBaseProcessor  implements BaseProcessor {
    private int kernelSize = 3;

    public int getKernelSize() {
        return kernelSize;
    }

    public void setKernelSize(int kernelSize) {
        this.kernelSize = kernelSize;
    }

    @Override
    public ImageProcessType tpye() {
        return ImageProcessType.TYPE_FILTER;
    }
}
