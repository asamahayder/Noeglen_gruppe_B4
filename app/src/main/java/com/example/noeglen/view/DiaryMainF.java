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
import com.example.noeglen.data.CurrentDate;

import java.text.SimpleDateFormat;


public class DiaryMainF extends Fragment implements View.OnClickListener{

   private TextView datefelt;
   private CurrentDate currentDate;
   private ImageView imageView;
   private IMainActivity iMain;
   private int  smiley;
   private String [] questions;
   private String date;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diarymain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        datefelt = getView().findViewById(R.id.currDate);
        Bundle bundle = getArguments();
        currentDate = CurrentDate.getInstance();

        if (bundle != null){
            date = bundle.getString("date");
            if (date == null){
                date = new SimpleDateFormat("dd/M/yyyy").format(currentDate.getDate());
            }
        }else{
            date = new SimpleDateFormat("dd/M/yyyy").format(currentDate.getDate());
        }


        datefelt.setText(date);
        datefelt.setOnClickListener(this);

        imageView = getView().findViewById(R.id.imageView3);

        questions = new String[4];

        SeekBar seekBar = getView().findViewById(R.id.simpleSeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // Bestemmer emojien og spørgsmålerne efter brugernes humør-valg
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


                if (seekBar.getProgress() > 0 && seekBar.getProgress() <= 20) {
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji5));
                    smiley = 5;
                    questions[0] = "Hvad har været den største årsag? ";
                    questions[1] = "Hvordan reagerede du til denne årsag?";
                    questions[2] = "Hvordan blev situationen bedre efter din reaktion?";
                    questions[3] = "Andet du vil tilføje?";

                }else if (seekBar.getProgress() > 20 && seekBar.getProgress() <= 40){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji4));
                    smiley = 4;
                    questions[0] = "Hvad gik dårligt i dag? ";
                    questions[1] = "Hvordan havde du håndteret det?";
                    questions[2] = "Hvordan vil du indgår de dårlige ting fremover?";
                    questions[3] = "Hvad kan du gøre anderledes næste gang du oplever samme negative årsag for at gøre situationen bedre?";

                }else if (seekBar.getProgress() > 40 && seekBar.getProgress() <= 60){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji3));
                    smiley = 3;
                    questions[0] = "Hvad gik godt i dag? ";
                    questions[1] = "Hvordan fik dette dig til at føle?";
                    questions[2] = "Hvordan kan du genskabe denne gode følelse i morgen?";


                }else if (seekBar.getProgress() > 60 && seekBar.getProgress() <= 80){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji2));
                    smiley = 2;
                    questions[0] = " Hvad har været den bedste del af dagen? ";
                    questions[1] = "Hvad har du gjort anderledes i dag end andre dage?";
                    questions[2] = "Hvordan ville du have det, hvis du også havde en god dag i morgen?";
                    questions[3] = "Hvad kan du gøre for at få endnu en god dag i morgen?";
                }else if (seekBar.getProgress() > 80 && seekBar.getProgress() <= 100){
                    imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji1));
                    smiley = 1;
                    questions[0] = " Hvad har været den bedste del af dagen? ";
                    questions[1] = "Hvad har du gjort anderledes i dag end andre dage?";
                    questions[2] = "Hvordan ville du have det, hvis du også havde en god dag i morgen?";
                    questions[3] = "Hvad kan du gøre for at få endnu en god dag i morgen?";
                }


            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            // Åbner Diary1 og gemmer emojien, spørgsmålerne, og datoen
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String mm = "";
                if (seekBar.getProgress() > -20 && seekBar.getProgress() <= 20){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    bundle.putString("date", date);
                    Diary1F diary1F = new Diary1F();
                    iMain.setFragment(diary1F,mm,true,bundle);
                }else if ((seekBar.getProgress() > 20 && seekBar.getProgress() <= 40)){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    bundle.putString("date", date);
                    Diary1F diary1F = new Diary1F();
                    iMain.setFragment(diary1F,mm,true,bundle);
                }else if (seekBar.getProgress() > 40 && seekBar.getProgress() <= 60){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    bundle.putString("date", date);
                    Diary1F diary1F = new Diary1F();
                    iMain.setFragment(diary1F,mm,true,bundle);
                }else if (seekBar.getProgress() > 60 && seekBar.getProgress() <= 80){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    bundle.putString("date", date);
                    Diary1F diary1F = new Diary1F();
                    iMain.setFragment(diary1F,mm,true,bundle);
                }else if (seekBar.getProgress() > 80 && seekBar.getProgress() <= 120){
                    mm = getString(R.string.fragment_diary1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("smiley", smiley);
                    bundle.putStringArray("questions",questions);
                    bundle.putString("date", date);
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
    // Åbner kalendaren hvis brugeren trykker på Datofeltet
    @Override
    public void onClick(View view) {
        String tag ="";

        if (view == datefelt){
            DiaryFCalendar diaryFCalendar = new DiaryFCalendar();
            iMain.inflateFragment(getString(R.string.fragment_calendar));

            }

        }

    }

