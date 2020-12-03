package org.fdev.business_layer.noise;

import org.fdev.App;
import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;
import java.util.Random;

import static org.opencv.core.Core.randu;
import static org.opencv.core.CvType.CV_8U;

public class SaltAndPepper extends NoiseBaseProcessor {

    private static final String SUCCESS_PROCESS = "SaltAndPepper success";
    private static final String FAIL_PROCESS = "SaltAndPepper Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";

    @Override
    public ImageFilterResponse filter(String filePath) {
        try{
            if(!filePath.equals("")){
                Mat src = Imgcodecs.imread(filePath);
                for(int i=0 ; i < src.rows(); i++){
                    for(int j=0 ; j<src.cols(); j++){
                        Random random = new Random();
                        double randomPlot = random.nextDouble();

                        double[] data = src.get(i , j);

                        double blackIntensity = this.caculateIntensity() + 0.00;
                        double whiteIntesity = 1.00 - this.caculateIntensity();
                        if(randomPlot < blackIntensity){
                            App.println("item");
                            data[0] = 0;
                            data[1] = 0;
                            data[2] = 0;
                        }else if(randomPlot > whiteIntesity){
                            App.println("putih");
                            data[0] = 255.0;
                            data[1] = 255.0;
                            data[2] = 255.0;
                        }
                        src.put(i,j , data);
                    }
                }
                MatOfByte buffer = new MatOfByte();
                Imgcodecs.imencode(".png",src , buffer);
                return ImageFilterResponse.succes(new ByteArrayInputStream(buffer.toArray()), SUCCESS_PROCESS);
            }else{
                return ImageFilterResponse.error(FILEPATH_EMPTY);
            }
        }catch(Exception exception){
            String errorMessage = exception.getMessage();
            return ImageFilterResponse.error(FAIL_PROCESS + "\n cause : \n\n" +  errorMessage);
        }
    }

    private double caculateIntensity(){
        return this.getIntensity()/2;
    }

    @Override
    public String name() {
        return "Salt and Pepper Noise";
    }
}
