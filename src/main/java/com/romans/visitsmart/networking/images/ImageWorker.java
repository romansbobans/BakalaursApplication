package com.romans.visitsmart.networking.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Romans on 29/05/14.
 */
public class ImageWorker extends AsyncTask<String, Void, Bitmap>{

    private final ImageView view;
    String BASE_URL = "http://lginvest.lv/assets/";

    MemoryCache memoryCache=new MemoryCache();
    FileCache fileCache;



    @Override
    protected Bitmap doInBackground(String... params) {
        return getBitmap(BASE_URL + params[0]);

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    public ImageWorker(Context context, ImageView view){
        fileCache=new FileCache(context);
        this.view = view;
    }



    private Bitmap getBitmap(String url)
    {

        File f=fileCache.getFile(url);

        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;

        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            conn.disconnect();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    private Bitmap decodeFile(File f){
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();

            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
