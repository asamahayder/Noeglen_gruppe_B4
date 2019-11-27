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

public class InfoArtiklerMainF extends Fragment implements View.OnClickListener {
    private IMainActivity iMain;
    private ImageView artikel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infoartiklermain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        artikel = getView().findViewById(R.id.imageView6);
        artikel.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }


    @Override
    public void onClick(View view) {
        String tag = "";
        if (view == artikel){
            tag = getString(R.string.fragment_artikel);
            iMain.inflateFragment(tag);
        }
    }
}
