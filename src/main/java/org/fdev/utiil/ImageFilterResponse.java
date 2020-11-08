package org.fdev.utiil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ImageFilterResponse{

    private Status status;
    private ByteArrayInputStream data;
    private String message;



    public static <K> ImageFilterResponse loading(){
        return new ImageFilterResponse(Status.LOADING , null , null);
    }

    public static <K> ImageFilterResponse succes(ByteArrayInputStream data , String message){
        return new ImageFilterResponse(Status.SUCCESS , data , message);
    }

    public static <K> ImageFilterResponse error(String message){
        return new ImageFilterResponse(Status.ERROR , null , message);
    }

    private ImageFilterResponse(Status status , ByteArrayInputStream data , String message){
        this.status = status;
        this.data =data;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ByteArrayInputStream getData() {
        return data;
    }

    public void setData(ByteArrayInputStream data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
