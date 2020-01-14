package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.noeglen.R;
import com.example.noeglen.data.ArticleDTO;
import com.example.noeglen.data.ExerciseDTO;
import com.example.noeglen.data.FavoritesDTO;
import com.example.noeglen.data.VideoDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InfoArticleF extends Fragment implements View.OnClickListener {

    private TextView textTITLE, textHEADER, textBODY;
    private ImageView backButton, favButton;

    private ArticleDTO article;
    private FavoritesDTO favorites;

    private Bundle bundle;
    private Gson gson;
    private SharedPreferences sPref;
    private SharedPreferences.Editor sEdit;

    private int resID1, resID2;

    private String sPrefKey, sPrefFavKey;

    private IMainActivity iMain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infoarticle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
    }

    private void initializeView(View view) {
        bundle = getArguments();
        gson = new Gson();

        textTITLE = view.findViewById(R.id.infoarticle_title);
        textHEADER = view.findViewById(R.id.infoarticle_header);
        textBODY = view.findViewById(R.id.infoarticle_body);
        backButton = getView().findViewById(R.id.infoarticle_backbutton);
        backButton.setOnClickListener(this);
        favButton = getView().findViewById(R.id.infoarticle_favbutton);
        favButton.setOnClickListener(this);

        sPrefKey = "Noeglen.data"; sPrefFavKey = "Favorites";
        sPref = getContext().getSharedPreferences(sPrefKey,Context.MODE_PRIVATE);
        sEdit = sPref.edit();

        resID1 = getContext().getResources().getIdentifier("fav1","drawable",getContext().getPackageName());
        resID2 = getContext().getResources().getIdentifier("fav2","drawable",getContext().getPackageName());

        checkWhichArticle(view);
        getSharedPref();
        traverseThroughFavs();
    }

    private boolean traverseThroughFavs() {
        boolean isFavorited = false;
        for (int i = 0; i < favorites.getListOfArticleDTOS().size(); i++) {
            favButton.setImageDrawable(getContext().getDrawable(resID1));
            isFavorited = false;
            if (favorites.getListOfArticleDTOS().get(i).getTitle().equals(article.getTitle())){
                favButton.setImageDrawable(getContext().getDrawable(resID2));
                isFavorited = true;
                break;
            }
        }
        System.out.println("asd 69 69");
        return isFavorited;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
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

    @Override
    public void onClick(View v) {

        if (v == favButton){
            getSharedPref();

            if (traverseThroughFavs()){
                favButton.setImageDrawable(getContext().getDrawable(resID1));
                System.out.println("blue");
                for (int i = 0; i < favorites.getListOfArticleDTOS().size(); i++) {
                    if (favorites.getListOfArticleDTOS().get(i).getTitle().equals(article.getTitle())){
                        favorites.getListOfArticleDTOS().remove(i);
                        break;
                    }
                }
            }
            else {
                favButton.setImageDrawable(getContext().getDrawable(resID2));
                System.out.println("red");
                favorites.getListOfArticleDTOS().add(article);
            }
            System.out.println(favorites.getListOfArticleDTOS().size());
            saveSharedPref();
        }
        if (v == backButton){
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(InfoArticleF.this);
            trans.commit();
            manager.popBackStack();
            iMain.inflateFragment(getString(R.string.fragment_infoarticlesmain), false);
        }
    }

    private void saveSharedPref() {
        String json = gson.toJson(favorites);
        sEdit.putString(sPrefFavKey,json);
        sEdit.commit();
    }

    private void getSharedPref() {
        String json = sPref.getString(sPrefFavKey,null);
        Type type = new TypeToken<FavoritesDTO>(){}.getType();
        favorites = gson.fromJson(json,type);
        if (favorites == null) {
            favorites = new FavoritesDTO(new ArrayList<ArticleDTO>(), new ArrayList<VideoDTO>(), new ArrayList<ExerciseDTO>());
        }
    }

}
