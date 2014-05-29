package com.romans.visitsmart.networking.maps;

import android.os.AsyncTask;
import com.google.android.gms.maps.model.LatLng;
import com.romans.visitsmart.dao.Measurable;
import com.romans.visitsmart.networking.handler.DistanceHandler;
import com.romans.visitsmart.utils.DevLog;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistanceRetriever extends AsyncTask<Void, Void, Map<Measurable, String>> {

    LatLng start;
    List<? extends Measurable> end;
    DistanceHandler distanceHandler;
    public DistanceRetriever(LatLng start, List<? extends Measurable> end, DistanceHandler handler)
    {
          this.start = start;
          this.end = end;
          distanceHandler = handler;
    }


	@Override
	protected Map<Measurable, String> doInBackground(Void... params) {
        Map<Measurable, String> map = new HashMap<>(end.size());

        for (Measurable m : end)
        {
            map.put(m, getDocument(start, m.getPosition()));
        }
		return map;
	}
	
	
	
	
	
	@Override
	protected void onPostExecute(Map<Measurable, String> result) {
		for (Measurable m : result.keySet())
        {
            m.setDistance(result.get(m));
        }
        distanceHandler.notifyDistancesRetrieved();
	}





	public String getDocument(LatLng a, LatLng b) {

        String url = "http://maps.googleapis.com/maps/api/directions/json?" 
                + "origin=" + a.latitude + "," + a.longitude  
                + "&destination=" + b.latitude + "," + b.longitude 
                + "&sensor=false&units=metric";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		StringBuilder sb = new StringBuilder();
    		
    		String line;
    		while ((line = br.readLine()) != null)
    			sb.append(line);
    		br.close();
            JSONObject object = new JSONObject(sb.toString());

            DevLog.e(object.toString());
            String distance = object.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");
    		return distance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
