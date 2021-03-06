package org.fdev.business_layer.morphology;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

import static org.opencv.core.Core.BORDER_DEFAULT;

public class Closing extends MorphologyBaseProcessor {

    private static final String SUCCESS_PROCESS = "Closing success";
    private static final String FAIL_PROCESS = "Closing Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";

    @Override
    public ImageFilterResponse filter(String filePath) {
        try{
            if(!filePath.equals("")){
                Mat src = Imgcodecs.imread(filePath);
                Mat dst = new Mat();
                Mat kernel = Imgproc.getStructuringElement( Imgproc.CV_SHAPE_RECT, new Size(getKernelSize(),getKernelSize()));
                Imgproc.morphologyEx(src , dst , Imgproc.MORPH_CLOSE , kernel);
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
