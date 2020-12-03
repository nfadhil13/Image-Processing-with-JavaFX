package org.fdev.business_layer.edge_detection;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

public class Sobel extends EdgeDetectionBaseProcessor{

    private static final String SUCCESS_PROCESS = "Sobel success";
    private static final String FAIL_PROCESS = "Sobel Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";

    @Override
    public ImageFilterResponse filter(String filePath) {
        try{
            if(!filePath.equals("")){
                Mat src = Imgcodecs.imread(filePath);
                Mat dest = new Mat();
                Imgproc.Sobel(src , dest , -1 , 0 , 1);

                MatOfByte buffer = new MatOfByte();
                Imgcodecs.imencode(".png",dest , buffer);
                return ImageFilterResponse.succes(new ByteArrayInputStream(buffer.toArray()), SUCCESS_PROCESS);
            }else{
                return ImageFilterResponse.error(FILEPATH_EMPTY);
            }
        }catch(Exception exception){
            String errorMessage = exception.getMessage();
            return ImageFilterResponse.error(FAIL_PROCESS + "\n cause : \n\n" +  errorMessage);
        }
    }

    @Override
    public String name() {
        return "Sobel edge detection";
    }
}
