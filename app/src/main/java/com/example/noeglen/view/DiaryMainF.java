package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.noeglen.R;
import com.example.noeglen.logic.CurrentDate;

public class DiaryMainF extends Fragment {

   private TextView textView1,textView2;
   private CurrentDate currentDate;
   private SeekBar seekBar;
   private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diarymain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        textView1 = getView().findViewById(R.id.textView4);
        textView1.setText(currentDate.getDateString());

        seekBar = getView().findViewById(R.id.simpleSeekBar);

        imageView = getView().findViewById(R.id.imageView3);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setMax(100);
               // textView2.setText("Progress" + seekBar.getProgress() + " / " +  seekBar.getMax() + "%" );
                if (seekBar.getProgress() > 0 && seekBar.getProgress() <= 20) {
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji1));
                }else if (seekBar.getProgress() > 20 && seekBar.getProgress() <= 40){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji2));
                }else if (seekBar.getProgress() > 40 && seekBar.getProgress() <= 60){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji3));
                }else if (seekBar.getProgress() > 60 && seekBar.getProgress() <= 80){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji4));
                }else if (seekBar.getProgress() > 80 && seekBar.getProgress() <= 100){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji5));
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        currentDate = CurrentDate.getInstance();
    }
}
