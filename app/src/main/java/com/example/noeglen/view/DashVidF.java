package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.data.VideoDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;

public class DashVidF extends Fragment implements View.OnClickListener {
    private TextView videoDescription;
    private TextView videoTitle;
    private ImageView returnButton;
    private Button markSeenButton;
    private Button markUnseenButton;
    private FullscreenVideoView videoView;
    private VideoDTO video;
    private IMainActivity iMain;
    private HashMap<String, Boolean> seenVideosList;
    private Bundle bundle;
    private String isPartOfDailyGoals;


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
        returnButton.setOnClickListener(this);
        markSeenButton.setOnClickListener(this);
        markUnseenButton.setOnClickListener(this);
        bundle = this.getArguments();

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

        videoView.videoUrl(video.getVideoUrl());
        videoView.enableAutoStart();
        videoView.requestFocus();
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
        }else if (view == markSeenButton){
            isPartOfDailyGoals = bundle.getString("isPartOfDailyGoals");
            if (isPartOfDailyGoals != null && isPartOfDailyGoals.equals("true")){
                handlePartOfDailyGoals();
                iMain.inflateFragment(getString(R.string.fragment_dashmain));
            }else{
                iMain.inflateFragment(getString(R.string.fragment_dashvidmain));
            }
            handleMarkAsSeen(true);

        }else if (view == markUnseenButton){
            handleMarkAsSeen(false);
            iMain.inflateFragment(getString(R.string.fragment_dashvidmain));
        }
    }

    public void handleGetVideo(){
        Gson gson = new Gson();
        bundle = this.getArguments();
        if (bundle!=null){
            String videoAsString = bundle.getString("videoObject","no vid here");
            //System.out.println("#######################" + videoAsString);
            video = gson.fromJson(videoAsString,VideoDTO.class);
        }else {
            System.out.println("something went wrong. Video was not sent from fragment");
        }
    }

    public void showErrorMessage(){
        //TODO show error message
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
        //System.out.println("############################" + video);
        Boolean isSeen = seenVideosList.get(video.getTitle());

        if (isSeen == null){
            isSeen = false;
        }

        return isSeen;
    }

    public void handlePartOfDailyGoals(){
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.sharedPreferencesKey),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.isTodaysVideoSeen),"true");
        editor.apply();
    }
}
