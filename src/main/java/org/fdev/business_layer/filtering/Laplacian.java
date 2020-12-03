package org.fdev.business_layer.filtering;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

public class Laplacian extends FilterBaseProcessor {

    private static final String SUCCESS_FILTER = "Laplacian  Success";
    private static final String FAIL_FILTER = "Laplacian  Fail";
    private static final String FILEPATH_EMPTY = "File should not empty";


    public ImageFilterResponse filter(String filepath){
        try{
            if(!filepath.equals("")){
                Imgcodecs imageCodecs = new Imgcodecs();
                Mat src = imageCodecs.imread(filepath);
                Mat dst = new Mat();
                Mat abs_dst = new Mat();
                Mat src_gray = new Mat();
                Imgproc.cvtColor( src, src_gray, Imgproc.COLOR_RGB2GRAY );
                Imgproc.Laplacian(src_gray , dst , CvType.CV_16S , this.getKernelSize() , 1, 0 , Core.BORDER_DEFAULT);
                Core.convertScaleAbs( dst, abs_dst );
                MatOfByte buffer = new MatOfByte();
                Imgcodecs.imencode(".png", abs_dst , buffer);
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
        return "Laplacian Filter";
    }

}
