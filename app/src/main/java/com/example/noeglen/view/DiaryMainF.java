package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.logic.CurrentDate;


  public class DiaryMainF extends Fragment implements View.OnClickListener{

   private TextView date;
   private CurrentDate currentDate;
   private SeekBar seekBar;
   private ImageView imageView;
   private IMainActivity iMain;
   private int  smiley;
   private String [] questions;
   private Button calendar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diarymain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        date = getView().findViewById(R.id.textView4);
        date.setText(currentDate.getDateString());
        date.setOnClickListener(this);

        seekBar = getView().findViewById(R.id.simpleSeekBar);

        imageView = getView().findViewById(R.id.imageView3);

        questions = new String[4];

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (seekBar.getProgress() > 0 && seekBar.getProgress() <= 20) {
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji5));
                    smiley = 5;
                    questions[0] = "Hvad gik dårligt i dag? ";
                    questions[1] = "Hvad gik mindre godt ?";
                    questions[2] = "Hvordan vil du indgår de dårlige ting fremover?";
                    questions[3] = "Andet du vil tilføje?";

                }else if (seekBar.getProgress() > 20 && seekBar.getProgress() <= 40){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji4));
                    smiley = 4;
                    questions[0] = "Hvad gik dårligt i dag? ";
                    questions[1] = "Hvad gik mindre godt ?";
                    questions[2] = "Hvordan vil du indgår de dårlige ting fremover?";
                    questions[3] = "Andet du vil tilføje?";

                }else if (seekBar.getProgress() > 40 && seekBar.getProgress() <= 60){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji3));
                    smiley = 3;
                    questions[0] = "Hvorfor er du ligeglad? ";
                    questions[1] = "Hvad gik mindre godt ?";
                    questions[2] = "Hvordan vil du indgår de dårlige ting fremover?";
                    questions[3] = "Andet du vil tilføje?";
                }else if (seekBar.getProgress() > 60 && seekBar.getProgress() <= 80){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji2));
                    smiley = 2;
                    questions[0] = "Hvad gik godt i dag? ";
                    questions[1] = "Hvad gik mindre godt ?";
                    questions[2] = "Hvordan vil du indgår de dårlige ting fremover?";
                    questions[3] = "Andet du vil tilføje?";
                }else if (seekBar.getProgress() > 80 && seekBar.getProgress() <= 100){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji1));
                    smiley = 1;
                    questions[0] = "Hvad gik godt i dag? ";
                    questions[1] = "Hvad gik mindre godt ?";
                    questions[2] = "Hvordan vil du indgår de dårlige ting fremover?";
                    questions[3] = "Andet du vil tilføje?";
                }


            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String mm = "";
                if (seekBar.getProgress() > -20 && seekBar.getProgress() <= 20){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    Diary1F diary1F = new Diary1F();
                    iMain.setFragment(diary1F,mm,true,bundle);
                }else if ((seekBar.getProgress() > 20 && seekBar.getProgress() <= 40)){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    Diary1F diary1F = new Diary1F();
                    iMain.setFragment(diary1F,mm,true,bundle);
                }else if (seekBar.getProgress() > 40 && seekBar.getProgress() <= 60){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    Diary1F diary1F = new Diary1F();
                    iMain.setFragment(diary1F,mm,true,bundle);
                }else if (seekBar.getProgress() > 60 && seekBar.getProgress() <= 80){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    Diary1F diary1F = new Diary1F();
                    iMain.setFragment(diary1F,mm,true,bundle);
                }else if (seekBar.getProgress() > 80 && seekBar.getProgress() <= 120){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    Diary1F diary1F = new Diary1F();
                    iMain.setFragment(diary1F,mm,true,bundle);
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

    @Override
    public void onClick(View view) {
        String tag ="";


        if (view == date){
            DiaryFCalendar diaryFCalendar = new DiaryFCalendar();
            iMain.inflateFragment(getString(R.string.fragment_calendar));

            }

        }

    }

