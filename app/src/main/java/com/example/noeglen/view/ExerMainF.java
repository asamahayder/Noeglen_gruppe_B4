package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;

public class ExerMainF extends Fragment implements View.OnClickListener {

    private IMainActivity iMain;
    private String fragmentTag;
    private CardView exer_1, exer_2, exer_3, exer_4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exermain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    public void initializeView(){

        exer_1 = getView().findViewById(R.id.exer_1);
        exer_2 = getView().findViewById(R.id.exer_2);
        exer_3 = getView().findViewById(R.id.exer_3);
        exer_4 = getView().findViewById(R.id.exer_4);

        exer_1.setOnClickListener(this);
        exer_2.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.exer_1:
                fragmentTag = getString(R.string.fragment_exerexer);
                break;
            case R.id.exer_2:
                fragmentTag = getString(R.string.fragment_exer_2);
                break;
        }
        iMain.inflateFragment(fragmentTag);
    }
}