package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class Diary1F extends Fragment  implements View.OnClickListener{

    private IMainActivity iMain;
    private Button saveButton;
    private EditText answer1, answer2, answer3,answer4;
    private DiaryDTO diaryDTO;
    private Bundle bundle;
    private Gson gson;
    private SharedPreferences sPref;
    private SharedPreferences.Editor sEdit;
    private String[] answers,questions;
    private String date;
    private String currentDate;
    private List<DiaryDTO> listOfEntries;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diary1, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();


    }

    private void initializeView() {
        bundle = getArguments();
        date = bundle.getString("date");
        CurrentDate currentDateInstace = CurrentDate.getInstance();
        currentDate = new SimpleDateFormat("dd/M/yyyy").format(currentDateInstace.getDate());


        if(date == null){
            date = currentDate;
        }

        answers = new String[4];

        TextView dateText = getView().findViewById(R.id.textView3);
        dateText.setText(date);

        saveButton = getView().findViewById(R.id.gem);
        saveButton.setOnClickListener(this);

        String image = "emoji" + bundle.getInt("smiley");
        int rec = getResources().getIdentifier(image,"drawable", this.getContext().getPackageName());

        ImageView imageView = getView().findViewById(R.id.imageView6);
        imageView.setImageDrawable(getContext().getDrawable(rec));

        questions = bundle.getStringArray("questions");

        answer1 = getView().findViewById(R.id.answer1);
        answer2 = getView().findViewById(R.id.answer2);
        answer3 = getView().findViewById(R.id.answer3);
        answer4 = getView().findViewById(R.id.answer4);

        TextView question1 = getView().findViewById(R.id.question1);
        TextView question2 = getView().findViewById(R.id.question2);
        TextView question3 = getView().findViewById(R.id.question3);
        TextView question4 = getView().findViewById(R.id.question4);

        question1.setText(questions[0]);
        question2.setText(questions[1]);
        question3.setText(questions[2]);
        question4.setText(questions[3]);


        if (bundle.getInt("smiley") == 3) {
            question4.setVisibility(View.GONE);
            answer4.setVisibility(View.GONE);
        }

        gson = new Gson();
        String sPrefKey = getString(R.string.sharedPreferencesKey);
        sPref = getContext().getSharedPreferences(sPrefKey,Context.MODE_PRIVATE);
        sEdit = sPref.edit();
    }

    @Override
    public void onClick(View view) {
        if (view == saveButton){
                saveDiaryDTO();
                String tag = getString(R.string.fragment_affirmationer);
                DiaryAffirmations diaryAffirmations = new DiaryAffirmations();

                Bundle bundle1 = new Bundle();
                bundle1.putString("date",date);

                Gson gson = new Gson();
                String json = gson.toJson(diaryDTO);
                bundle1.putString("diaryDTO",json);
                bundle1.putStringArray("questions", questions);

                iMain.setFragment(diaryAffirmations,tag,true,bundle1);
            }
    }

    private void saveDiaryDTO() {
        String sPrefEditKey = getString(R.string.diaryListKey);
        getListOfEntries(sPrefEditKey);

        answers[0] = answer1.getText().toString();
        answers[1] = answer2.getText().toString();
        answers[2] = answer3.getText().toString();
        answers[3] = answer4.getText().toString();
        diaryDTO  = new DiaryDTO(bundle.getInt("smiley"), answers,questions,date);

        listOfEntries.add(diaryDTO);
        saveSharedPref(sPrefEditKey);
        handleMarkTodaysDiaryAsDone();
    }

    private void getListOfEntries(String sPrefEditKey) {
        String json = sPref.getString(sPrefEditKey,null);
        Type type = new TypeToken<List<DiaryDTO>>(){}.getType();
        listOfEntries = gson.fromJson(json,type);
        if (listOfEntries == null){
            listOfEntries = new ArrayList<>();
        }

    }

    private void saveSharedPref(String sPrefEditKey) {
        String json = gson.toJson(listOfEntries);
        sEdit.putString(sPrefEditKey,json);
        sEdit.commit();

        getListOfEntries(sPrefEditKey);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    public void handleMarkTodaysDiaryAsDone(){
        if (date.equals(currentDate)) {
            SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.sharedPreferencesKey), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(getString(R.string.isTodaysDiaryWritten), "true");
            editor.apply();
        }
    }
}