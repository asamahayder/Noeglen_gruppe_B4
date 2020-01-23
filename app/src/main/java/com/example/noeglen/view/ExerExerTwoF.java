package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.data.ExerciseDTO;
import com.example.noeglen.data.FavoriteDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExerExerTwoF extends Fragment implements View.OnClickListener {
    private Button btnPlay;
    private TextView tTitle, tDesc;
    private MediaPlayer mp;
    private IMainActivity iMain;
    private ImageView bAddToFav;
    private int primaryDark;
    private List<FavoriteDTO> favList;
    private SharedPreferences sPref;
    private SharedPreferences.Editor sEdit;
    private Gson gson;
    private ExerciseDTO currExercise;
    private int resID1, resID2;
    private boolean isFavorite;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exer_soundfile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    private void initializeView() {
        bAddToFav = getView().findViewById(R.id.bAddToFav);
        btnPlay = getView().findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);
        bAddToFav.setOnClickListener(this);

        tTitle = getView().findViewById(R.id.tExerTitle);
        tDesc  = getView().findViewById(R.id.tExerDesc);
        currExercise = new ExerciseDTO(tTitle.getText().toString(),tDesc.getText().toString(),"https://i.imgur.com/HHTC6Eu.png");

        gson = new Gson();
        sPref = getContext().getSharedPreferences(getString(R.string.sharedPreferencesKey),Context.MODE_PRIVATE);
        sEdit = sPref.edit();

        resID1 = getContext().getResources().getIdentifier("fav1","drawable",getContext().getPackageName());
        resID2 = getContext().getResources().getIdentifier("fav2","drawable",getContext().getPackageName());


        getSharedPref();
        checkIfCurrExerIsFav();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }
    /**
     *
     * Denne er lavet for at stoppe lydfilen, når man klikker videre til et andet fragment i navbaren.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    /**
     * Onclick gør to forskellige ting, alt efter hvilken knap man klikker på
     * Den første som er start knappen for lyden, her skifter den designet af knappen, og starter/stopper lydfilen
     *
     * Den anden del, er til favorit knappen. Den skifter så mellem hvordan imageviewet skal se ud (den skifter mellem to drawabels).
     * @param
     */

    @Override
    public void onClick(View v) {
        if (v == btnPlay) {
            if (mp == null) {
                int primaryOrange = getResources().getColor(R.color.primaryOrange);
                mp = MediaPlayer.create(getActivity(), R.raw.stressoevelselydfile);
                btnPlay.setText("Stop");
                btnPlay.setTextColor(primaryOrange);
                btnPlay.setBackgroundResource(R.drawable.orange_border);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        stopMediaPlayer();
                    }
                });
            } else if (mp != null) {
                stopMediaPlayer();
            }
        }
        if(v == bAddToFav) {
            if (addORemoveFromFav()) {
                bAddToFav.setBackground(getContext().getDrawable(resID2));
            } else {
                bAddToFav.setBackground(getContext().getDrawable(resID1));
            }
        }
    }

    /**
     * Denne metode sørger for at stoppe lydfilen og ændre designet af knappen.
     */

    public void stopMediaPlayer() {
        if (mp != null) {
            primaryDark = getResources().getColor(R.color.comeback_green_dark);
            btnPlay.setText("Afspil");
            btnPlay.setTextColor(primaryDark);
            btnPlay.setBackgroundResource(R.drawable.dark_green_border);
            mp.release();
            mp = null;
        }
    }
    /**
     * Det som denne metode gør, er at tjekke om man har øvelsen til favorit eller ej.
     * Her gemmer den omkring hvilken drawable der skal vise, alt efter om man har valgt den som favorit eller ej
     */
    private boolean checkIfCurrExerIsFav() {
        for (int i = 0; i < favList.size(); i++) {
            if (favList.get(i).getTitle().equals(currExercise.getTitle()) && favList.get(i).getCURRENT_TYPE() == 2){
                bAddToFav.setBackground(getContext().getDrawable(resID2));
                isFavorite = true;
                break;
            }
            else {
                bAddToFav.setBackground(getContext().getDrawable(resID1));
                isFavorite = false;
            }
        }
        return isFavorite;
    }

    /**
     * Det som denne metode gør, er at tilføje eller fjerne øvelsen fra favoritter.
     */

    private boolean addORemoveFromFav() {
        getSharedPref();
        if (isFavorite){
            for (int i = 0; i < favList.size(); i++) {
                if (favList.get(i).getTitle().equals(currExercise.getTitle()) && favList.get(i).getCURRENT_TYPE() == 2){
                    favList.remove(i);
                    isFavorite = false;
                    break;
                }
            }
        }
        else {
            favList.add(new FavoriteDTO(2,currExercise.getImage(),currExercise.getTitle(),currExercise.getDesc()));
            isFavorite = true;
        }
        saveSharedPref();
        return isFavorite;
    }

    private void getSharedPref() {
        String json = sPref.getString(getString(R.string.sPref_favorites),null);
        Type type = new TypeToken<List<FavoriteDTO>>(){}.getType();
        favList = gson.fromJson(json,type);
        if (favList == null){
            favList = new ArrayList<>();
        }
    }

    private void saveSharedPref(){
        String json = gson.toJson(favList);
        sEdit.putString(getString(R.string.sPref_favorites),json);
        sEdit.commit();
    }
}
/*
Lavet af gruppe B4 for ComeBack
Kursus: 62550 62550 Brugerinteraktion og udvikling på mobile enheder
Medlemmer af gruppen:
Simon Andersen (s185083), Asama Hayder(s185099), Jákup Viljam Dam(s185095), Christoffer Adrian Detlef(s185117) & Thaer Almalla(s170727)
*/