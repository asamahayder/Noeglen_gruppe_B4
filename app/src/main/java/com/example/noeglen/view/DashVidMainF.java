package com.example.noeglen.view;

import android.content.Context;
import android.content.Intent;
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
import com.example.noeglen.logic.YoutubePlayer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DashVidMainF extends Fragment implements View.OnClickListener {

    private ImageView iVIdeoLink;

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

        iVIdeoLink = getView().findViewById(R.id.iVideoLink);
        iVIdeoLink.setOnClickListener(DashVidMainF.this,DashVidF.class);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View view) {
        if (view == iVIdeoLink){
            startActivity(new Intent());
        }
    }
}