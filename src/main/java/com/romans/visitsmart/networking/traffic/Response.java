package com.romans.visitsmart.networking.traffic;

/**
 * Created by Romans on 01/04/14.
 */
public class Response<T> {

    String responseMessage;
    ResponseCode code;
    T object;



    public Response(ResponseCode ok, T response, String errorMessage) {

    }

    public T getReturnObject() {
        return object;
    }
}
