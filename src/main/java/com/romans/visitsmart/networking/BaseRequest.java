package com.romans.visitsmart.networking;

import com.romans.visitsmart.networking.handler.NetworkHandler;

import java.net.URL;

/**
 * Created by Romans on 01/04/14.
 */
public class BaseRequest {

    private static final String CATEGORIES = "raw/categories";
    NetworkHandler handler;
    public BaseRequest(NetworkHandler handler) {
        this.handler = handler;
    }

    public void getAllCategories()
    {
        NetworkRequest.createGetRequest(handler, CATEGORIES, NetworkHandler.ON_GET_CATEGORIES).execute();
    }
}
