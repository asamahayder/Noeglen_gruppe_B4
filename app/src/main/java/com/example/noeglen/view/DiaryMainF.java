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

//public class DiaryMainF extends Fragment implements View.OnClickListener {
  public class DiaryMainF extends Fragment{

   private TextView date,textView2;
   private CurrentDate currentDate;
   private SeekBar seekBar;
   private ImageView imageView;
   private IMainActivity iMain;
   private int smiley;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diarymain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        date = getView().findViewById(R.id.textView4);
        date.setText(currentDate.getDateString());

        seekBar = getView().findViewById(R.id.simpleSeekBar);

        imageView = getView().findViewById(R.id.imageView3);
        imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji3));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (seekBar.getProgress() > 0 && seekBar.getProgress() <= 20) {
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji1));
                    smiley = 0;
                }else if (seekBar.getProgress() > 20 && seekBar.getProgress() <= 40){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji2));
                    smiley = 1;
                }else if (seekBar.getProgress() > 40 && seekBar.getProgress() <= 60){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji3));
                    smiley = 2;
                }else if (seekBar.getProgress() > 60 && seekBar.getProgress() <= 80){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji4));
                    smiley = 3;
                }else if (seekBar.getProgress() > 80 && seekBar.getProgress() <= 100){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji5));
                    smiley = 4;
                }


            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String mm = "";
                if (seekBar.getProgress() > 0 && seekBar.getProgress() <= 20){
                    mm = "Fragment Diary1";
                    iMain.inflateFragment(mm);
                }


            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        currentDate = CurrentDate.getInstance();
        iMain = (IMainActivity) getActivity();
    }

   /* @Override
    public void onClick(View view) {
        String tag ="";

        if (view == con){
            if (seekBar.getProgress() > 0 && seekBar.getProgress() <= 20){
                tag = getString(R.string.fragment_diary1);
                iMain.inflateFragment(tag);
            }else if (seekBar.getProgress() > 20 && seekBar.getProgress() <= 40){
                tag= getString(R.string.fragment_diarymain);
                iMain.inflateFragment(tag);
            }

        }

    }*/
}
