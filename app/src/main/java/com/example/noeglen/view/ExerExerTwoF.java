package com.example.noeglen.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;

public class ExerExerTwoF extends Fragment implements View.OnClickListener {
    private Button bFav, btnPlay;
    private MediaPlayer mp;
    private IMainActivity iMain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exer_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    private void initializeView() {
        bFav = getView().findViewById(R.id.bAddToFav);
        btnPlay = getView().findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (mp == null) {
            mp = MediaPlayer.create(getActivity(), R.raw.stressoevelselydfile);
            btnPlay.setText("Stop");
            mp.start();
        } else if (mp != null){
            btnPlay.setText("Afspil");
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}
