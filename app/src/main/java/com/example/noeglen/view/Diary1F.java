package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.data.DiaryDTO;
import com.example.noeglen.logic.CurrentDate;
import com.google.gson.Gson;

public class Diary1F extends Fragment  implements View.OnClickListener{

    private Button a;
    private IMainActivity iMain;
    private TextView text;
    private CurrentDate currentDate;
    DiaryDTO diaryDTO;
    private Bundle bundle;
    private String[] answers,questions;
    private EditText answer1, answer2, answer3,answer4;
    private TextView question1,question2,question3, question4;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diary1, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentDate = CurrentDate.getInstance();
        text = getView().findViewById(R.id.textView3);
        text.setText(currentDate.getDateString());
       // text.setOnClickListener(this);

        a = getView().findViewById(R.id.gem);
        a.setOnClickListener(this);

        bundle = getArguments();
        questions = bundle.getStringArray("questions");

        answer1 = getView().findViewById(R.id.editText);
        answer2 = getView().findViewById(R.id.editText3);
        answer3 = getView().findViewById(R.id.editText4);
        answer4 = getView().findViewById(R.id.editText1);

        question1 = getView().findViewById(R.id.textView8);
        question2 = getView().findViewById(R.id.textView9);
        question3 = getView().findViewById(R.id.textView10);
        question4 = getView().findViewById(R.id.textView11);

        question1.setText(questions[0]);
        question2.setText(questions[1]);
        question3.setText(questions[2]);
        question4.setText(questions[3]);




    }

    @Override
    public void onClick(View view) {
        answers = new String[4];
        answers[0] = answer1.getText().toString();
        answers[1] = answer2.getText().toString();
        answers[2] = answer3.getText().toString();
        answers[3] = answer4.getText().toString();



        diaryDTO  = new DiaryDTO(bundle.getInt("smiley"), answers,currentDate);
        /*System.out.println(diaryDTO.getQuestions()[0]);
        System.out.println(diaryDTO.getQuestions()[1]);
        System.out.println(diaryDTO.getQuestions()[2]);
        System.out.println(diaryDTO.getDate());
        System.out.println(diaryDTO.getSmiley());*/


        String tag ="";

        if (view == a){
                tag = getString(R.string.fragment_calendar);
                DiaryFCalendar diaryFCalendar = new DiaryFCalendar();
                Bundle bundle1 = new Bundle();
            Gson gson = new Gson();
            String json = gson.toJson(diaryDTO);
            bundle1.putString("diaryDTO",json);
                iMain.setFragment(diaryFCalendar,tag,true,bundle1);

            }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }
}