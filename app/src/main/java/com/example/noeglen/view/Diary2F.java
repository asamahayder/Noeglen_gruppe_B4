package com.example.noeglen.view;

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

public class Diary2F extends Fragment implements View.OnClickListener {

    private String [] questions,answers;
    private String date;
    private TextView dateText, question1,question2,question3, question4,
                     answer1, answer2, answer3,answer4;
    private Bundle bundle;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diary2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();

        imageView = getView().findViewById(R.id.imageView6);
        imageView.setBackground(getView().getResources().getDrawable(R.drawable.emoji3));



    }

    private void initializeView() {
        questions = new String[4];
        answers   = new String[4];

        bundle = getArguments();
        dateText = getView().findViewById(R.id.textView3);

        questions = bundle.getStringArray("questions");

        question1 = getView().findViewById(R.id.textView8);
        question2 = getView().findViewById(R.id.textView9);
        question3 = getView().findViewById(R.id.textView10);
        question4 = getView().findViewById(R.id.textView11);
        answer1   = getView().findViewById(R.id.textView14);
        answer2   = getView().findViewById(R.id.textView15);
        answer3   = getView().findViewById(R.id.textView16);
        answer4   = getView().findViewById(R.id.textView17);

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

}
