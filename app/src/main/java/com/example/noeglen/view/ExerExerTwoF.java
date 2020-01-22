package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "ExerExerF";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exer_2, container, false);
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

    @Override
    public void onDetach() {
        super.onDetach();
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

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
        if (isFavorite){
            Log.d(TAG, "checkIfCurrExerIsFav: RED");
        }
        else {
            Log.d(TAG, "checkIfCurrExerIsFav: BLUE");
        }
        return isFavorite;
    }

    private boolean addORemoveFromFav() {
        getSharedPref();
        if (isFavorite){
            for (int i = 0; i < favList.size(); i++) {
                if (favList.get(i).getTitle().equals(currExercise.getTitle()) && favList.get(i).getCURRENT_TYPE() == 2){
                    favList.remove(i);
                    isFavorite = false;
                    Log.d(TAG, "addORemoveFromFav: BLUE");
                    break;
                }
            }
        }
        else {
            favList.add(new FavoriteDTO(2,currExercise.getImage(),currExercise.getTitle(),currExercise.getDesc()));
            isFavorite = true;
            Log.d(TAG, "addORemoveFromFav: RED");
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
        Log.d(TAG, "getSharedPref: favListSize = " + favList.size());
    }

    private void saveSharedPref(){
        String json = gson.toJson(favList);
        sEdit.putString(getString(R.string.sPref_favorites),json);
        sEdit.commit();
        Log.d(TAG, "saveSharedPref: favListSize = " + favList.size());
    }
}
