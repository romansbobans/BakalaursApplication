package com.romans.visitsmart.activities;

import android.app.Activity;
import android.os.Bundle;
import com.romans.visitsmart.networking.BaseRequest;
import com.romans.visitsmart.networking.handler.NetworkHandler;
import com.romans.visitsmart.networking.traffic.Response;

import java.util.ArrayList;

public class BaseActivity extends Activity implements NetworkHandler
{

    BaseRequest request;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        createRequest();
    }

    void createRequest()
    {
        request = new BaseRequest(this);
    }





    @Override
    public void onCategoriesReceivedSuccess(ArrayList categories) {

    }

    @Override
    public void onCategoriesReceivedFailed(Response response) {

    }
}
