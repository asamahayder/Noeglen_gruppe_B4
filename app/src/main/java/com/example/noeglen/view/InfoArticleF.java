package com.example.noeglen.view;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
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
                headline.setText("Hvad er stress?");
                body.setText(R.string.whatsStress);
            }else if (num == 2){
                headline.setText("Hvad skyldes stress?");
                body.setText(R.string.causesStress);
            }else if (num == 3){
                headline.setText("Hvordan viser stress sig?");
                body.setText(R.string.signStress);
            }else if (num == 4){
                headline.setText("Gode råd til pårørende");
                body.setText(R.string.adviceStress);
            }else if (num == 5){
                headline.setText("Behandling af stress");
                body.setText(R.string.treatmentStress);
            }else if (num == 6){
                headline.setText("Stress og arbejde");
                body.setText(R.string.workStress);
            }else if (num == 7){
                headline.setText("Få mere at vide om stress");
                body.setText(R.string.infoStress);
                body.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }
}
