package com.example.noeglen.view;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;



public class DiaryFCalendar extends Fragment implements View.OnClickListener {

    private static final String tag ="DiaryFCalendar";
    private CalendarView calendar;
    Long date;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendar = getView().findViewById(R.id.calendarView);
        date = calendar.getDate();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
               String date = day + "/" + (month + 1) + "/" +year ;
                Log.d(tag, "onSelectedDayChange: day/month/year:"+ date);

             //   String nyDate = calendar.setDate(date);

                //System.out.println(calendar.getDate());






            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}
