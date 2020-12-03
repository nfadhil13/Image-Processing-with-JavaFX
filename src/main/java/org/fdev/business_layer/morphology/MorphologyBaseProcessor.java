package org.fdev.business_layer.morphology;

import org.fdev.App;
import org.fdev.business_layer.BaseProcessor;
import org.fdev.business_layer.ImageProcessType;
import org.fdev.business_layer.ProcessImageInteractors;
public abstract class MorphologyBaseProcessor implements BaseProcessor {
    private int kernelSize = 3;

    public int getKernelSize() {
        return kernelSize;
    }

    public void setKernelSize(int kernelSize) {
        App.println(String.valueOf(kernelSize));
        this.kernelSize = kernelSize;
    }

    @Override
    public ImageProcessType tpye() {
        return ImageProcessType.TYPE_MORPHOLOGY;
    }
}
