package com.romans.visitsmart.networking;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.romans.visitsmart.networking.handler.NetworkHandler;
import com.romans.visitsmart.networking.traffic.Response;
import com.romans.visitsmart.networking.traffic.ResponseCode;
import com.romans.visitsmart.networking.traffic.Type;
import com.romans.visitsmart.utils.DevLog;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;

/**
 * Created by Romans on 01/04/14.
 */
class NetworkRequest {

    private static final String BASE_URL = "http://lginvest.lv/";
    private static final int PORT_NR = 9000;
    private final String callbackMethod;
    private final AsyncTask task;
    private final NetworkHandler handler;
    private final String link;
    private final String message;

    private Class returningClass;

    private NetworkRequest(NetworkHandler handler, String link, String callbackMethod, Type type, Class returnVal) {
        this(handler, link, null, callbackMethod, type, returnVal);
    }

    private NetworkRequest(NetworkHandler handler, String link, String postMessage, String callbackMethod, Type type, Class returnVal) {
        this.handler = handler;
        this.link = link;
        this.callbackMethod = callbackMethod;
        this.message = postMessage;

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

        returningClass = returnVal;
    }

    public static NetworkRequest createGetRequest(NetworkHandler handler, String link, String callbackMethod, Class returnVal) {

        return new NetworkRequest(handler, link, callbackMethod, Type.GET, returnVal);
    }

    public static NetworkRequest createPostRequest(NetworkHandler handler, String link, String postMessage, String callbackMethod, Class returnVal) {

        return new NetworkRequest(handler, link, postMessage, callbackMethod, Type.POST, returnVal);
    }

    public void execute() {
        task.execute();
    }


    private class GetTask extends AsyncTask<Object, Void, Response> {
        @Override
        protected Response doInBackground(Object... params) {
            try
            {
                HttpClient c = new DefaultHttpClient();


                HttpGet get = new HttpGet(BASE_URL + link);
                HttpResponse response = c.execute(get);

                String responseString = getResponse(response.getEntity().getContent());
                Log.e("TAAG", responseString);
                Object returnVal = new Gson().fromJson(responseString, returningClass);
                return new Response(null, ResponseCode.OK, returnVal);// new Response(ResponseCode.OK, responseString, null);
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
                return new Response(null, ResponseCode.NO_NETWORK, null);//new Response(ResponseCode.UNKNOWN_HOST, null, "Unable to connect to network");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                //Shouldn't happen, programmatic error;

                return new Response(e.getMessage(), ResponseCode.UNKNOWN_ERROR, null);
            }

        }

        @Override
        protected void onPostExecute(Response o) {
            super.onPostExecute(o);

            callCallBack(o);
        }
    }



    private class PostTask extends AsyncTask<Object, Void, Response> {
        @Override
        protected Response doInBackground(Object[] params) {
            try
            {
                HttpClient c = new DefaultHttpClient();


                DevLog.e(message);
                HttpPost post = new HttpPost(BASE_URL + link);
                post.setEntity(new StringEntity(message));
                post.addHeader("Content-Type", "application/json");
                HttpResponse response = c.execute(post);

                String responseString = getResponse(response.getEntity().getContent());
                Log.e("TAAG", responseString);
                Object returnVal = new Gson().fromJson(responseString, returningClass);
                return new Response(null, ResponseCode.OK, returnVal);// new Response(ResponseCode.OK, responseString, null);
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
                return new Response(null, ResponseCode.NO_NETWORK, null);//new Response(ResponseCode.UNKNOWN_HOST, null, "Unable to connect to network");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                //Shouldn't happen, programmatic error;

                return new Response(e.getMessage(), ResponseCode.UNKNOWN_ERROR, null);
            }

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
        BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        while ((c = in.read()) != -1)
        {
            builder.append((char)c);
        }
        return builder.toString();
    }

    private void callCallBack(Response result)
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
