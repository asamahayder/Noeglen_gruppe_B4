package com.example.noeglen.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;


public class InfoArticleF extends Fragment {
    TextView headline, body;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infoarticle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        headline = view.findViewById(R.id.headline);
        body = view.findViewById(R.id.body);
        if (bundle != null){
            int num = bundle.getInt("textChanger");
            if (num == 1){
                System.out.println(num);
                headline.setText("Hvad er stress?");
                body.setText(R.string.hvadErStress);
            }else if (num == 2){
                System.out.println(num);
            }else if (num == 3){
                System.out.println(num);
            }
        }
    }
}
