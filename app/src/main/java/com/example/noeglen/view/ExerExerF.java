package com.example.noeglen.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;

public class ExerExerF extends Fragment implements View.OnClickListener {

    private IMainActivity iMain;
    private ImageView iAnim;
    private Button bAnim;
    Animation connectingAnimation;

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
    public void initializeView(){
        iAnim = getView().findViewById(R.id.iExerAnim);
        bAnim = getView().findViewById(R.id.startAnimbtn);
        bAnim.setOnClickListener(this);
        connectingAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {
        iAnim.startAnimation(connectingAnimation);
    }
}
