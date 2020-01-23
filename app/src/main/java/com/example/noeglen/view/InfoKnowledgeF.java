package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.noeglen.R;
import com.example.noeglen.data.FavoriteDTO;
import com.example.noeglen.data.KnowledgeDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Dette er artikel objektet der håndterer alt med viewet på den enkelte artikel og indsætte ting
 */

public class InfoKnowledgeF extends Fragment implements View.OnClickListener {

    /**
     * @variable textTITLE
     * textviewet hvor titlen skal være
     * @variable textBODY
     * textviewet hvor brødteksten ligger
     * @variable favButton
     * knappen der adder ting til favorit eller sletter fra favorit
     * @variable backgroundImage
     * baggrund imageviewet hos artiklen
     * @variable currentKnowledgeArticle
     * den nuværende artikel fra bundlet eller sharedpreferences
     * @variable favoriteList
     * listen af favoriter som kommer fra sharedpreferences
     * @variable bundle
     * bundlet er der hvor man henter artikelen fra
     * @variable gson
     * gson er et bibliotek der laver objekter om til json
     * @variable sPref
     * sPref er appens sharedpreference objekt, bliver også brugt til at hente data
     * @variable sEdit
     * sEdit bliver brugt til at gemme data på telefonen via sharedprefrences
     * @variable resID1
     * resID1 er IDet for den ene favorit billede som ligger i drawable mappen
     * @variable resID2
     * resID2 er også IDet for det andet favorit billede. Begge bliver brugt til at skifte imellem billeder når man trykker på dem
     */

    private TextView textTITLE, textBODY;
    private ImageView favButton, backgroundImage;

    private KnowledgeDTO currentKnowledgeArticle;
    private List<FavoriteDTO> favoriteList;

    private Bundle bundle;
    private Gson gson;
    private SharedPreferences sPref;
    private SharedPreferences.Editor sEdit;

    private int resID1, resID2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infoknowledge, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
    }

    /**
     * initialiserer alle de ting som skal bruges til dette fragment når det åbnes
     *
     * @param view
     * view variablen er brugt til at finde views på layoutet
     */

    private void initializeView(View view) {
        bundle = getArguments();
        gson = new Gson();

        textTITLE = view.findViewById(R.id.infoarticle_title);
        textBODY = view.findViewById(R.id.infoarticle_body);
        favButton = getView().findViewById(R.id.infoarticle_favbutton);
        favButton.setOnClickListener(this);
        backgroundImage = getView().findViewById(R.id.infoknowledge_backgroundimage);

        //Henter sharedpreferences fra strings filen
        sPref = getContext().getSharedPreferences(getString(R.string.sharedPreferencesKey),Context.MODE_PRIVATE);
        sEdit = sPref.edit();

        //finder favorit billederne i drawable
        resID1 = getContext().getResources().getIdentifier("fav1","drawable",getContext().getPackageName());
        resID2 = getContext().getResources().getIdentifier("fav2","drawable",getContext().getPackageName());

        //tjekker hvilken artikel det er, kigger i sharedpreferences og ser om det er en favorit eller ikke
        checkWhichKnowledgeArticle();
        getSharedPref();
        traverseThroughFavs();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {

        //hvis favorit knappen bliver trykket
        if (v == favButton){
            //hent shared preferences
            getSharedPref();

            //tjek om det er en favorit, hvis den er gå ind
            if (traverseThroughFavs()){
                //skifter billedet fra rød til blá
                favButton.setImageDrawable(getContext().getDrawable(resID1));
                //sletter favoritten fra sharedpreferences
                for (int i = 0; i < favoriteList.size(); i++) {
                    if (favoriteList.get(i).getTitle().equals(currentKnowledgeArticle.getTitle()) && favoriteList.get(i).getCURRENT_TYPE() == 3){
                        favoriteList.remove(i);
                        break;
                    }
                }
            }
            else {
                //hvis den ikke var en favorit, add den til favorit listen
                favButton.setImageDrawable(getContext().getDrawable(resID2));
                favoriteList.add(new FavoriteDTO(3,currentKnowledgeArticle.getImage(),currentKnowledgeArticle.getTitle(),currentKnowledgeArticle.getBody()));
            }
            //gem sharedpreferences
            saveSharedPref();
        }
    }

    /**
     * Finder ud af om det er et bundle som indeholder en artikel eller ikke
     */

    private void checkWhichKnowledgeArticle() {
        if (bundle != null){
            if (bundle.containsKey("currentKnowledgeArticle")){
                infoKnowledgeChecker();
            }
        }
    }

    /**
     * Tjekker om bundlet indeholder noget. Om det ikke indeholder noget er det en favorit, om det indeholder noget er det en del af bundlet
     */

    private void infoKnowledgeChecker() {
        String imageURL= "";
        Gson gson = new Gson();
        Type type = new TypeToken<KnowledgeDTO>(){}.getType();
        //tjekker bundlet
        currentKnowledgeArticle = gson.fromJson(bundle.getString("currentKnowledgeArticle"),type);
        if (currentKnowledgeArticle.getBody() == null){
            Type type1 = new TypeToken<FavoriteDTO>(){}.getType();
            //laver et objekt ud fra typen FavoriteDTO
            FavoriteDTO currentArticle = gson.fromJson(bundle.getString("currentKnowledgeArticle"),type1);
            //indsætter på fragmentet
            textTITLE.setText(currentArticle.getTitle());
            if (currentArticle.getBodyORweek() != null){
                textBODY.setText(Html.fromHtml(currentArticle.getBodyORweek(),Html.FROM_HTML_MODE_LEGACY));
                imageURL = currentArticle.getIamgeURL();
            }
        }
        //hvis det var en del af bundlet
        else {
            //Indsætter på fragmentet
            textTITLE.setText(currentKnowledgeArticle.getTitle());
            textBODY.setText(Html.fromHtml(currentKnowledgeArticle.getBody(),Html.FROM_HTML_MODE_LEGACY));
            imageURL = currentKnowledgeArticle.getImage();
        }

        //Indsætter billedet på imageviewet
        Glide
                .with(getContext())
                .load(imageURL)
                .into(backgroundImage);
    }

    /**
     * Går ingennem listen af favoritter og finder ud af om den nuværende artikel er en favorit
     *
     * @return
     * returnerer en boolean der fortæller om den var en favorit eller ej
     */

    private boolean traverseThroughFavs() {
        boolean isFavorited = false;
        for (int i = 0; i < favoriteList.size(); i++) {
            favButton.setImageDrawable(getContext().getDrawable(resID1));
            isFavorited = false;
            if (favoriteList.get(i).getTitle().equals(currentKnowledgeArticle.getTitle())){
                favButton.setImageDrawable(getContext().getDrawable(resID2));
                isFavorited = true;
                break;
            }
        }
        return isFavorited;
    }

    /**
     * Gemmer data lokalt via sharedpreferences
     */

    private void saveSharedPref() {
        String json = gson.toJson(favoriteList);
        sEdit.putString(getString(R.string.sPref_favorites),json);
        sEdit.commit();
    }

    /**
     * Henter data lokalt via shared preferences
     */

    private void getSharedPref() {
        String json = sPref.getString(getString(R.string.sPref_favorites),null);
        Type type = new TypeToken<List<FavoriteDTO>>(){}.getType();
        favoriteList = gson.fromJson(json,type);
        if (favoriteList == null) {
            favoriteList = new ArrayList<>();
        }
    }

}
