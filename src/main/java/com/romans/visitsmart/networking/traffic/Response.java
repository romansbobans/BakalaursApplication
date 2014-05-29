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

    public T getReturnObject() {
        return object;
    }
}
