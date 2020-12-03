package org.fdev.business_layer;

import org.fdev.utiil.ImageFilterResponse;

public interface BaseProcessor {

    public ImageFilterResponse filter(String filePath);
    public String name();
    public ImageProcessType tpye();
}
