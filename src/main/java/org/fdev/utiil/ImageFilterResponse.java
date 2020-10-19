package org.fdev.utiil;

public class ImageFilterResponse<T>{

    private Status status;
    private T data;
    private String message;



    public static <K> ImageFilterResponse loading(){
        return new ImageFilterResponse<K>(Status.LOADING , null , null);
    }

    public static <K> ImageFilterResponse succes(K data , String message){
        return new ImageFilterResponse<K>(Status.SUCCESS , data , message);
    }

    public static <K> ImageFilterResponse error(String message){
        return new ImageFilterResponse<K>(Status.ERROR , null , message);
    }

    private ImageFilterResponse(Status status , T data , String message){
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
