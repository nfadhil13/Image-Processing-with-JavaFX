package org.fdev.business_layer.morphology;

import java.util.ArrayList;
import java.util.List;

public class MorphologyInteractors {


    private Closing closingMorphology = new Closing();
    private Dilation dilationMorphology = new Dilation();
    private Erosion erosionMorphology = new Erosion();
    private Opening openingMorphology = new Opening();
    private List<MorphologyBaseProcessor> morphologies= new ArrayList<>();

    public MorphologyInteractors() {
        morphologies.add(closingMorphology);
        morphologies.add(dilationMorphology);
        morphologies.add(erosionMorphology);
        morphologies.add(openingMorphology);
    }

    public List<MorphologyBaseProcessor> getMorphologies() {
        return morphologies;
    }
}
