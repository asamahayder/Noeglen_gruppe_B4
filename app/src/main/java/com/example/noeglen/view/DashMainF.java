package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.noeglen.R;
import com.example.noeglen.data.DiaryDTO;
import com.example.noeglen.data.FavoriteDTO;
import com.example.noeglen.data.VideoDTO;
import com.example.noeglen.logic.CurrentDate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class DashMainF extends Fragment implements View.OnClickListener, DashMainRecyclerAdapter.OnFavoriteListener {

    private IMainActivity iMain;
    private CardView iVidDash, iDiaryDash, iExerciseDash;
    private String fragmentTag;
    private RecyclerView rView;
    private DashMainRecyclerAdapter adapter;
    private List<FavoriteDTO> favoriteList;
    private SharedPreferences sPref;
    private Gson gson;
    private ImageView markTodaysVideoAsSeenImage;
    private ImageView markTodaysDiaryAsWrittenImage;
    private ImageView markTodaysExerciseAsDoneImage;
    private boolean isNewDay;
    private ArrayList<VideoDTO> videoList;
    private TextView emptyFavoriteListTextView;
    private ConstraintLayout diaryContentFrame;
    private ArrayList<DiaryDTO> diaryList;
    private TextView emptyDiaryContentFrameTextView;
    private LinearLayout recentDiariesLinearLayout;
    private CardView recentDiary1;
    private CardView recentDiary2;
    private CardView recentDiary3;
    private TextView recentDiary1Text;
    private TextView recentDiary2Text;
    private TextView recentDiary3Text;


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
        emptyFavoriteListTextView = getView().findViewById(R.id.emptyFavoriteListText);
        diaryContentFrame = getView().findViewById(R.id.diaryContentFrame);
        emptyDiaryContentFrameTextView = getView().findViewById(R.id.emptyDiaryContentList);
        recentDiariesLinearLayout = getView().findViewById(R.id.recentDiariesLinearLayout);
        recentDiary1 = getView().findViewById(R.id.recentDiary1);
        recentDiary2 = getView().findViewById(R.id.recentDiary2);
        recentDiary3 = getView().findViewById(R.id.recentDiary3);
        recentDiary1Text = getView().findViewById(R.id.recentDiary1Text);
        recentDiary2Text = getView().findViewById(R.id.recentDiary2Text);
        recentDiary3Text = getView().findViewById(R.id.recentDiary3Text);
        iVidDash.setOnClickListener(this);
        iDiaryDash.setOnClickListener(this);
        iExerciseDash.setOnClickListener(this);

        gson = new Gson();
        sPref = getContext().getSharedPreferences(getString(R.string.sharedPreferencesKey), MODE_PRIVATE);
        getFavoriteList();

        if (!favoriteList.isEmpty()){
            emptyFavoriteListTextView.setVisibility(View.GONE);
            rView = getView().findViewById(R.id.favorites_recyclerview);
            adapter = new DashMainRecyclerAdapter(favoriteList,getContext(),this);
            rView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
            rView.setAdapter(adapter);
        }


        getVideoList();
        checkIfNewDay();
        handleNewDay();
        handleShowCheckMarks();

        getDiaryList();
        getRecentDiaries();
    }

    private void getFavoriteList() {
        String json = sPref.getString(getString(R.string.sPref_favorites),null);
        Type type = new TypeToken<List<FavoriteDTO>>(){}.getType();
        favoriteList = gson.fromJson(json,type);
        if (favoriteList == null) {
            favoriteList = new ArrayList<>();
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
                    iMain.visibilityGone();
                    iMain.setFragment(new DashVidF(), getString(R.string.fragment_dashvid),true, bundle);
                }else {
                    iMain.inflateFragment(getString(R.string.fragment_dashvidmain));
                    iMain.visibilityGone();
                }
                break;
            case R.id.iDashDiary:
                if (markTodaysDiaryAsWrittenImage.getVisibility() != View.VISIBLE){
                    iMain.inflateFragment(getString(R.string.fragment_diarymain));
                }else{
                    Bundle bundle = new Bundle();
                    CurrentDate currentDate = CurrentDate.getInstance();
                    String date = new SimpleDateFormat("dd/M/yyyy").format(currentDate.getDate());
                    bundle.putString("date", date);
                    iMain.setFragment(new Diary2F(), getString(R.string.fragment_diary2),true,bundle);
                }
                break;
            case R.id.iDashExercise:
                if (markTodaysExerciseAsDoneImage.getVisibility() != View.VISIBLE){
                    iMain.inflateFragment(getString(R.string.fragment_exerexer));
                    iMain.visibilityGone();
                }else{
                    iMain.inflateFragment(getString(R.string.fragment_exermain));
                    iMain.visibilityGone();
                }
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
            json = gson.toJson(favoriteList.get(position));
            bundle.putString("videoObject",json);
            System.out.println("######################3" + favoriteList.get(position).getTitle());
            System.out.println("######################" + favoriteList.get(position).getVideoURL());
            iMain.setFragment(videoF,getString(R.string.fragment_dashvid),true,bundle);
            iMain.visibilityGone();
        }
        if (CURRENT_TYPE == 2){
            json = gson.toJson(favoriteList.get(position));
            bundle.putString("currentExercise",json);
            if (favoriteList.get(position).getTitle().equals("Vejrtrækningsøvelse")){
                ExerExerF exerciseF = new ExerExerF();
                iMain.setFragment(exerciseF,getString(R.string.fragment_exerexer),true,bundle);
            }
            else {
                ExerExerTwoF exerciseF = new ExerExerTwoF();
                iMain.setFragment(exerciseF,getString(R.string.fragment_exerexer),true,bundle);
            }
            iMain.visibilityGone();
        }
        if (CURRENT_TYPE == 3){
            InfoKnowledgeF articleF = new InfoKnowledgeF();
            json = gson.toJson(favoriteList.get(position));
            bundle.putString("currentKnowledgeArticle",json);
            iMain.setFragment(articleF,getString(R.string.fragment_infoknowledge),true,bundle);
            iMain.visibilityGone();
        }
    }

    public void checkIfNewDay(){
        String preferenceKey = getString(R.string.sharedPreferencesKey);
        String savedDateKey = getString(R.string.savedDateKey);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, MODE_PRIVATE);
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

            SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, MODE_PRIVATE);
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

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, MODE_PRIVATE);

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

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, MODE_PRIVATE);
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

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, MODE_PRIVATE);
        String listInJSON = preferences.getString(videoListKey, null);
        if (listInJSON == null){
            System.out.println("##################### The video list was not retrieved from shared preferences");
            return;
        }
        Type type = new TypeToken<ArrayList<VideoDTO>>(){}.getType(); //getting arrayList type for gson
        videoList = gson.fromJson(listInJSON, type);
    }

    private void getDiaryList(){
        Gson gson = new Gson();
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.sharedPreferencesKey), MODE_PRIVATE);
        String listInJSON = preferences.getString("Diary",null);
        Type type = new TypeToken<ArrayList<DiaryDTO>>(){}.getType(); //getting arrayList type for gson
        diaryList = gson.fromJson(listInJSON, type);
    }

    private void getRecentDiaries(){
        ArrayList<DiaryDTO> recentDiaries = new ArrayList<>();
        if (diaryList == null){
            emptyDiaryContentFrameTextView.setVisibility(View.VISIBLE);
        }else{
            if (diaryList.isEmpty()){
                emptyDiaryContentFrameTextView.setVisibility(View.VISIBLE);
            }else{
                Collections.sort(diaryList);
                for (int i = 0; i < diaryList.size(); i++) {
                    recentDiaries.add(diaryList.get(i));
                    if (i == 3)break;
                }
                showRecentDiaries(recentDiaries);
            }
        }
    }

    private void showRecentDiaries(final ArrayList<DiaryDTO> recentDiaryList){
        switch (recentDiaryList.size()){
            case 3:
                recentDiary3Text.setText(recentDiaryList.get(2).getDate());
                recentDiary3.setVisibility(View.VISIBLE);
                recentDiary3.setClickable(true);
                recentDiary3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("date",recentDiaryList.get(2).getDate());
                        Diary2F diary2F = new Diary2F();
                        iMain.setFragment(diary2F, getString(R.string.fragment_diary2),true,bundle);
                        iMain.visibilityGone();
                    }
                });
            case 2:
                recentDiary2Text.setText(recentDiaryList.get(1).getDate());
                recentDiary2.setVisibility(View.VISIBLE);
                recentDiary2.setClickable(true);
                recentDiary2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("date",recentDiaryList.get(1).getDate());
                        Diary2F diary2F = new Diary2F();
                        iMain.setFragment(diary2F, getString(R.string.fragment_diary2),true,bundle);
                        iMain.visibilityGone();
                    }
                });
            case 1:
                recentDiary1Text.setText(recentDiaryList.get(0).getDate());
                recentDiary1.setVisibility(View.VISIBLE);
                recentDiary1.setClickable(true);
                recentDiary1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("date",recentDiaryList.get(0).getDate());
                        Diary2F diary2F = new Diary2F();
                        iMain.setFragment(diary2F, getString(R.string.fragment_diary2),true,bundle);
                        iMain.visibilityGone();
                    }
                });
            default: break;
        }
    }


}
