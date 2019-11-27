package com.example.noeglen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.noeglen.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity {

    private Animation in;
    private List<Button> navBarBtnList;
    private List<TextView> navBarTxtList;
    private String fragmentTag;
    private FragmentManager fm;

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
            navText.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            if (bID.equals("bNav2")){
                navButton.animate().translationY(-77).setDuration(200).setInterpolator(new DecelerateInterpolator());
                navButton.setTranslationY(-77);
                navButton.setSelected(true);

                navText.setVisibility(View.VISIBLE);
                navText.animate().translationY(-40).setDuration(200).setInterpolator(new DecelerateInterpolator());
                navText.startAnimation(in);
            }
        }

        DashMainF fragment = new DashMainF();
        setFragment(fragment,getString(R.string.fragment_dash),false);
        fm = this.getSupportFragmentManager();
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
                t.animate().translationY(-40).setDuration(200).setInterpolator(new DecelerateInterpolator());
                t.startAnimation(in);

                switch (i){
                    case 0:
                        selectedFragment = new InfoMainF();
                        fragmentTag = getString(R.string.fragment_info);
                        break;
                    case 1:
                        selectedFragment = new DiaryMainF();
                        fragmentTag = getString(R.string.fragment_diary);
                        break;
                    case 2:
                        selectedFragment = new DashMainF();
                        fragmentTag = getString(R.string.fragment_dash);
                        break;
                    case 3:
                        selectedFragment = new FavMainF();
                        fragmentTag = getString(R.string.fragment_fav);
                        break;
                    case 4:
                        selectedFragment = new ExerMainF();
                        fragmentTag = getString(R.string.fragment_exer);
                        break;
                }
            }
        }

        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }

        setFragment(selectedFragment,fragmentTag,false);
    }

    private void setFragment(Fragment f, String tag, boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_frame,f,tag);

        if (addToBackStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void inflateFragment(String tag) {
        if (tag.equals(getString(R.string.fragment_vid))){
            Fragment selectedFragment = new DashVidF();
            setFragment(selectedFragment,tag,true);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fm = this.getSupportFragmentManager();
    }
}