package com.romans.visitsmart.networking.handler;

import com.romans.visitsmart.networking.traffic.Response;

import java.util.ArrayList;

/**
 * Created by Romans on 01/04/14.
 */
public interface NetworkHandler {

    String ON_GET_CATEGORIES = "onCategoriesReceived";

    void onCategoriesReceivedSuccess(ArrayList categories);
    void onCategoriesReceivedFailed(Response response);
}
