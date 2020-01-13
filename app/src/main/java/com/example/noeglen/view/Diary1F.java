package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.data.DiaryDTO;
import com.example.noeglen.logic.CurrentDate;

public class Diary1F extends Fragment  implements View.OnClickListener{

    private Button a;
    private IMainActivity iMain;
    private TextView text;
    private CurrentDate currentDate;
    DiaryDTO diaryDTO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diary1, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentDate = CurrentDate.getInstance();
        text = getView().findViewById(R.id.textView3);
        text.setText(currentDate.getDateString());

        a = getView().findViewById(R.id.gem);
        a.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        diaryDTO = new DiaryDTO()


        String tag ="";

        if (view == a){
                tag = getString(R.string.fragment_calendar);
                iMain.inflateFragment(tag);

            }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }
}