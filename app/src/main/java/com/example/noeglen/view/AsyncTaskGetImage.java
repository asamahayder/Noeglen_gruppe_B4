package com.example.noeglen.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//Denne klasse er lavet med hjælp fra hjælpelæren Nicolai.
public class AsyncTaskGetImage extends AsyncTask<String, String, Exception> {

    private final WeakReference<DashVidMainRecyclerAdapter> activityRef; //Weak reference bruges så der ikke kommer memory leakage. En forklaring findes her: https://medium.com/google-developer-experts/finally-understanding-how-references-work-in-android-and-java-26a0d9c92f83
    private Bitmap image;

    public AsyncTaskGetImage(DashVidMainRecyclerAdapter activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    @Override
    protected Exception doInBackground(String... strings) {
        try {
            image = getImageFromWeb(strings[0]);

        } catch (Exception e) {
            return e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Exception e) {
        if (activityRef.get() != null) {
            if (e != null) {
                activityRef.get().showErrorMessage();
            } else {
                activityRef.get().setImage(image);
            }
        }
    }

    //this method fetches an image from web based on a url. The method was made with help from the following: https://stackoverflow.com/questions/3870638/how-to-use-setimageuri-on-android
    public Bitmap getImageFromWeb(String url){
        Bitmap image = null;
        try {
            URL imageURL = new URL(url);
            URLConnection connection = imageURL.openConnection();
            connection.connect();
            InputStream inputStream =  connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            image = BitmapFactory.decodeStream(bufferedInputStream);
            bufferedInputStream.close();
            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

}
