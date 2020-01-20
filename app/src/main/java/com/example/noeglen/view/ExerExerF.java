package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.data.ExerciseDTO;
import com.example.noeglen.data.FavoriteDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExerExerF extends Fragment implements View.OnClickListener {

    private ImageView iAnim;
    private TextView tTitle, tDesc;
    private Animation breatheAnimation;
    private Button bstartAnim;
    private IMainActivity iMain;
    private CardView bAddToFav;
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
        return inflater.inflate(R.layout.fragment_exerexer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    private void initializeView(){
        iAnim = Objects.requireNonNull(getView()).findViewById(R.id.iExerAnim);
        bstartAnim = getView().findViewById(R.id.bstartAnim);
        bAddToFav = getView().findViewById(R.id.bAddToFav);
        bstartAnim.setOnClickListener(this);
        bAddToFav.setOnClickListener(this);
        breatheAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale);

        tTitle = getView().findViewById(R.id.tExcerTitle);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {
        if(v == bAddToFav) {
            if (addORemoveFromFav()){
                bAddToFav.setBackground(getContext().getDrawable(resID2));
                System.out.println("######################################");
            }
            else {
                bAddToFav.setBackground(getContext().getDrawable(resID1));
            }
        } else if( v == bstartAnim){
            iAnim.startAnimation(breatheAnimation);
        }

    }
}
