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

/**
 * MainActivity er hoved aktiviteten der indeholder navBaren og alle fragment skiftelser
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMainActivity {

    /**
     * @variable in
     * in er animation objektet der bliver brugt til lave animationen hos navbaren
     *
     * @variable navBarBtnList
     * dette er listen af navbar knapper som kan trykkes på hvor i der kan ske at der skiftes
     * fragment og/eller animationen bliver ændret på navbaren
     *
     * @variable navBarTxtList
     * teksten ved siden af navbar knappen hvor bliver brugt til at adde nogle animationer
     * på og ændres når nogle specifikke ting sker
     *
     * @variable fragmentTag
     * Taget hos fragmentet der bliver brugt til at holde styr på hviklet fragment der skal skiftes
     *
     * @variable fm
     * fragmentManageren skifter fragmenter og adder fragmenterne til backstack om det skal være
     *
     * @variable currentButton
     * den nuværende navbar knap som er trykket på. Bliver brugt til at ikke at love brugeren at trykke på
     * den samme knap flere gange når brugeren allerede bruger knappen
     *
     * @variable currentFragment
     * det nuværende fragment man er på. Bliver brugt til at holde styr på at man ikke kan f.eks.
     * skifte fragment til det samme fragment flere gange
     *
     * @variable iconAnimationValue
     * En værdi der sige hvor langt animationen skal flytte sig, alt efter hvor stor skærmen er
     *
     * @variable textAnimationValue
     * En værdi der sige hvor langt animationen skal flytte sig, alt efter hvor stor skærmen er
     */

    private Animation in;
    private List<Button> navBarBtnList;
    private List<TextView> navBarTxtList;
    private String fragmentTag;
    private FragmentManager fm;
    private View currentButton;
    private Fragment currentFragment;
    private float iconAnimationValue;
    private float textAnimationValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);

        //definerer hvor hjælpe linjerne er
        Guideline iconGuideLine = findViewById(R.id.iconguide);
        Guideline textGuideLine = findViewById(R.id.textguide);

        //beregner prosentene fra hjælpe linjerne
        float percent = ((ConstraintLayout.LayoutParams) iconGuideLine.getLayoutParams()).guidePercent;
        float percent2= ((ConstraintLayout.LayoutParams) textGuideLine.getLayoutParams()).guidePercent;

        // finder ud af hvor langt animation skal flytte sig alt efter hvor stor procentene er på den nuværende skærm
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        iconAnimationValue = -((1 - percent) * metrics.ydpi);
        textAnimationValue = -((1 - percent2) * metrics.ydpi);
        //initialiserer viewet
        initializeView();

    }

    /**
     * Initialiserer viewet
     */

    private void initializeView() {

        //laver animationen
        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(400);

        //definerer listerne med knapper og tetviews
        navBarBtnList = new ArrayList<>();
        navBarTxtList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            //finder knapperne og textviews i resources
            String bID = "bNav" + i;
            String tID = "tNav" + i;
            int resbID = getResources().getIdentifier(bID,"id",getPackageName());
            int restID = getResources().getIdentifier(tID,"id",getPackageName());
            Button navButton = findViewById(resbID);
            TextView navText = findViewById(restID);

            //adder knappen og teksten til listerne
            navBarBtnList.add(navButton);
            navBarTxtList.add(navText);

            //giver knapperne en onClickListener
            navButton.setStateListAnimator(null);
            navButton.setOnClickListener(this);
            //Sætter alle knapper som ikke er trykket på usynlige og animerer dem til at gå ned
            navText.setVisibility(View.INVISIBLE);
            navText.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            //Hvis den nuværende knap er på dashboardet, sp bliver den animeret op
            if (bID.equals("bNav2")){
                //animerer knappen
                navButton.animate().translationY(iconAnimationValue).setDuration(200).setInterpolator(new DecelerateInterpolator());
                navButton.setTranslationY(iconAnimationValue);
                navButton.setSelected(true);

                //gør teksten synlig
                navText.setVisibility(View.VISIBLE);
                navText.animate().translationY(textAnimationValue).setDuration(200).setInterpolator(new DecelerateInterpolator());
                navText.startAnimation(in);
            }
        }

        //laver et nyt dashboard fragment, som er det første fragment der bliver lavet og inflate'er det
        DashMainF fragment = new DashMainF();
        setFragment(fragment,getString(R.string.fragment_dashmain),false, null);

        //initaliserer fragmentManageren
        fm = this.getSupportFragmentManager();

        //adder den nuværende knap til at være dashboard knappen
        currentButton = navBarBtnList.get(2);
        currentFragment = fragment;
    }

    @Override
    public void onClick(View v) {
        //Hvis viewet ikke er den nuværende knap, finder vi ud af hvilket fragment det er
        if (v != currentButton){
            //Tjekker hvilket fragment det er
            Fragment selectedFragment = checkNavBarFragment(v);
            //sletter alt i backstack
            clearBackStack();
            //Åbner fragmentet
            setFragment(selectedFragment,fragmentTag,false,null);
            //adder det selectedFragment til det nuværende fragment, samt den nuværende knap
            currentFragment = selectedFragment;
            currentButton = v;
        }
        //hvis det er den samme knap som nuværende
        else{
            //tjekker om det nuværende fragment er det samme som det som fragmenterne på navbaren
            if (!(currentFragment instanceof InfoKnowledgeMainF) && !(currentFragment instanceof DiaryMainF) && !(currentFragment instanceof DashMainF) && !(currentFragment instanceof DashVidMainF) && !(currentFragment instanceof ExerMainF)){
                //Hvis det ikke var en af dem, så tjekker vi hvilket fragment det er og sletter backstacken igen
                Fragment selectedFragment = checkNavBarFragment(v);
                clearBackStack();
                //åbner det nye fragment og obdaterer fragmentet
                setFragment(selectedFragment,fragmentTag,false,null);
                currentFragment = selectedFragment;
            }
        }
    }

    /**
     * Tjekker om den knap der var trykket er det samme som en af de knapper på navbaren
     *
     * @param v
     * dette er knappen der blev trykket på som skal tjekkes
     *
     * @return
     * returnerer det nuværende fragment
     */

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

    /**
     * Sletter alt fra backstacken, såsom man kan ikke bruge tilbage knappen til at gå
     * tilbage til et sted der ikke var meningen man skulle
     */

    private void clearBackStack() {
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    /**
     * Metoden der skifter imellem fragmenter og adder til backstack alt efter hvad der står i parametrene
     *
     * @param f
     * dette er fragmentet som er det nye fragment der skal laves
     * @param tag
     * taget hos fragmentet som der skal ændres til
     * @param addToBackStack
     * variablen der definerer om fragmentet skal addes til backstacken
     * @param bundle
     * om fragmentet indeholder et bundle med data, bliver dette brugt
     */

    @Override
    public void setFragment(Fragment f, String tag, boolean addToBackStack, Bundle bundle){
        if (f != currentFragment) {
            View v = null;

            //tjekker om det nuværende fragment er en af dem på navbaren, om det er så bliver viewet fundet
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

            //Hvis viewet er lig med currentbutton, skal der tjekkes om der skal ske en animation
            if (v != currentButton) {
                handleAnimation(v);
                //søtter den nuværende knap til at være lig med viewet
                currentButton = v;
            }

            //hvis der findes et bundle, bliver det addet til fragmentet
            if (bundle != null) {
                f.setArguments(bundle);
            }

            //opretter det nye fragment og animerer det
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.content_frame, f, tag);

            //adder til backstack om variablen er true
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            //sætter nuværende fragment til at være lig med f
            currentFragment = f;
        }
    }

    /**
     * inflate fragment metoden som sender bare den videre til den anden inflatefragment metode
     * @param tag
     * fragment taget der bliver brugt til at finde ud af hvilket fragment der skal laves
     */

    @Override
    public void inflateFragment(String tag) {
        inflateFragment(tag, true);
    }

    /**
     * inflate fragment metoden der finder ud af hvad for et fragment der skal laves og sender det videre til setfragment
     *
     * @param tag
     * taget der siger hvilket fragment skal bruges til at definere hvilket fragment skal laves
     *
     *@param addToBackStack
     * en boolean som siger om det skal ades til backstack eller ikke
     */

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

        // ANDRE FRAGMENTS
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

    /**
     * Denne metode undersøger om der allerede er lavet en dagbog for dagen. Hvis der er, så skipper den dairy_main hvor man vælger humør.
     *
     * @return
     * returnerer hvilket fragment der skal bruges om dagbogen er lavet eller ikke
     */

    public Fragment handleDiaryFragmentChange(){

        //Henter gemt data
        SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedPreferencesKey), MODE_PRIVATE);
        String isTodaysDiaryWritten = preferences.getString(getString(R.string.isTodaysDiaryWritten), "false");

        // tjekker om man har lavet dagbogen for i dag
        Fragment selectedFragment;
        if (isTodaysDiaryWritten.equals("true")){
            //Om man har lavet den, bliver calender fragmentet brugt
            selectedFragment = new DiaryFCalendar();
            fragmentTag = getString(R.string.fragment_calendar);
        }else{
            //om man ikke har, bliver man sendt ind i dagbogen
            selectedFragment = new DiaryMainF();
            fragmentTag = getString(R.string.fragment_diarymain);
        }

        return selectedFragment;
    }

    /**
     * Metoden der håndterer animationen for navbaren
     *
     * @param v
     * hvilken knap på navbaren der blev trykket/er brugt lige nu
     */

    public void handleAnimation(View v){
        for (int i = 0; i < 5; i++) {
            //Går igennem alle knapper og tekstviews
            Button b = navBarBtnList.get(i);
            TextView t = navBarTxtList.get(i);

            //animerer alle knapper og tekste ned / gør teksten usynlig
            b.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            b.setTranslationY(0);
            b.setSelected(false);

            t.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            t.setVisibility(View.INVISIBLE);

            //hvis viewet er lig med en af knapperne i listen, bliver den animeret op og de andre ned
            if (v == b){
                b.animate().translationY(iconAnimationValue).setDuration(200).setInterpolator(new DecelerateInterpolator());
                b.setTranslationY(iconAnimationValue);
                b.setSelected(true);
                t.setVisibility(View.VISIBLE);
                t.animate().translationY(textAnimationValue).setDuration(200).setInterpolator(new DecelerateInterpolator());
                t.startAnimation(in);
            }
        }
        //Sætter nuværende knap til at være lig med viewet
        currentButton = v;
    }
}