package com.example.noeglen.View.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.noeglen.R;
import com.example.noeglen.View.View.fragments.dash.DashMain;
import com.example.noeglen.View.View.fragments.diary.DiaryMain;
import com.example.noeglen.View.View.fragments.exer.ExerMain;
import com.example.noeglen.View.View.fragments.fav.FavMain;
import com.example.noeglen.View.View.fragments.info.InfoMain;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity {

    private Animation in;
    private List<Button> navBarBtnList;
    private List<TextView> navBarTxtList;
    private String fragmentTag;
    private boolean addToBackStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
        initializeView();
    }

    private void initializeView() {
        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(400);

        navBarBtnList = new ArrayList<>();
        navBarTxtList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String bID = "bNav" + i;
            String tID = "tNav" + i;
            int resbID = getResources().getIdentifier(bID,"id",getPackageName());
            int restID = getResources().getIdentifier(tID,"id",getPackageName());

            Button navButton = findViewById(resbID);
            TextView navText = findViewById(restID);
            navBarBtnList.add(navButton);
            navBarTxtList.add(navText);


            navButton.setStateListAnimator(null);
            navButton.setOnClickListener(this);
            navText.setVisibility(View.INVISIBLE);

            if (bID.equals("bNav2")){
                navButton.animate().translationY(-77).setDuration(200).setInterpolator(new DecelerateInterpolator());
                navButton.setTranslationY(-77);
                navButton.setSelected(true);

                navText.setVisibility(View.VISIBLE);
                navText.startAnimation(in);
            }
        }

        DashMain fragment = new DashMain();
        setFragment(fragment,getString(R.string.fragment_dash),false);
    }


    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;

        for (int i = 0; i < 5; i++) {
            Button b = navBarBtnList.get(i);
            TextView t = navBarTxtList.get(i);

            b.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            b.setTranslationY(0);
            b.setSelected(false);

            t.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            t.setVisibility(View.INVISIBLE);

            if (v == b){
                b.animate().translationY(-77).setDuration(200).setInterpolator(new DecelerateInterpolator());
                b.setTranslationY(-77);
                b.setSelected(true);

                t.setVisibility(View.VISIBLE);
                t.startAnimation(in);

                switch (i){
                    case 0:
                        selectedFragment = new InfoMain();
                        fragmentTag = getString(R.string.fragment_info);
                        addToBackStack = false;
                        break;
                    case 1:
                        selectedFragment = new DiaryMain();
                        fragmentTag = getString(R.string.fragment_diary);
                        addToBackStack = false;
                        break;
                    case 2:
                        selectedFragment = new DashMain();
                        fragmentTag = getString(R.string.fragment_dash);
                        addToBackStack = false;
                        break;
                    case 3:
                        selectedFragment = new FavMain();
                        fragmentTag = getString(R.string.fragment_fav);
                        addToBackStack = false;
                        break;
                    case 4:
                        selectedFragment = new ExerMain();
                        fragmentTag = getString(R.string.fragment_exer);
                        addToBackStack = false;
                        break;
                }
            }
        }
        setFragment(selectedFragment,fragmentTag,addToBackStack);
    }

    @Override
    public void setFragment(Fragment f, String tag, boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_frame,f,tag);

        if (addToBackStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }
}