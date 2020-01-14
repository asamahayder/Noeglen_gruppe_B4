package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.List;

public class Diary1F extends Fragment  implements View.OnClickListener{

    private IMainActivity iMain;

    private Button saveButton;
    private TextView dateText, question1,question2,question3, question4;
    private EditText answer1, answer2, answer3,answer4;

    private CurrentDate currentDate;
    private DiaryDTO diaryDTO;

    private Bundle bundle;
    private SharedPreferences sPref;
    private SharedPreferences.Editor sEdit;

    private String[] answers,questions;
    private String date;


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
        bundle = getArguments();

        currentDate = CurrentDate.getInstance();
        date = currentDate.getDateString();
        answers = new String[4];

        dateText = getView().findViewById(R.id.textView3);

        saveButton = getView().findViewById(R.id.gem);
        saveButton.setOnClickListener(this);

        questions = bundle.getStringArray("questions");

        answer1 = getView().findViewById(R.id.editText);
        answer2 = getView().findViewById(R.id.editText3);
        answer3 = getView().findViewById(R.id.editText4);
        answer4 = getView().findViewById(R.id.editText1);

        question1 = getView().findViewById(R.id.textView8);
        question2 = getView().findViewById(R.id.textView9);
        question3 = getView().findViewById(R.id.textView10);
        question4 = getView().findViewById(R.id.textView11);

        dateText.setText(date);
        question1.setText(questions[0]);
        question2.setText(questions[1]);
        question3.setText(questions[2]);
        question4.setText(questions[3]);
    }

    @Override
    public void onClick(View view) {
        saveDiaryDTO();
        if (view == saveButton){
                String tag = getString(R.string.fragment_calendar);
                DiaryFCalendar diaryFCalendar = new DiaryFCalendar();
                Bundle bundle1 = new Bundle();

                Gson gson = new Gson();
                String json = gson.toJson(diaryDTO);
                bundle1.putString("diaryDTO",json);
                iMain.setFragment(diaryFCalendar,tag,true,bundle1);

            }
    }

    private void saveDiaryDTO() {
        String sPrefKey = "Noeglen.data";
        String sPrefEditKey = "Diary";
        getSharedPref(sPrefKey, sPrefEditKey);
        answers[0] = answer1.getText().toString();
        answers[1] = answer2.getText().toString();
        answers[2] = answer3.getText().toString();
        answers[3] = answer4.getText().toString();
        diaryDTO  = new DiaryDTO(bundle.getInt("smiley"), answers,date);
        saveSharedPref(sPrefKey, sPrefEditKey);
    }

    private void getSharedPref(String sPrefKey, String sPrefEditKey) {
        List<DiaryDTO> listOfEntries;

    }

    private void saveSharedPref(String sPrefKey, String sPrefEditKey) {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }
}