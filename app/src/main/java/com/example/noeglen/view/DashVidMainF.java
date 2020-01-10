package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noeglen.R;
import com.example.noeglen.data.VideoDTO;
import com.example.noeglen.logic.VideoListLogic;

import java.util.ArrayList;

public class DashVidMainF extends Fragment implements View.OnClickListener {

    private IMainActivity mainActivity;
    private RecyclerView recyclerView;
    private ArrayList<VideoDTO> videoList;

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
        new AsyncTaskGetVideos(this).execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onClick(View view) {
    }


    public void setVideoList(ArrayList<VideoDTO> videoList){
        this.videoList = videoList;
    }

    public void displayVideos(DashVidMainRecyclerAdapter adapter){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    public void showErrorMessage(){
        //TODO show error message in case Async fails
    }
}