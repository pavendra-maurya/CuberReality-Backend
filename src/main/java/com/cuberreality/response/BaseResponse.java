package com.cuberreality.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class  BaseResponse<T> {

    @JsonProperty("data")
    private T data;

    @JsonProperty("error")
    private ErrorResponse error;

    @JsonProperty("server_time")
    private String serverTime ;

    public  BaseResponse (T data,ErrorResponse error,String serverTime){
            this.data=data;
            this.error=error;
            this.serverTime=serverTime;
    }

    public  BaseResponse (T data,String serverTime){
        this.data=data;
        this.serverTime=serverTime;
    }

   public BaseResponse (ErrorResponse error,String serverTime){
        this.error=error;
        this.serverTime=serverTime;
    }

    BaseResponse (ErrorResponse error){
        this.error=error;
    }


}
