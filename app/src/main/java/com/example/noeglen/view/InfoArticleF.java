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
import com.example.noeglen.data.ArticleDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class InfoArticleF extends Fragment {

    private TextView textTITLE, textHEADER, textBODY;
    private ArticleDTO article;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infoarticle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        checkWhichArticle(view);
    }

    private void initializeView(View view) {
        bundle = getArguments();
        textTITLE = view.findViewById(R.id.infoarticle_title);
        textHEADER = view.findViewById(R.id.infoarticle_header);
        textBODY = view.findViewById(R.id.infoarticle_body);
    }

    private void checkWhichArticle(View view) {
        if (bundle != null){
            if (bundle.containsKey("textChanger")){
                infoKnowledgeChecker(view);
            }
            if (bundle.containsKey("currentArticle")){
                articleMainChecker(view);
            }
        }
    }


    private void infoKnowledgeChecker(View view) {
        textBODY.setMovementMethod(LinkMovementMethod.getInstance());
        int num = bundle.getInt("textChanger");
        if (num == 1){
            textTITLE.setText(R.string.whatIsStrsssTitle);
            textHEADER.setVisibility(View.GONE);
            textBODY.setText(R.string.whatIsStrsssBody);
        }else if (num == 2){
            textTITLE.setText(R.string.whatCausesStrsssTitle);
            textHEADER.setVisibility(View.GONE);
            textBODY.setText(R.string.whatCausesStrsssBody);
        }else if (num == 3){
            textTITLE.setText(R.string.howDoesStressShowTitle);
            textHEADER.setVisibility(View.GONE);
            textBODY.setText(R.string.howDoesStressShowBody);
        }else if (num == 4){
            textTITLE.setText(R.string.goodAdviceForTheAffectedPersonsTitle);
            textHEADER.setVisibility(View.GONE);
            textBODY.setText(R.string.goodAdviceForTheAffectedPersonsBody);
        }else if (num == 5){
            textTITLE.setText(R.string.treatmentForStressTitle);
            textHEADER.setVisibility(View.GONE);
            textBODY.setText(R.string.treatmentForStressBody);
        }else if (num == 6){
            textTITLE.setText(R.string.stressAndWorkTitle);
            textHEADER.setVisibility(View.GONE);
            textBODY.setText(R.string.stressAndWorkBody);
        }else if (num == 7){
            textTITLE.setText(R.string.getMoreInfoAboutStressTitle);
            textHEADER.setVisibility(View.GONE);
            textBODY.setText(R.string.getMoreInfoAboutStressBody);
        }
    }

    private void articleMainChecker(View view) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArticleDTO>(){}.getType();
        article = gson.fromJson(bundle.getString("currentArticle"),type);

        textTITLE.setText(article.getTitle());
        textHEADER.setText(article.getHeader());
        textBODY.setText(article.getBody());
    }
}
