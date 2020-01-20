package com.example.noeglen.view;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;

public class ExerExerTwoF extends Fragment implements View.OnClickListener {
    private Button btnPlay;
    private MediaPlayer mp;
    private IMainActivity iMain;
    private CardView bAddToFav;
    private int primaryDark;

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
        bAddToFav = getView().findViewById(R.id.bAddToFav);
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
            int primaryOrange = getResources().getColor(R.color.primaryOrange);
            mp = MediaPlayer.create(getActivity(), R.raw.stressoevelselydfile);
            btnPlay.setText("Stop");
            btnPlay.setTextColor(primaryOrange);
            btnPlay.setBackgroundResource(R.drawable.orange_border);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    stopMediaPlayer();
                }
            });
        } else if (mp != null) {
            stopMediaPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mp.isPlaying()) {
            System.out.println("###########################################################");
        }
    }

    public void stopMediaPlayer() {
        if (mp != null) {
            primaryDark = getResources().getColor(R.color.primaryDark);
            btnPlay.setText("Afspil");
            btnPlay.setTextColor(primaryDark);
            btnPlay.setBackgroundResource(R.drawable.blue_border);
            mp.release();
            mp = null;
        }
    }
}
