package com.example.noeglen.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.noeglen.R;
import com.example.noeglen.logic.CurrentDate;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity {

    private Animation in;
    private List<Button> navBarBtnList;
    private List<TextView> navBarTxtList;
    private String fragmentTag, currDateString;
    private FragmentManager fm;
    private CurrentDate currDate;
    private View currentView;

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
        setFragment(fragment,getString(R.string.fragment_dashmain),false, null);

        fm = this.getSupportFragmentManager();

        currDate = CurrentDate.getInstance();
        currDateString = currDate.createCurrentDate();
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        currentView = v;

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
                        selectedFragment = new InfoKnowledgeMainF();
                        fragmentTag = getString(R.string.fragment_infoknowledgemain);
                        break;
                    case 1:
                        selectedFragment = new DiaryMainF();
                        fragmentTag = getString(R.string.fragment_diarymain);
                        break;
                    case 2:
                        selectedFragment = new DashMainF();
                        fragmentTag = getString(R.string.fragment_dashmain);
                        break;
                    case 3:
                        selectedFragment = new DashVidMainF();
                        fragmentTag = getString(R.string.fragment_dashvidmain);
                        break;
                    case 4:
                        selectedFragment = new ExerMainF();
                        fragmentTag = getString(R.string.fragment_exermain);
                        break;
                }
            }
        }
        clearBackStack();
        setFragment(selectedFragment,fragmentTag,false,null);
    }

    private void clearBackStack() {
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    private void clearOneBackStack() {
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            if (i + 1 > fm.getBackStackEntryCount()){
                fm.popBackStack();
            }
        }
    }

    @Override
    public void setFragment(Fragment f, String tag, boolean addToBackStack, Bundle bundle){

        if (bundle != null){
            f.setArguments(bundle);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_frame,f,tag);

        if (addToBackStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }


    @Override
    public void inflateFragment(String tag) {
        inflateFragment(tag, true);
    }

    @Override
    public void inflateFragment(String tag, boolean addToBackStack) {

        Fragment selectedFragment = null;
        View v = currentView;

        if (tag.equals(getString(R.string.fragment_dashvidmain))){
            selectedFragment = new DashVidMainF();
        }
        if (tag.equals(getString(R.string.fragment_diarymain))){
            selectedFragment = new DiaryMainF();
            v = findViewById(R.id.bNav1);
        }
        if (tag.equals(getString(R.string.fragment_exermain))){
            selectedFragment = new ExerMainF();
            v = findViewById(R.id.bNav4);
        }
        if (currentView != v){
            onClick(v);
            setFragment(selectedFragment,tag,false,null);
        }
        else if (selectedFragment != null){
            setFragment(selectedFragment,tag,true,null);
        }
        if (tag.equals(getString(R.string.fragment_infoknowledge))){
            selectedFragment = new InfoKnowledgeF();
        }
        if (tag.equals(getString(R.string.fragment_infoknowledge))){
            selectedFragment = new InfoKnowledgeF();
        }
        if (tag.equals(getString(R.string.fragment_exerexer))){
            selectedFragment = new ExerExerF();
        }
        if (tag.equals(getString(R.string.fragment_dashvid))){
            selectedFragment = new DashVidF();
        }
        if(tag.equals(getString(R.string.fragment_calendar))){
            selectedFragment = new DiaryFCalendar();
        }
        if (tag.equals(getString(R.string.fragment_infoknowledgemain))){
            selectedFragment = new InfoKnowledgeMainF();
            if (!addToBackStack){
                clearBackStack();
            }
        }
        if (selectedFragment != null){
            setFragment(selectedFragment,tag,addToBackStack, null);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fm = this.getSupportFragmentManager();
    }
}