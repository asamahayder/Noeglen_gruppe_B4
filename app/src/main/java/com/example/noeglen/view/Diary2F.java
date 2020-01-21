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
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Diary2F extends Fragment implements View.OnClickListener {

    private String[] questions, answers;
    private String date;
    private TextView dateText, question1, question2, question3, question4;
    private EditText answer1, answer2, answer3, answer4;
    private Bundle bundle;
    private ImageView imageView;
    private SharedPreferences sPref;
    private SharedPreferences.Editor sEdit;
    private List<DiaryDTO> listOfEntries;
    private Gson gson;
    private CurrentDate currentDate;
    private String image;
    private IMainActivity iMain;
    private ImageView editKnap;
    private Button gem;
    private DiaryDTO diaryDTO;


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
        dateText = getView().findViewById(R.id.textView3);

        questions = new String[4];
        answers = new String[4];
        gson = new Gson();
        String sPrefKey = getString(R.string.sharedPreferencesKey);
        sPref = getContext().getSharedPreferences(sPrefKey, Context.MODE_PRIVATE);
        sEdit = sPref.edit();
        bundle = getArguments();
        if (bundle != null){
            date = bundle.getString("date");
        }else{
            currentDate = CurrentDate.getInstance();
            date = new SimpleDateFormat("dd/M/yyyy").format(currentDate.getDate());
        }


      //  questions = bundle.getStringArray("questions");
        getListOfEntries("Diary");

        for (int i = 0; i < listOfEntries.size(); i++) {
            if (listOfEntries.get(i).getDate().equals(date)){
                diaryDTO = listOfEntries.get(i);
            }
        }
        questions = diaryDTO.getQuestions();
        answers = diaryDTO.getAnswers();
        image = "emoji" + diaryDTO.getSmiley();
        int rec = getResources().getIdentifier(image,"drawable", this.getContext().getPackageName());
        imageView = getView().findViewById(R.id.imageView6);
        imageView.setImageDrawable(getContext().getDrawable(rec));



      /*  for (int i = 0; i < listOfEntries.size(); i++) {
            if (listOfEntries.get(i).getQuestions()[0].equals(questions[0])) {
                answers = listOfEntries.get(i).getAnswers();
                image = "emoji" + listOfEntries.get(i).getSmiley();
                int rec = getResources().getIdentifier(image,"drawable", this.getContext().getPackageName());
                imageView = getView().findViewById(R.id.imageView6);
                imageView.setImageDrawable(getContext().getDrawable(rec));

            }
        }  */


        dateText = getView().findViewById(R.id.textView3);
        editKnap = getView().findViewById(R.id.editKnap);
        editKnap.setOnClickListener(this);
        gem = getView().findViewById(R.id.gem);
        gem.setOnClickListener(this);

        question1 = getView().findViewById(R.id.question1);
        question2 = getView().findViewById(R.id.question2);
        question3 = getView().findViewById(R.id.question3);
        question4 = getView().findViewById(R.id.question4);

        answer1 = getView().findViewById(R.id.answer1);
        answer1.setFocusable(false);
        answer2 = getView().findViewById(R.id.answer2);
        answer2.setFocusable(false);
        answer3 = getView().findViewById(R.id.answer3);
        answer3.setFocusable(false);
        answer4 = getView().findViewById(R.id.answer4);
        answer4.setFocusable(false);

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

    private void getListOfEntries(String sPrefEditKey) {
        String json = sPref.getString(sPrefEditKey, null);
        System.out.println(json + "##########");
        Type type = new TypeToken<List<DiaryDTO>>() {
        }.getType();
        listOfEntries = gson.fromJson(json, type);
        if (listOfEntries == null) {
            listOfEntries = new ArrayList<>();
        }

    }
    private void saveDiaryDTO() {
        for (int i = 0; i < listOfEntries.size(); i++) {
            if (listOfEntries == null ){
                String sPrefEditKey = "Diary";
                getListOfEntries(sPrefEditKey);
                diaryDTO  = new DiaryDTO(bundle.getInt("smiley"), answers,questions,date);
                listOfEntries.add(diaryDTO);
                saveSharedPref(sPrefEditKey);
            } else {
                
            }
        }


    }

    @Override
    public void onClick(View v) {
        if (v == editKnap ) {
            answer1.setFocusableInTouchMode(true);
            answer2.setFocusableInTouchMode(true);
            answer3.setFocusableInTouchMode(true);
            answer4.setFocusableInTouchMode(true);

        }else if (v == gem){


            iMain.inflateFragment(getString(R.string.fragment_calendar),true);

            /*String tag = getString(R.string.fragment_calendar);
            DiaryFCalendar diaryFCalendar = new DiaryFCalendar();
            Bundle bundle1 = new Bundle();
            iMain.setFragment(diaryFCalendar,tag,true,bundle1);*/
        }
    }
    private void saveSharedPref(String sPrefEditKey) {
        String json = gson.toJson(listOfEntries);
        sEdit.putString(sPrefEditKey,json);
        sEdit.commit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }
}
