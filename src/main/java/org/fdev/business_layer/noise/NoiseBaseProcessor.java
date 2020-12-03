package org.fdev.business_layer.noise;

import org.fdev.App;
import org.fdev.business_layer.BaseProcessor;
import org.fdev.business_layer.ImageProcessType;

public abstract class NoiseBaseProcessor implements BaseProcessor {
    private double intensity = 0.05;

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        App.println("" + intensity);
        this.intensity = intensity;
    }

    @Override
    public ImageProcessType tpye() {
        return ImageProcessType.TYPE_NOISE;
    }
}
