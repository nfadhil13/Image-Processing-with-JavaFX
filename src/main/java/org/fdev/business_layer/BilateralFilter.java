package org.fdev.business_layer;

import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

import static org.opencv.core.Core.BORDER_DEFAULT;

public class BilateralFilter implements BaseFilter {

    private static final String SUCCESS_FILTER = "Bilateral Filter Success";
    private static final String FAIL_FILTER = "Bilateral Filter Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";

    public ImageFilterResponse filter(String filepath){
        try{
            if(!filepath.equals("")){
                Imgcodecs imageCodecs = new Imgcodecs();
                Mat src = imageCodecs.imread(filepath);
                Mat dst = new Mat();
                Imgproc.cvtColor(src , src , Imgproc.COLOR_RGBA2RGB , 0);
                Imgproc.bilateralFilter(src , dst , 9 , 75 ,75 , BORDER_DEFAULT);
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
    public String filterName() {
        return "Bilateral Filter";
    }

}
