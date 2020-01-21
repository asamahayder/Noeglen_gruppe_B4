package com.example.noeglen.view;


import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.noeglen.data.DiaryDTO;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DiaryFCalendar extends Fragment  {

    private static final String tag ="DiaryFCalendar";
    private CalendarView calendar;
    private Long dato;
    private String date;
    private SharedPreferences sPref;
    private SharedPreferences.Editor sEdit;
    private List<DiaryDTO> listOfEntries;
    private Gson gson;
    private IMainActivity iMain;
    private Bundle bundle;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();

        calendar = getView().findViewById(R.id.calendarView);
        dato = calendar.getDate();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String years = Integer.toString(year);
                years.substring(0,1);
                year = Integer.parseInt(years);
                String date = day + "/" + (month + 1) + "/" + year;
                Log.d(tag, "onSelectedDayChange: day/month/year:"+ date);

                Bundle bundle = new Bundle();
                bundle.putString("date",date);
                Diary2F diary2F = new Diary2F();

                Boolean diaryExists = false;
                for (int i = 0; i < listOfEntries.size(); i++) {
                    if (listOfEntries.get(i).getDate().equals(date)){
                        diaryExists = true;
                    }
                }
                if (diaryExists){
                    iMain.setFragment(diary2F,getString(R.string.fragment_diary2),true,bundle);
                }
            }

        });





    }
    private void initializeView(){
        gson = new Gson();
        String sPrefKey = getString(R.string.sharedPreferencesKey);
        sPref = getContext().getSharedPreferences(sPrefKey, Context.MODE_PRIVATE);
        sEdit = sPref.edit();
        bundle = getArguments();


        getListOfEntries("Diary");

    }


    private void getListOfEntries(String sPrefEditKey) {
        String json = sPref.getString(sPrefEditKey, null);
        System.out.println(json + "##########");
        Type type = new TypeToken<List<DiaryDTO>>() {
        }.getType();
        listOfEntries = gson.fromJson(json, type);
        if (listOfEntries == null) {
            listOfEntries = new ArrayList<>();
        }

    }

    private void saveSharedPref(String sPrefEditKey) {
        String json = gson.toJson(listOfEntries);
        sEdit.putString(sPrefEditKey,json);
        sEdit.commit();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }


}
