package com.example.noeglen.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.noeglen.R;

public class InfoKnowledgeF extends Fragment{

    private IMainActivity iMain;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infoknowledge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iMain = (IMainActivity) getActivity();


        btn1 = getView().findViewById(R.id.whatsStress);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoArticleF article = new InfoArticleF();
                Bundle bundle = new Bundle();
                bundle.putInt("textChanger", 1);
                iMain.setFragment(article,getString(R.string.fragment_infoarticle),true,bundle);

            }
        });

        btn2 = getView().findViewById(R.id.causesStress);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoArticleF article = new InfoArticleF();
                Bundle bundle = new Bundle();
                bundle.putInt("textChanger", 2);
                iMain.setFragment(article,getString(R.string.fragment_infoarticle),true,bundle);

            }
        });

        btn3 = getView().findViewById(R.id.signStress);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoArticleF article = new InfoArticleF();
                Bundle bundle = new Bundle();
                bundle.putInt("textChanger", 3);
                iMain.setFragment(article,getString(R.string.fragment_infoarticle),true,bundle);

            }
        });

        btn4 = getView().findViewById(R.id.adviceStress);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoArticleF article = new InfoArticleF();
                Bundle bundle = new Bundle();
                bundle.putInt("textChanger", 4);
                iMain.setFragment(article,getString(R.string.fragment_infoarticle),true,bundle);

            }
        });

        btn5 = getView().findViewById(R.id.treatmentStress);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoArticleF article = new InfoArticleF();
                Bundle bundle = new Bundle();
                bundle.putInt("textChanger", 5);
                iMain.setFragment(article,getString(R.string.fragment_infoarticle),true,bundle);
            }
        });

        btn6 = getView().findViewById(R.id.workStress);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoArticleF article = new InfoArticleF();
                Bundle bundle = new Bundle();
                bundle.putInt("textChanger", 6);
                iMain.setFragment(article,getString(R.string.fragment_infoarticle),true,bundle);

            }
        });

        btn7 = getView().findViewById(R.id.infoStress);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoArticleF article = new InfoArticleF();
                Bundle bundle = new Bundle();
                bundle.putInt("textChanger", 7);
                iMain.setFragment(article,getString(R.string.fragment_infoarticle),true,bundle);

            }
        });
    }
}
