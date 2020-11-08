package org.fdev.business_layer.morphology;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

public class Dilation implements BaseProcessor {


    private static final String SUCCESS_PROCESS = "Dilataion success";
    private static final String FAIL_PROCESS = "Dilataion Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";

    @Override
    public ImageFilterResponse filter(String filePath) {
        try{
            if(!filePath.equals("")){
                Mat src = Imgcodecs.imread(filePath);
                Mat dst = new Mat();
                Mat kernel = Mat.ones(5, 5, CvType.CV_32F);
                Imgproc.morphologyEx(src , dst , Imgproc.MORPH_DILATE , kernel);
                MatOfByte buffer = new MatOfByte();
                Imgcodecs.imencode(".png",dst , buffer);
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
        return "Closing Morphology";
    }
}
