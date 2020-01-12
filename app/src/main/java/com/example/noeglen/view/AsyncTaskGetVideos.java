package com.example.noeglen.view;

import android.os.AsyncTask;

import com.example.noeglen.data.MyCallBack;
import com.example.noeglen.data.VideoDAO;
import com.example.noeglen.data.VideoDTO;
import com.example.noeglen.logic.VideoListLogic;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

//Denne klasse er lavet med hjælp fra hjælpelæren Nicolai.
public class AsyncTaskGetVideos extends AsyncTask<String, String, Exception> {

    private final WeakReference<DashVidMainF> activityRef; //Weak reference bruges så der ikke kommer memory leakage. En forklaring findes her: https://medium.com/google-developer-experts/finally-understanding-how-references-work-in-android-and-java-26a0d9c92f83
    private ArrayList<VideoDTO> vList;
    private DashVidMainRecyclerAdapter adapter;

    public AsyncTaskGetVideos(DashVidMainF activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    @Override
    protected Exception doInBackground(String... strings) {
        try {
            VideoDAO videoDAO = new VideoDAO();
            videoDAO.getAllVideos(new MyCallBack() {
                @Override
                public void onCallBack(ArrayList<VideoDTO> videoList) {
                    vList = videoList;
                }
            });

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
                //System.out.println("###############" + vList);
                adapter = new DashVidMainRecyclerAdapter(vList);
                activityRef.get().setVideoList(vList);
                activityRef.get().displayVideos(adapter);
            }
        }
    }

}

