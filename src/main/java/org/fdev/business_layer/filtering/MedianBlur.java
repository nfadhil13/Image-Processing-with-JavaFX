package org.fdev.business_layer.filtering;

import org.fdev.App;
import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

public class MedianBlur extends FilterBaseProcessor {

    private static final String SUCCESS_FILTER = "Median Blur Success";
    private static final String FAIL_FILTER = "Median Blur Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";


    public ImageFilterResponse filter(String filepath){
        try{
            if(!filepath.equals("")){
                Imgcodecs imageCodecs = new Imgcodecs();
                Mat src = imageCodecs.imread(filepath);
                App.println(" " +  src.rows() + " " + src.cols());
                Mat dst = new Mat();
                Imgproc.medianBlur(src , dst , this.getKernelSize());
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
        return "Median Blur Filtering";
    }

}
