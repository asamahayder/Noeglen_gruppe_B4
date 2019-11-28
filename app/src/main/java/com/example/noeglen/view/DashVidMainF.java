package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noeglen.R;

public class DashVidMainF extends Fragment {

    private IMainActivity iMain;
    private RecyclerView rView;
    private DashVidMainRecyclerAdapter rAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initializeView();
        return inflater.inflate(R.layout.fragment_dashvidmain, container, false);
    }

    private void initializeView() {
        rView = getView().findViewById(R.id.dashvidmain_recyclerview);
        rAdapter = new DashVidMainRecyclerAdapter(vidoes,this);
        rView.setAdapter(rAdapter);
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }
}