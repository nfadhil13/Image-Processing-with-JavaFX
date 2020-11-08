package org.fdev.business_layer;

public interface SaveImageCallback{
    void onSaveSuccess(String message);
    void onSaveError(String message);
}
