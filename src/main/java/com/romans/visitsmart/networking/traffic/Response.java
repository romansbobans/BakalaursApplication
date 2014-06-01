package com.romans.visitsmart.networking.traffic;

/**
 * Created by Romans on 01/04/14.
 */
public class Response<T> {

    String errorMessage;
    ResponseCode code;
    T object;

    public Response(String errorMessage, ResponseCode code, T object) {
        this.errorMessage = errorMessage;
        this.code = code;
        this.object = object;
    }

    public ResponseCode getCode() {
        return code;
    }

    public boolean isValid()
    {
        return code == ResponseCode.OK;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }

    public T getReturnObject() {
        return object;
    }
}
