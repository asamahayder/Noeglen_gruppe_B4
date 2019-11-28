package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.noeglen.R;
import com.example.noeglen.logic.CurrentDate;

public class DiaryMainF extends Fragment {

    TextView textView;
    CurrentDate currentDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diarymain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textView = getView().findViewById(R.id.textView4);
        textView.setText(currentDate.getDateString());


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        currentDate = CurrentDate.getInstance();
    }
}
