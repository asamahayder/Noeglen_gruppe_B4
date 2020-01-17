package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.data.DiaryDTO;
import com.example.noeglen.logic.CurrentDate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Diary2F extends Fragment implements View.OnClickListener {

    private String[] questions, answers;
    private String date;
    private TextView dateText, question1, question2, question3, question4,
                     answer1, answer2, answer3, answer4;
    private Bundle bundle;
    private ImageView imageView;
    private SharedPreferences sPref;
    private SharedPreferences.Editor sEdit;
    private List<DiaryDTO> listOfEntries;
    private Gson gson;
    private CurrentDate currentDate;
    private String image;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diary2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();



    }

    private void initializeView() {

        currentDate = CurrentDate.getInstance();
        date = new SimpleDateFormat("dd/M/yyyy").format(currentDate.getDate());
        dateText = getView().findViewById(R.id.textView3);




        questions = new String[4];
        answers = new String[4];
        gson = new Gson();
        String sPrefKey = "Noeglen.data";
        sPref = getContext().getSharedPreferences(sPrefKey, Context.MODE_PRIVATE);
        sEdit = sPref.edit();
        bundle = getArguments();
        questions = bundle.getStringArray("questions");
        getSharedPref("Diary");


        for (int i = 0; i < listOfEntries.size(); i++) {
            if (listOfEntries.get(i).getQuestions()[0].equals(questions[0])) {
                answers = listOfEntries.get(i).getAnswers();
                image = "emoji" + listOfEntries.get(i).getSmiley();
                int rec = getResources().getIdentifier(image,"drawable", this.getContext().getPackageName());
                imageView = getView().findViewById(R.id.imageView6);
                imageView.setImageDrawable(getContext().getDrawable(rec));

            }
        }


        dateText = getView().findViewById(R.id.textView3);




        question1 = getView().findViewById(R.id.textView8);
        question2 = getView().findViewById(R.id.textView9);
        question3 = getView().findViewById(R.id.textView10);
        question4 = getView().findViewById(R.id.textView11);
        answer1 = getView().findViewById(R.id.textView14);
        answer2 = getView().findViewById(R.id.textView15);
        answer3 = getView().findViewById(R.id.textView16);
        answer4 = getView().findViewById(R.id.textView17);

        dateText.setText(date);

        question1.setText(questions[0]);
        question2.setText(questions[1]);
        question3.setText(questions[2]);
        question4.setText(questions[3]);
        answer1.setText(answers[0]);
        answer2.setText(answers[1]);
        answer3.setText(answers[2]);
        answer4.setText(answers[3]);


    }

    @Override
    public void onClick(View view) {

    }

    private void getSharedPref(String sPrefEditKey) {
        String json = sPref.getString(sPrefEditKey, null);
        Type type = new TypeToken<List<DiaryDTO>>() {
        }.getType();
        listOfEntries = gson.fromJson(json, type);
        if (listOfEntries == null) {
            listOfEntries = new ArrayList<>();
        }

    }
}
