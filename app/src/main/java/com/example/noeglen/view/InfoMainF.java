package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;

public class InfoMainF extends Fragment implements View.OnClickListener {

    private IMainActivity iMain;
    private ImageView iKnowledge, iArticles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infomain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iKnowledge = getView().findViewById(R.id.imageView2);
        iKnowledge.setOnClickListener(this);
        iArticles = getView().findViewById(R.id.imageView5);
        iArticles.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        String tag = "";

        if (view == iArticles) {
            tag = getString(R.string.fragment_infoarticlesmain);

        } else if (view == iKnowledge){
            tag = getString(R.string.fragment_infoknowledge);
        }
        iMain.inflateFragment(tag);

    }
}