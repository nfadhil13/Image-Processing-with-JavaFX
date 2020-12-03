package org.fdev.business_layer.noise;

import java.util.ArrayList;
import java.util.List;

public class NoiseInteractors {

    private Gaussian gaussianNoise = new Gaussian();
    private SaltAndPepper saltAndPepperNoise = new SaltAndPepper();
    private Speckle speckle = new Speckle();

    private List<NoiseBaseProcessor> noises = new ArrayList<>();

    public NoiseInteractors() {
        noises.add(gaussianNoise);
        noises.add(saltAndPepperNoise);
        noises.add(speckle);
    }

    public List<NoiseBaseProcessor> getNoises() {
        return noises;
    }
}
