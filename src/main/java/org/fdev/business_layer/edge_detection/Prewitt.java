package org.fdev.business_layer.edge_detection;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

import static org.opencv.core.Core.BORDER_DEFAULT;

public class Prewitt extends EdgeDetectionBaseProcessor {

    private static final String SUCCESS_PROCESS = "Prewitt success";
    private static final String FAIL_PROCESS = "Prewitt Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";

    @Override
    public ImageFilterResponse filter(String filePath) {
        try{
            if(!filePath.equals("")){
                Mat src = Imgcodecs.imread(filePath);
                Mat gray = new Mat();
                Imgproc.cvtColor(src , gray , Imgproc.COLOR_BGR2GRAY);
                Mat gauss = new Mat();

                Imgproc.GaussianBlur(gray , gauss, new Size(3,3) , 0 ,0 , BORDER_DEFAULT );
                Mat dest = new Mat();
                Mat kernelX = new Mat();
//                kernelX.put(0,0)
//
//                Imgproc.filter2D(gauss,dest , );

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
