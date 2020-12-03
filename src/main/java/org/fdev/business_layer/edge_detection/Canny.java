package org.fdev.business_layer.edge_detection;

import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

public class Canny extends EdgeDetectionBaseProcessor {

    private static final String SUCCESS_PROCESS = "Canny success";
    private static final String FAIL_PROCESS = "Canny Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";

    @Override
    public ImageFilterResponse filter(String filePath) {
        try{
            if(!filePath.equals("")){
                Mat src = Imgcodecs.imread(filePath);
                Mat gray = new Mat(src.rows(), src.cols(), src.type());
                Mat edges = new Mat(src.rows(), src.cols(), src.type());

                //Converting the image to Gray
//                Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY);
//                //Blurring the image
//                Imgproc.blur(gray, edges, new Size(3, 3));
                //Detecting the edges
                Imgproc.Canny(src, edges, 100, 300);

                Mat result = new Mat(src.rows(), src.cols(), src.type());
                //Copying the detected edges to the destination matrix
                src.copyTo(result, edges);
                MatOfByte buffer = new MatOfByte();
                Imgcodecs.imencode(".png",result , buffer);
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
        return "Canny Edge";
    }
}
