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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.noeglen.R;
import com.example.noeglen.data.FavoriteDTO;
import com.example.noeglen.data.KnowledgeDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InfoKnowledgeF extends Fragment implements View.OnClickListener {

    private TextView textTITLE, textBODY;
    private ImageView backButton, favButton, backgroundImage;

    private KnowledgeDTO currentKnowledgeArticle;
    private List<FavoriteDTO> favoriteList;

    private Bundle bundle;
    private Gson gson;
    private SharedPreferences sPref;
    private SharedPreferences.Editor sEdit;

    private int resID1, resID2;

    private IMainActivity iMain;

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

    private void initializeView(View view) {
        bundle = getArguments();
        gson = new Gson();

        textTITLE = view.findViewById(R.id.infoarticle_title);
        textBODY = view.findViewById(R.id.infoarticle_body);
        //backButton = getView().findViewById(R.id.infoarticle_backbutton);
        //backButton.setOnClickListener(this);
        favButton = getView().findViewById(R.id.infoarticle_favbutton);
        favButton.setOnClickListener(this);
        backgroundImage = getView().findViewById(R.id.infoknowledge_backgroundimage);

        sPref = getContext().getSharedPreferences(getString(R.string.sharedPreferencesKey),Context.MODE_PRIVATE);
        sEdit = sPref.edit();

        resID1 = getContext().getResources().getIdentifier("fav1","drawable",getContext().getPackageName());
        resID2 = getContext().getResources().getIdentifier("fav2","drawable",getContext().getPackageName());

        checkWhichKnowledgeArticle();
        getSharedPref();
        traverseThroughFavs();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {

        if (v == favButton){
            getSharedPref();

            if (traverseThroughFavs()){
                favButton.setImageDrawable(getContext().getDrawable(resID1));
                System.out.println("blue");
                for (int i = 0; i < favoriteList.size(); i++) {
                    if (favoriteList.get(i).getTitle().equals(currentKnowledgeArticle.getTitle()) && favoriteList.get(i).getCURRENT_TYPE() == 3){
                        favoriteList.remove(i);
                        break;
                    }
                }
            }
            else {
                favButton.setImageDrawable(getContext().getDrawable(resID2));
                System.out.println("red");
                favoriteList.add(new FavoriteDTO(3,currentKnowledgeArticle.getImage(),currentKnowledgeArticle.getTitle(),currentKnowledgeArticle.getBody()));
            }
            System.out.println(favoriteList.size());
            saveSharedPref();
        }
        if (v == backButton){
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(InfoKnowledgeF.this);
            trans.commit();
            manager.popBackStack();
            iMain.inflateFragment(getString(R.string.fragment_infoknowledgemain), false);
        }
    }

    private void checkWhichKnowledgeArticle() {
        if (bundle != null){
            if (bundle.containsKey("currentKnowledgeArticle")){
                infoKnowledgeChecker();
            }
        }
    }

    private void infoKnowledgeChecker() {
        String imageURL= "";
        Gson gson = new Gson();
        Type type = new TypeToken<KnowledgeDTO>(){}.getType();
        currentKnowledgeArticle = gson.fromJson(bundle.getString("currentKnowledgeArticle"),type);
        if (currentKnowledgeArticle.getBody() == null){
            Type type1 = new TypeToken<FavoriteDTO>(){}.getType();
            FavoriteDTO currentArticle = gson.fromJson(bundle.getString("currentKnowledgeArticle"),type1);
            textTITLE.setText(currentArticle.getTitle());
            if (currentArticle.getBodyORweek() != null){
                textBODY.setText(Html.fromHtml(currentArticle.getBodyORweek(),Html.FROM_HTML_MODE_LEGACY));
                imageURL = currentArticle.getIamgeURL();
            }
        }
        else {
            textTITLE.setText(currentKnowledgeArticle.getTitle());
            textBODY.setText(Html.fromHtml(currentKnowledgeArticle.getBody(),Html.FROM_HTML_MODE_LEGACY));
            imageURL = currentKnowledgeArticle.getImage();
        }

        Glide
                .with(getContext())
                .load(imageURL)
                .into(backgroundImage);
    }

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

    private void saveSharedPref() {
        String json = gson.toJson(favoriteList);
        sEdit.putString(getString(R.string.sPref_favorites),json);
        sEdit.commit();
    }

    private void getSharedPref() {
        String json = sPref.getString(getString(R.string.sPref_favorites),null);
        Type type = new TypeToken<List<FavoriteDTO>>(){}.getType();
        favoriteList = gson.fromJson(json,type);
        if (favoriteList == null) {
            favoriteList = new ArrayList<>();
        }
    }

}
