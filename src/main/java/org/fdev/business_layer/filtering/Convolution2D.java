package org.fdev.business_layer.filtering;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

import static org.opencv.core.Core.BORDER_DEFAULT;
import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.core.CvType.CV_8U;

public class Convolution2D extends FilterBaseProcessor {

    private static final String SUCCESS_FILTER = "Convolution2D Filter Success";
    private static final String FAIL_FILTER = "Convolution2D Filter Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";


    public ImageFilterResponse filter(String filepath){
        try{
            if(!filepath.equals("")){
                Imgcodecs imageCodecs = new Imgcodecs();
                Mat src = imageCodecs.imread(filepath);
                Mat dst = new Mat();
                Mat mat = Mat.eye(this.getKernelSize(),this.getKernelSize(),CV_32FC1);
                Point anchor = new Point(-1,-1);
                Imgproc.filter2D(src, dst, CV_8U, mat, anchor, 0, BORDER_DEFAULT);
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
        return "Convolution2D Filter";
    }

}
