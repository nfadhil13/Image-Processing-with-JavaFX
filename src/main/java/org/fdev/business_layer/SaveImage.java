package org.fdev.business_layer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.fdev.App;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.imageio.ImageIO;
public class SaveImage {

    private SaveImageCallback callback;

    public SaveImage(SaveImageCallback callback){
        this.callback = callback;
    }

    public void saveImage(Image image  , File file){
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image,
                    null), "png", file);
            callback.onSaveSuccess("Success Export Image");

        }catch (Exception e){
            callback.onSaveError("Failed to Export Image " + e.getMessage());
        }
    }

}


