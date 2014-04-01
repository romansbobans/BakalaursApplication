package com.romans.visitsmart.networking;

import android.os.AsyncTask;
import com.romans.visitsmart.networking.handler.NetworkHandler;
import com.romans.visitsmart.networking.traffic.Response;
import com.romans.visitsmart.networking.traffic.ResponseCode;
import com.romans.visitsmart.networking.traffic.Type;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;

/**
 * Created by Romans on 01/04/14.
 */
public class NetworkRequest<T> {

    private static final String BASE_URL = "AS";
    private final String callbackMethod;
    private final AsyncTask task;
    private final NetworkHandler handler;
    private final String link;

    public NetworkRequest(NetworkHandler handler, String link, String callbackMethod, Type type) {
        this.handler = handler;
        this.link = link;
        this.callbackMethod = callbackMethod;

        switch (type)
        {
            case GET:
                task = new GetTask();
                break;
            case POST:
                task = new PostTask();
                break;
            default:
                task = null;
                assert (false) : "Shouldn't happen, check your enum constants " + type;
        }
    }

    public static <T> NetworkRequest createGetRequest(NetworkHandler handler, String link, String callbackMethod) {
        return new NetworkRequest<T>(handler, link, callbackMethod, Type.GET);
    }


    public void execute() {
        task.execute();
    }

    private class GetTask extends AsyncTask<Void, Void, Response> {
        @Override
        protected Response doInBackground(Void... params) {
            try
            {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(BASE_URL + link);
                HttpResponse response = client.execute(get);
                String responseString = getResponse(response.getEntity().getContent());
                return new Response(ResponseCode.OK, responseString, null);
            }
            catch (UnknownHostException e)
            {
                return new Response(ResponseCode.UNKNOWN_HOST, null, "Unable to connect to network");
            }
            catch (Exception e)
            {
                //Shouldn't happen, programmatic error;
                assert false:"Wrong URL: " + BASE_URL + link;
            }

            return null;
        }
    }

    private class PostTask extends AsyncTask<Void, Void, Response> {
        @Override
        protected Response doInBackground(Void[] params) {
            return null;
        }

        @Override
        protected void onPostExecute(Response o) {
            super.onPostExecute(o);

            callCallBack(o);
        }
    }

    private String getResponse(InputStream stream) throws IOException
    {
        int c;
        StringBuilder builder = new StringBuilder();
        while ((c = stream.read()) != -1)
        {
            builder.append(c);
        }
        return builder.toString();
    }

    private void callCallBack(Response<T> result)
    {
        String method = callbackMethod + (result.getReturnObject() != null ? "Success" : "Failed");
        Object obj = result.getReturnObject() != null ? result.getReturnObject() : result;
       // DevLog.d("OBJECT: " + obj.getClass());
        try {
            Method m = handler.getClass().getDeclaredMethod(method, obj.getClass());
            m.invoke(handler, obj);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot find callback:" + method + "  with parameters: " + obj.getClass().getSimpleName());
        }
    }


}
