package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;

import java.util.Random;


public class DiaryAffirmations extends Fragment {

    private IMainActivity iMain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_affirmations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
        *  hver enste gang den bliver kaldt, bliver så der valgt en random sætning ud ad de sætninger der er inde i Arrayet
        */

        String[] arr = new String[]{"Det bliver en fantastisk dag", "Hvor er jeg heldig – jeg trækker vejret, lever og elsker", "Alt er muligt, jeg ved det og jeg kan mærke det",
        "Jeg er elsket", "Der er så meget at være taknemmelig over", "Jeg mærker en styrke og en kraft vokse indeni mig", "Der er så meget at glæde sig over",
        "Jeg kan mærke energien brede sig i min krop"};

        Random random = new Random();
        int randomAffir = random.nextInt(arr.length);

        TextView affirmation = getView().findViewById(R.id.affirmation);
        affirmation.setText(arr[randomAffir]);

        Handler mainLooperHandler = new Handler(Looper.getMainLooper());

        mainLooperHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getActivity()!=null){
                    iMain.inflateFragment(getString(R.string.fragment_dashmain),false);

                }
            }
        }, 2000);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }
}
