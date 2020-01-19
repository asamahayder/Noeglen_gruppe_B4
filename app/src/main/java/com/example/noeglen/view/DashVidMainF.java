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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noeglen.R;
import com.example.noeglen.data.MyCallBack;
import com.example.noeglen.data.VideoDAO;
import com.example.noeglen.data.VideoDTO;
import com.example.noeglen.data.WeekDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class DashVidMainF extends Fragment implements View.OnClickListener {

    private IMainActivity mainActivity;
    private RecyclerView recyclerView;
    private ArrayList<VideoDTO> videoList;
    private ArrayList<Integer> weekPositionList;
    private ArrayList<Object> itemList;
    private ImageView returnButton;
    private ProgressBar videoListBufferSign;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashvidmain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    private void initializeView() {
        recyclerView = getView().findViewById(R.id.videoRecyclerView);
        returnButton = getView().findViewById(R.id.returnToDashButton);
        videoListBufferSign = getView().findViewById(R.id.videoListBufferSign);

        getAllVideosFromDataBase();
        returnButton.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        if (view == returnButton){
            System.out.println("##################333333 hey");
            mainActivity.inflateFragment(getString(R.string.fragment_dashmain), false);
        }
    }


    public void setVideoList(ArrayList<VideoDTO> videoList){
        this.videoList = videoList;
    }

    public void getAllVideosFromDataBase(){
        VideoDAO videoDAO = new VideoDAO();
        videoDAO.getAllVideos(new MyCallBack() {
            @Override
            public void onCallBack(Object object) {
                setVideoList((ArrayList<VideoDTO>) object);
                createListForAdapter();
                createWeekPositionList();
                if (getContext() != null){
                    DashVidMainRecyclerAdapter adapter = new DashVidMainRecyclerAdapter(itemList, getContext(), getSeenVideosList(), weekPositionList, new MyCallBack() {
                        @Override
                        public void onCallBack(Object object) {
                            Gson gson = new Gson();
                            Bundle bundle = new Bundle();
                            String videoAsString = gson.toJson(object);
                            bundle.putString("videoObject",videoAsString);
                            mainActivity.setFragment(new DashVidF(), getString(R.string.fragment_dashvid),true, bundle);
                        }
                    });
                    displayVideos(adapter);
                }
            }
        });
    }



    public void displayVideos(DashVidMainRecyclerAdapter adapter){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        videoListBufferSign.setVisibility(View.INVISIBLE);
    }

    public void showErrorMessage(){
        //TODO show error message in case Async fails
    }

    public HashMap<String, Boolean> getSeenVideosList(){
        HashMap<String, Boolean> seenVideosList;
        Gson gson = new Gson();
        //Getting list from shared preferences
         String preferenceKey = getString(R.string.sharedPreferencesKey);
        String listKey = getString(R.string.seenVideosListKey);

        SharedPreferences preferences = getActivity().getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        String listInJSON = preferences.getString(listKey, null);
        Type type = new TypeToken<HashMap<String, Boolean>>(){}.getType(); //getting hashmap type for gson
        seenVideosList = gson.fromJson(listInJSON, type);

        return seenVideosList;
    }

    //This method combines a video object and a weekobject in the same list and positions them in a way that makes sense for the adapter:
    public void createListForAdapter(){
        ArrayList<Object> list = new ArrayList<>();
        boolean firstVideoInWeek = true;
        for (int i = 0; i < videoList.size(); i++) {
            if (!firstVideoInWeek){
                if (i != 0){
                    if (!videoList.get(i-1).getWeek().equals(videoList.get(i).getWeek()))firstVideoInWeek = true;
                }
            }
            if (firstVideoInWeek){
                list.add(new WeekDTO(videoList.get(i).getWeek()));
                firstVideoInWeek = false;
            }
            list.add(videoList.get(i));
        }
        itemList = list;
    }

    //This is used by the adapter to determine when to create a week label and when to create a video in the recycler view.
    public void createWeekPositionList(){
        ArrayList<Integer> weekTitlePositionList = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i) instanceof WeekDTO){
                weekTitlePositionList.add(i);
            }
        }
        weekPositionList = weekTitlePositionList;
    }
}