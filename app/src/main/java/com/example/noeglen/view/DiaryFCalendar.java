package com.example.noeglen.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.noeglen.R;
import com.example.noeglen.data.DiaryDTO;
import com.example.noeglen.data.MyCallBack;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DiaryFCalendar extends Fragment  {

    private static final String tag ="DiaryFCalendar";
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

        CalendarView calendar = getView().findViewById(R.id.calendarView);
        bundle = new Bundle();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String years = Integer.toString(year);
                years.substring(0,1);
                year = Integer.parseInt(years);
                date = day + "/" + (month + 1) + "/" + year;
                Log.d(tag, "onSelectedDayChange: day/month/year:"+ date);
                bundle.putString("date",date);
                final Diary2F diary2F = new Diary2F();

                /**
                 * Tjekker om der er skrevet noget inde in Diary, og hvis det er så bliver den åbnet og hvis ik så bliver CreateDiaryOnEmptyDayDialog kaldt
                 */

                Boolean diaryExists = false;
                for (int i = 0; i < listOfEntries.size(); i++) {

                    if (listOfEntries.get(i).getDate().equals(date)){
                        diaryExists = true;
                    }
                }

                if (diaryExists){
                    iMain.setFragment(diary2F,getString(R.string.fragment_diary2),true,bundle);
                }else{
                    CreateDiaryOnEmptyDayDialog dialog = new CreateDiaryOnEmptyDayDialog(new MyCallBack() {
                        @Override
                        public void onCallBack(Object object) {
                            boolean a = (boolean)object;
                            if (a){
                                iMain.setFragment(new DiaryMainF(),getString(R.string.fragment_diarymain),true,bundle);
                            }
                        }
                    });
                    dialog.show(getFragmentManager(),"dialog");
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

        Type type = new TypeToken<List<DiaryDTO>>() {
        }.getType();
        listOfEntries = gson.fromJson(json, type);
        if (listOfEntries == null) {
            listOfEntries = new ArrayList<>();
        }

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    /**
     * Hvis der ikke er skrevet noget inde i dagbogen for den kaldte dato, kommer der så en besked der spørger om brugern gerne vil tilføje noget til dagbogen
     */


    public static class CreateDiaryOnEmptyDayDialog extends DialogFragment {

        MyCallBack myCallBack;

        public CreateDiaryOnEmptyDayDialog(MyCallBack callBack) {
            myCallBack = callBack;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.emptyDayDiaryDialog).setPositiveButton(R.string.emptyDayDiaryPositiveOption, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    myCallBack.onCallBack(true);
                }
            }).setNegativeButton(R.string.emptyDayDiaryNegativeOption, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    myCallBack.onCallBack(false);
                }
            });

            return builder.create();
        }
    }


}
