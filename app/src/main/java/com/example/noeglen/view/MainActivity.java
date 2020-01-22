package com.example.noeglen.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.noeglen.R;
import com.example.noeglen.logic.CurrentDate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity {

    private Animation in;
    private List<Button> navBarBtnList;
    private List<TextView> navBarTxtList;
    private String fragmentTag, currDateString;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private CurrentDate currDate;
    private View currentButton;
    private Fragment currentFragment;
    ImageView bluenav;
    float iconAnimationValue;
    float textAnimationValue;
    Guideline iconGuideLine;
    Guideline textGuideLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);

        bluenav = findViewById(R.id.blueNavImg);

        iconGuideLine = findViewById(R.id.iconguide);
        textGuideLine = findViewById(R.id.textguide);

        float percent = ((ConstraintLayout.LayoutParams) iconGuideLine.getLayoutParams()).guidePercent;
        float percent2= ((ConstraintLayout.LayoutParams) textGuideLine.getLayoutParams()).guidePercent;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        iconAnimationValue = -((1 - percent) * metrics.ydpi);
        textAnimationValue = -((1 - percent2) * metrics.ydpi);
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
                navButton.animate().translationY(iconAnimationValue).setDuration(200).setInterpolator(new DecelerateInterpolator());
                navButton.setTranslationY(iconAnimationValue);
                navButton.setSelected(true);

                navText.setVisibility(View.VISIBLE);
                navText.animate().translationY(textAnimationValue).setDuration(200).setInterpolator(new DecelerateInterpolator());
                navText.startAnimation(in);
            }
        }

        DashMainF fragment = new DashMainF();
        setFragment(fragment,getString(R.string.fragment_dashmain),false, null);

        fm = this.getSupportFragmentManager();

        currDate = CurrentDate.getInstance();
        currDateString = currDate.createCurrentDate();
        currentButton = navBarBtnList.get(2);
        currentFragment = fragment;
    }

    @Override
    public void onClick(View v) {
        if (v != currentButton){
            Fragment selectedFragment = checkNavBarFragment(v);
            clearBackStack();
            setFragment(selectedFragment,fragmentTag,false,null);
            currentFragment = selectedFragment;
            currentButton = v;
        }else{
            if (!(currentFragment instanceof InfoKnowledgeMainF) && !(currentFragment instanceof DiaryMainF) && !(currentFragment instanceof DashMainF) && !(currentFragment instanceof DashVidMainF) && !(currentFragment instanceof ExerMainF)){
                Fragment selectedFragment = checkNavBarFragment(v);
                clearBackStack();
                setFragment(selectedFragment,fragmentTag,false,null);
                currentFragment = selectedFragment;
            }
        }
    }

    private Fragment checkNavBarFragment(View v) {
        Fragment selectedFragment = null;
        if (v == navBarBtnList.get(0)){
            selectedFragment = new InfoKnowledgeMainF();
            fragmentTag = getString(R.string.fragment_infoknowledgemain);
        }else if (v == navBarBtnList.get(1)){
            selectedFragment = handleDiaryFragmentChange();
        }else if (v == navBarBtnList.get(2)){
            selectedFragment = new DashMainF();
            fragmentTag = getString(R.string.fragment_dashmain);
        }else if (v == navBarBtnList.get(3)){
            selectedFragment = new DashVidMainF();
            fragmentTag = getString(R.string.fragment_dashvidmain);
        }else if (v == navBarBtnList.get(4)){
            selectedFragment = new ExerMainF();
            fragmentTag = getString(R.string.fragment_exermain);
        }
        return selectedFragment;
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
        if (f != currentFragment) {
            View v = null;

            if (tag.equals(getString(R.string.fragment_exermain)) || tag.equals(getString(R.string.fragment_exerexer)) || tag.equals(getString(R.string.fragment_exer_2))){
                v = findViewById(R.id.bNav4);
            }else if (tag.equals(getString(R.string.fragment_dashvid)) || tag.equals(getString(R.string.fragment_dashvidmain))) {
                v = findViewById(R.id.bNav3);
            }else if (tag.equals(getString(R.string.fragment_dashmain))){
                v = findViewById(R.id.bNav2);
            }else if (tag.equals(getString(R.string.fragment_diarymain)) || tag.equals(getString(R.string.fragment_diary1)) || tag.equals(getString(R.string.fragment_diary2)) || tag.equals(getString(R.string.fragment_calendar)) || tag.equals(getString(R.string.fragment_affirmationer))) {
                v = findViewById(R.id.bNav1);
            }else if (tag.equals(getString(R.string.fragment_infoknowledgemain)) || tag.equals(getString(R.string.fragment_infoknowledge))) {
                v = findViewById(R.id.bNav0);
            }

            if (v != currentButton) {
                handleAnimation(v);
                currentButton = v;
            }

            if (bundle != null) {
                f.setArguments(bundle);
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.content_frame, f, tag);

            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            currentFragment = f;
        }
    }

    @Override
    public void inflateFragment(String tag) {
        inflateFragment(tag, true);
    }

    @Override
    public void inflateFragment(String tag, boolean addToBackStack) {
        Fragment selectedFragment = null;
        View v = null;

        // NAVBAR
        if (tag.equals(getString(R.string.fragment_infoknowledgemain))){
            selectedFragment = new InfoKnowledgeMainF();
            v = findViewById(R.id.bNav0);
        }
        if (tag.equals(getString(R.string.fragment_diarymain))){
            selectedFragment = new DiaryMainF();
            v = findViewById(R.id.bNav1);
        }
        if (tag.equals(getString(R.string.fragment_dashmain))){
            selectedFragment = new DashMainF();
            v = findViewById(R.id.bNav2);
        }
        if (tag.equals(getString(R.string.fragment_dashvidmain))){
            selectedFragment = new DashVidMainF();
            v = findViewById(R.id.bNav3);
        }
        if (tag.equals(getString(R.string.fragment_exermain))){
            selectedFragment = new ExerMainF();
            v = findViewById(R.id.bNav4);
        }

        // OTHER FRAGMENTS
        if (tag.equals(getString(R.string.fragment_infoknowledge))){
            selectedFragment = new InfoKnowledgeF();
        }
        if(tag.equals(getString(R.string.fragment_calendar))){
            selectedFragment = new DiaryFCalendar();
        }
        if (tag.equals("Fragment Affirmations")){
            selectedFragment = new DiaryAffirmations();
        }
        if (tag.equals(getString(R.string.fragment_dashvid))){
            selectedFragment = new DashVidF();
            v = findViewById(R.id.bNav3);
        }
        if (tag.equals(getString(R.string.fragment_exerexer))){
            selectedFragment = new ExerExerF();
            v = findViewById(R.id.bNav4);
        }
        if (tag.equals(getString(R.string.fragment_exer_2))){
            selectedFragment = new ExerExerTwoF();
            v = findViewById(R.id.bNav4);
        }

        // CLEAR BACKSTACK / CHANGE NAVBAR ANIMATION
        if (!addToBackStack){
            clearBackStack();
        }

        if (currentButton != v){
            setFragment(selectedFragment,tag,false,null);
        }
        else if (selectedFragment != null){
            setFragment(selectedFragment,tag,addToBackStack, null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fm = this.getSupportFragmentManager();
    }

    //Denne metode undersøger om der allerede er lavet en dagbog for dagen. Hvis der er, så skipper den dairy_main hvor man vælger humør.
    public Fragment handleDiaryFragmentChange(){

        SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedPreferencesKey), MODE_PRIVATE);
        String isTodaysDiaryWritten = preferences.getString(getString(R.string.isTodaysDiaryWritten), "false");

        Fragment selectedFragment;
        if (isTodaysDiaryWritten.equals("true")){
            selectedFragment = new DiaryFCalendar();
            fragmentTag = getString(R.string.fragment_calendar);
        }else{
            selectedFragment = new DiaryMainF();
            fragmentTag = getString(R.string.fragment_diarymain);
        }

        return selectedFragment;
    }

    public void handleAnimation(View v){
        for (int i = 0; i < 5; i++) {
            Button b = navBarBtnList.get(i);
            TextView t = navBarTxtList.get(i);

            b.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            b.setTranslationY(0);
            b.setSelected(false);

            t.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            t.setVisibility(View.INVISIBLE);

            if (v == b){
                b.animate().translationY(iconAnimationValue).setDuration(200).setInterpolator(new DecelerateInterpolator());
                b.setTranslationY(iconAnimationValue);
                b.setSelected(true);
                t.setVisibility(View.VISIBLE);
                t.animate().translationY(textAnimationValue).setDuration(200).setInterpolator(new DecelerateInterpolator());
                t.startAnimation(in);
            }
        }
        currentButton = v;
    }


}