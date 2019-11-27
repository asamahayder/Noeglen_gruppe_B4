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

<<<<<<<< HEAD:app/src/main/java/com/example/noeglen/view/InfoMainF.java
public class InfoMainF extends Fragment implements View.OnClickListener {

    private IMainActivity iMain;
    private ImageView nyttigViden, artikler;

========
public class InfoVidenF extends Fragment {
>>>>>>>> origin/Dev:app/src/main/java/com/example/noeglen/view/InfoVidenF.java
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infoviden, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nyttigViden = getView().findViewById(R.id.imageView2);
        nyttigViden.setOnClickListener(this);
        artikler = getView().findViewById(R.id.imageView5);
        artikler.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
          String tag = "";

        if (view == artikler) {
           tag = getString(R.string.fragment_artikler);

        } else if (view == nyttigViden){
            tag = getString(R.string.fragment_nyttigViden);
        }
           iMain.inflateFragment(tag);

    }
}
