package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;

import java.util.Objects;

public class ExerExerF extends Fragment implements View.OnClickListener {

    private ImageView iAnim;
    private Animation breatheAnimation;
    private Button bFav, bAnim;
    private IMainActivity iMain;


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
        bAnim = getView().findViewById(R.id.bstartAnim);
        bFav = getView().findViewById(R.id.bAddToFav);
        bAnim.setOnClickListener(this);
        bFav.setOnClickListener(this);
        breatheAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {
        if(v == bFav) {
            //TODO: Adding functionality to add the exercise to favourites but beaware of animations and stuff
            iAnim.startAnimation(breatheAnimation);
        } else if( v == bAnim){
            iAnim.startAnimation(breatheAnimation);
        }

    }
}
