package org.fdev.business_layer;

import org.fdev.utiil.ImageFilterResponse;

interface BaseFilter {

    public ImageFilterResponse filter(String filePath);
}
