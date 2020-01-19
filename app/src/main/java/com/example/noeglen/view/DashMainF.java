package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.noeglen.R;
import com.example.noeglen.data.ExerciseDTO;
import com.example.noeglen.data.FavoritesDTO;
import com.example.noeglen.data.KnowledgeDTO;
import com.example.noeglen.data.MyCallBack;
import com.example.noeglen.data.VideoDAO;
import com.example.noeglen.data.VideoDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class DashMainF extends Fragment implements View.OnClickListener, DashMainRecyclerAdapter.OnFavoriteListener {

    private IMainActivity iMain;
    private CardView iVidDash, iDiaryDash, iExerciseDash;
    private String fragmentTag;
    private RecyclerView rView;
    private DashMainRecyclerAdapter adapter;
    private FavoritesDTO favorites;
    private SharedPreferences sPref;
    private Gson gson;
    private ImageView markTodaysVideoAsSeenImage;
    private ImageView markTodaysDiaryAsWrittenImage;
    private ImageView markTodaysExerciseAsDoneImage;
    private boolean isNewDay;
    private ArrayList<VideoDTO> videoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashmain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        iMain.visibilityShow();
    }


    private void initializeView() {
        iVidDash = Objects.requireNonNull(getView()).findViewById(R.id.iDashVid);
        iDiaryDash = Objects.requireNonNull(getView()).findViewById(R.id.iDashDiary);
        iExerciseDash = Objects.requireNonNull(getView()).findViewById(R.id.iDashExercise);
        markTodaysVideoAsSeenImage = getView().findViewById(R.id.markTodaysVideoAsSeenImage);
        markTodaysDiaryAsWrittenImage = getView().findViewById(R.id.markTodaysDiaryAsWrittenImage);
        markTodaysExerciseAsDoneImage = getView().findViewById(R.id.markTodaysExerciseAsDoneImage);
        iVidDash.setOnClickListener(this);
        iDiaryDash.setOnClickListener(this);
        iExerciseDash.setOnClickListener(this);

        gson = new Gson();
        sPref = getContext().getSharedPreferences(getString(R.string.sharedPreferencesKey),Context.MODE_PRIVATE);
        getSharedPref();
        rView = getView().findViewById(R.id.favorites_recyclerview);
        adapter = new DashMainRecyclerAdapter(favorites,getContext(),this);
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        rView.setAdapter(adapter);

        getVideoList();
        checkIfNewDay();
        handleNewDay();
        handleShowCheckMarks();

    }

    private void getSharedPref() {
        String json = sPref.getString(getString(R.string.sPref_favorites),null);
        Type type = new TypeToken<FavoritesDTO>(){}.getType();
        favorites = gson.fromJson(json,type);
        if (favorites == null) {
            favorites = new FavoritesDTO(new ArrayList<KnowledgeDTO>(), new ArrayList<VideoDTO>(), new ArrayList<ExerciseDTO>());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iDashVid:
                if (markTodaysVideoAsSeenImage.getVisibility() != View.VISIBLE){
                    VideoDTO videoDTO = handleGetNextVideo();
                    Gson gson = new Gson();
                    String videoInJSON = gson.toJson(videoDTO);
                    Bundle bundle = new Bundle();
                    bundle.putString("videoObject",videoInJSON);
                    bundle.putString("isPartOfDailyGoals","true");
                    iMain.setFragment(new DashVidF(), getString(R.string.fragment_dashvid),true, bundle);
                    iMain.visibilityGone();
                }else {
                    iMain.inflateFragment(getString(R.string.fragment_dashvidmain));
                }
                break;
            case R.id.iDashDiary:
                iMain.inflateFragment(getString(R.string.fragment_diarymain));
                break;
            case R.id.iDashExercise:
                iMain.inflateFragment(getString(R.string.fragment_exermain));
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        iMain.visibilityGone();
    }

    @Override
    public void onFavoriteClick(int position, int CURRENT_TYPE) {
        Bundle bundle = new Bundle();
        String json = "";
        if (CURRENT_TYPE == 1){
            DashVidF videoF = new DashVidF();
            json = gson.toJson(favorites.getListOfVideoDTOS().get(position));
            bundle.putString("videoObject",json);
            iMain.setFragment(videoF,getString(R.string.fragment_infoknowledge),true,bundle);
            iMain.visibilityGone();
        }
        if (CURRENT_TYPE == 2){
            ExerExerF exerciseF = new ExerExerF();
            json = gson.toJson(favorites.getListOfExerciseDTOS().get(position));
            bundle.putString("currentExercise",json);
            iMain.setFragment(exerciseF,getString(R.string.fragment_infoknowledge),true,bundle);
            iMain.visibilityGone();
        }
        if (CURRENT_TYPE == 3){
            InfoKnowledgeF articleF = new InfoKnowledgeF();
            json = gson.toJson(favorites.getListOfKnowledgeDTOS().get(position));
            bundle.putString("currentKnowledgeArticle",json);
            iMain.setFragment(articleF,getString(R.string.fragment_infoknowledge),true,bundle);
            iMain.visibilityGone();
        }
    }

    public void checkIfNewDay(){
        String preferenceKey = getString(R.string.sharedPreferencesKey);
        String savedDateKey = getString(R.string.savedDateKey);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        String savedDate = preferences.getString(savedDateKey,"2020-01-01");

        if (!currentDate.equals(savedDate)){
            isNewDay = true;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(savedDateKey,currentDate);
            editor.apply();
        }else {
            isNewDay = false;
        }
    }

    public void handleNewDay(){
        if (isNewDay){
            String preferenceKey = getString(R.string.sharedPreferencesKey);
            String isTodaysVideoSeenKey = getString(R.string.isTodaysVideoSeen);
            String isTodaysDiaryWrittenKey = getString(R.string.isTodaysDiaryWritten);
            String isTodaysExerciseDoneKey = getString(R.string.isTodaysExerciseDone);

            SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(isTodaysVideoSeenKey,"false");
            editor.putString(isTodaysDiaryWrittenKey,"false");
            editor.putString(isTodaysExerciseDoneKey,"false");
            editor.apply();
        }
    }

    public void handleShowCheckMarks(){
        String isVideoSeen;
        String isDiaryWritten;
        String isExerciseDone;

        String preferenceKey = getString(R.string.sharedPreferencesKey);
        String isTodaysVideoSeenKey = getString(R.string.isTodaysVideoSeen);
        String isTodaysDiaryWrittenKey = getString(R.string.isTodaysDiaryWritten);
        String isTodaysExerciseDoneKey = getString(R.string.isTodaysExerciseDone);

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);

        isVideoSeen = preferences.getString(isTodaysVideoSeenKey, "false");
        isDiaryWritten = preferences.getString(isTodaysDiaryWrittenKey, "false");
        isExerciseDone = preferences.getString(isTodaysExerciseDoneKey, "false");

        if (isVideoSeen.equals("true")){
            markTodaysVideoAsSeenImage.setVisibility(View.VISIBLE);
        }
        if (isDiaryWritten.equals("true")){
            markTodaysDiaryAsWrittenImage.setVisibility(View.VISIBLE);
        }
        if (isExerciseDone.equals("true")){
            markTodaysExerciseAsDoneImage.setVisibility(View.VISIBLE);
        }
    }

    public VideoDTO handleGetNextVideo(){
        HashMap<String, Boolean> seenVideosList = getSeenVideosList();
        for (int i = 0; i < videoList.size(); i++) {
            Boolean seen = seenVideosList.get(videoList.get(i).getTitle());
               if (seen == null || !seen){
                   return videoList.get(i);
               }
        }
        return null;
    }

    public HashMap<String, Boolean> getSeenVideosList(){
        HashMap<String, Boolean> seenVideosList;
        Gson gson = new Gson();
        //Getting list from shared preferences
        String preferenceKey = getString(R.string.sharedPreferencesKey);
        String listKey = getString(R.string.seenVideosListKey);

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        String listInJSON = preferences.getString(listKey, null);
        if (listInJSON == null){
            HashMap<String, Boolean> hashMap = new HashMap<>();
            return hashMap;
        }else{
            Type type = new TypeToken<HashMap<String, Boolean>>(){}.getType(); //getting hashmap type for gson
            seenVideosList = gson.fromJson(listInJSON, type);

            return seenVideosList;
        }
    }

    public void getVideoList(){
        Gson gson = new Gson();
        String preferenceKey = getString(R.string.sharedPreferencesKey);
        String videoListKey = getString(R.string.videoListKey);

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        String listInJSON = preferences.getString(videoListKey, null);
        if (listInJSON == null){
            System.out.println("##################### The video list was not retrieved from shared preferences");
            return;
        }
        Type type = new TypeToken<ArrayList<VideoDTO>>(){}.getType(); //getting arrayList type for gson
        videoList = gson.fromJson(listInJSON, type);
    }


}
