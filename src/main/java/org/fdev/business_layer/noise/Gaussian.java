package org.fdev.business_layer.noise;

import org.fdev.App;
import org.fdev.business_layer.BaseProcessor;
import org.fdev.utiil.ImageFilterResponse;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;
import java.util.Random;

public class Gaussian extends NoiseBaseProcessor {

    private static final String SUCCESS_PROCESS = "Gaussian success";
    private static final String FAIL_PROCESS = "Gaussian Fail";
    private static final String FILEPATH_EMPTY = "Filepath should not empty";

    @Override
    public ImageFilterResponse filter(String filePath) {
        try{
            if(!filePath.equals("")){
                //Baca gambar
                Mat src = Imgcodecs.imread(filePath);
                //Buat matriks untuk menampung noise
                Mat noise = new Mat(src.size(), src.type());
                // Membuat matriks untuk mean
                MatOfDouble mean = new MatOfDouble ();
                // Membuat matriks untuk standar deviasi
                MatOfDouble dev = new MatOfDouble ();

                //Menghitung mean dan standar deviasi dari src , dan hasil dimasukan ke matriks yang telah didefnisikan
                Core.meanStdDev(src,mean,dev);

                App.println(" " + mean.get(0,0)[0] + " / " + dev.get(0,0)[0]);

                //Membuat matrix random
                Core.randn(noise,mean.get(0,0)[0], dev.get(0,0)[0]);

                for(int i=0 ; i < noise.rows(); i++){
                    for(int j=0 ; j<noise.cols(); j++){
                        Random random = new Random();
                        double randomPlot = random.nextDouble();

                        double[] data_noise = noise.get(i,j);
                        if(randomPlot > this.getIntensity()) {
                            data_noise[0] = 0;
                            data_noise[1] = 0;
                            data_noise[2] = 0;
                        }
                        noise.put(i,j, data_noise);
                    }
                }
                Core.add(src, noise, src);
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

    @Override
    public String name() {
        return "Gaussian Noise";
    }
}
