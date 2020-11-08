package org.fdev.business_layer.filtering;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

import static org.opencv.core.Core.BORDER_DEFAULT;

public class GaussianBlurFilter implements BaseProcessor {

    private static final String SUCCESS_FILTER = "GaussianBlurFilter Success";
    private static final String FAIL_FILTER = "GaussianBlurFilter Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";


    public ImageFilterResponse filter(String filepath){
        try{
            if(!filepath.equals("")){
                Imgcodecs imageCodecs = new Imgcodecs();
                Mat src = imageCodecs.imread(filepath);
                Size ksize = new Size(3,3);
                Mat dst = new Mat();
                Imgproc.GaussianBlur(src , dst , ksize , 0 ,0 , BORDER_DEFAULT);
                MatOfByte buffer = new MatOfByte();
                Imgcodecs.imencode(".png",dst , buffer);
                return ImageFilterResponse.succes(new ByteArrayInputStream(buffer.toArray()), SUCCESS_FILTER);
            }else{
                return ImageFilterResponse.error(FILEPATH_EMPTY);
            }
        }catch(Exception exception){
            String errorMessage = exception.getMessage();
            return ImageFilterResponse.error(FAIL_FILTER + "\n cause : \n\n" +  errorMessage);
        }
    }

    @Override
    public String name() {
        return "Gaussian Blur Filter";
    }

}
