package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.data.VideoDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


//Some of the code in this class is inspired by the following: https://www.youtube.com/watch?v=EaJHJK6Vzqg&t=367s
public class DashVidF extends Fragment implements View.OnClickListener {
    private TextView videoDescription;
    private TextView videoTitle;
    private ImageView returnButton;
    private Button markSeenButton;
    private Button markUnseenButton;
    private VideoView videoView;
    private VideoDTO video;

    //video controls
    private ImageView buttonPlayAndPause;
    private TextView timeCurrentView;
    private TextView timeTotalView;
    private ProgressBar videoBufferSign;
    private boolean isPlaying;
    private boolean wasPlaying;
    private SeekBar videoSeekBar;
    private boolean isTouchingBar;

    private int currentTime;
    private int totalTime;
    private IMainActivity iMain;

    private HashMap<String, Boolean> seenVideosList;
    private boolean markedAsSeen;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashvid, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoView = getView().findViewById(R.id.videoView);
        returnButton = getView().findViewById(R.id.returnToDashVidMainButton);
        videoDescription = getView().findViewById(R.id.videoDescription);
        videoTitle = getView().findViewById(R.id.videoTitle);
        markSeenButton = getView().findViewById(R.id.markSeenButton);
        markUnseenButton = getView().findViewById(R.id.markUnseenButton);
        buttonPlayAndPause = getView().findViewById(R.id.buttonPlayAndPause);
        timeCurrentView = getView().findViewById(R.id.timeCurrent);
        timeTotalView = getView().findViewById(R.id.timeTotal);
        videoBufferSign = getView().findViewById(R.id.videoBufferSign);
        videoSeekBar = getView().findViewById(R.id.videoSeekBar);

        isPlaying = false;
        wasPlaying = false;
        isTouchingBar = false;
        buttonPlayAndPause.setOnClickListener(this);
        returnButton.setOnClickListener(this);
        markSeenButton.setOnClickListener(this);
        markUnseenButton.setOnClickListener(this);

        videoSeekBar.setMax(100);
        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (isTouchingBar){
                    int newProgress = seekBar.getProgress();
                    int newTime = newProgress*totalTime/100;
                    videoView.seekTo(newTime*1000);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTouchingBar = true;
                wasPlaying = isPlaying;
                isPlaying = false;
                videoView.pause();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isTouchingBar = false;
                if (wasPlaying){
                    isPlaying = true;
                    videoView.start();
                }else {
                    isPlaying = false;
                    videoView.pause();
                }
            }
        });

        handleGetVideo();
        handleGetSeenList();

        //show the correct mark button
        if (handleCheckIfSeen()){
            markUnseenButton.setVisibility(View.VISIBLE);
        }else {
            markSeenButton.setVisibility(View.VISIBLE);
        }

        videoTitle.setText(video.getTitle());
        //videoDescription.setText(video.getDescription);

        Uri videoURI = Uri.parse(video.getVideoUrl());
        videoView.setVideoURI(videoURI);
        videoView.requestFocus();
        videoView.start();
        isPlaying = true;

        videoBufferSign.setVisibility(View.VISIBLE);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoBufferSign.setVisibility(View.INVISIBLE);

                totalTime = videoView.getDuration()/1000;
                String totalDurationFormatted = String.format("%02d:%02d", totalTime/60, totalTime%60);
                timeTotalView.setText(totalDurationFormatted);
            }
        });

        new VideoProgress().execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        if (view == returnButton){
            iMain.inflateFragment(getString(R.string.fragment_dashvidmain));
        }else if (view == buttonPlayAndPause){
            if (isPlaying){
                buttonPlayAndPause.setImageResource(R.drawable.play);
                isPlaying = false;
                videoView.pause();
            }else{
                buttonPlayAndPause.setImageResource(R.drawable.pause);
                isPlaying = true;
                videoView.start();
            }
        }else if (view == markSeenButton){
            handleMarkAsSeen(true);
            iMain.inflateFragment(getString(R.string.fragment_dashvidmain));
        }else if (view == markUnseenButton){
            handleMarkAsSeen(false);
            iMain.inflateFragment(getString(R.string.fragment_dashvidmain));
        }
    }

    public void handleGetVideo(){
        Gson gson = new Gson();
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            String videoAsString = bundle.getString("videoObject","no vid here");
            video = gson.fromJson(videoAsString,VideoDTO.class);
        }else {
            System.out.println("something went wrong. Video was not sent from fragment");
        }
    }

    public void showErrorMessage(){
        //TODO show error message
    }

    @Override
    public void onStop() {
        super.onStop();
        isPlaying = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPlaying = true;
        videoView.start();
        System.out.println("#############################################3hejsa");
    }

    public void handleVideoBuffer(){
    }

    public class VideoProgress extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            do{
                if (isPlaying || isTouchingBar){
                    currentTime = getVideoPosition()/1000;
                    publishProgress(currentTime);
                }

            }while (getVideoSeekBarProgress() <= 100);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            try {
                if (!isTouchingBar){
                    int currentPercent = values[0] * 100/ totalTime;
                    videoSeekBar.setProgress(currentPercent);
                }
                String currentTimeString = String.format("%02d:%02d", values[0]/60, values[0]%60);
                timeCurrentView.setText(currentTimeString);
            }catch (Exception e){

            }

        }
    }

    public int getVideoPosition(){
        return videoView.getCurrentPosition();
    }

    public int getVideoSeekBarProgress(){
        return videoSeekBar.getProgress();
    }

    public void handleMarkAsSeen(Boolean isSeen){
        Gson gson = new Gson();
        String preferenceKey = getString(R.string.sharedPreferencesKey);
        String listKey = getString(R.string.seenVideosListKey);

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);

        //inserting value
        seenVideosList.put(video.getTitle(), isSeen);

        //converting from object to string
        String listInJSON = gson.toJson(seenVideosList);

        //saving to shared preferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(listKey, listInJSON);
        editor.apply();
    }

    public void handleGetSeenList(){
        Gson gson = new Gson();

        //Getting list from shared preferences
        String preferenceKey = getString(R.string.sharedPreferencesKey);
        String listKey = getString(R.string.seenVideosListKey);

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        String listInJSON = preferences.getString(listKey, null);
        HashMap<String, Boolean> seenVideosList;

        if (listInJSON == null){

            //create list
            seenVideosList = new HashMap<>();
        }else{
            //converting from string to object
            Type type = new TypeToken<HashMap<String, Boolean>>(){}.getType(); //getting hashmap type for gson
            seenVideosList = gson.fromJson(listInJSON, type);
        }



        this.seenVideosList = seenVideosList;

    }

    public Boolean handleCheckIfSeen(){
        Boolean isSean = seenVideosList.get(video.getTitle());

        if (isSean == null){
            isSean = false;
        }

        return isSean;
    }
}
