package com.example.noeglen.View.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.noeglen.R;

public class navtest extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;


    TextView articletw;
    TextView diarytw;
    TextView dashboardtw;
    TextView meditationtw;
    TextView chattw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navtest);

        articletw = findViewById(R.id.textView);
        diarytw = findViewById(R.id.textView2);
        dashboardtw = findViewById(R.id.textView3);
        meditationtw = findViewById(R.id.textView4);
        chattw = findViewById(R.id.textView5);

        articletw.setVisibility(View.INVISIBLE);
        diarytw.setVisibility(View.INVISIBLE);
        dashboardtw.setVisibility(View.INVISIBLE);
        meditationtw.setVisibility(View.INVISIBLE);
        chattw.setVisibility(View.INVISIBLE);

        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();

    }


    @Override
    public void onClick(View view) {
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(400);

        Fragment selectedFragment = null;

        if(view.equals(btn)){
            //Button Animations
            btn.animate().translationY(-90).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn2.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn3.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn4.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn5.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            //Button Relative position
            btn.setTranslationY(-90);
            btn2.setTranslationY(0);
            btn3.setTranslationY(0);
            btn4.setTranslationY(0);
            btn5.setTranslationY(0);

            //Texview animation
            articletw.animate().translationY(-30).setDuration(200).setInterpolator(new DecelerateInterpolator());
            diarytw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            dashboardtw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            meditationtw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            chattw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            //TextView fade-in and visibility handling
            articletw.setVisibility(View.VISIBLE);
            articletw.startAnimation(in);
            diarytw.setVisibility(View.INVISIBLE);
            dashboardtw.setVisibility(View.INVISIBLE);
            meditationtw.setVisibility(View.INVISIBLE);
            chattw.setVisibility(View.INVISIBLE);


            //Fragment selector
            selectedFragment = new HomeFragment();

        } else if (view.equals(btn2)){

            btn2.animate().translationY(-90).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn3.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn4.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn5.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            btn2.setTranslationY(-90);
            btn.setTranslationY(0);
            btn3.setTranslationY(0);
            btn4.setTranslationY(0);
            btn5.setTranslationY(0);

            diarytw.animate().translationY(-30).setDuration(200).setInterpolator(new DecelerateInterpolator());
            articletw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            dashboardtw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            meditationtw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            chattw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());


            diarytw.setVisibility(View.VISIBLE);
            diarytw.startAnimation(in);

            articletw.setVisibility(View.INVISIBLE);
            dashboardtw.setVisibility(View.INVISIBLE);
            meditationtw.setVisibility(View.INVISIBLE);
            chattw.setVisibility(View.INVISIBLE);



            selectedFragment = new FavFragment();

        } else if (view.equals(btn3)){

            btn3.animate().translationY(-90).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn2.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn4.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn5.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            btn3.setTranslationY(-90);
            btn.setTranslationY(0);
            btn2.setTranslationY(0);
            btn4.setTranslationY(0);
            btn5.setTranslationY(0);

            dashboardtw.animate().translationY(-30).setDuration(200).setInterpolator(new DecelerateInterpolator());
            articletw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            diarytw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            meditationtw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            chattw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            dashboardtw.setVisibility(View.VISIBLE);
            dashboardtw.startAnimation(in);

            articletw.setVisibility(View.INVISIBLE);
            diarytw.setVisibility(View.INVISIBLE);
            meditationtw.setVisibility(View.INVISIBLE);
            chattw.setVisibility(View.INVISIBLE);

            selectedFragment = new SearchFragment();

        } else if (view.equals(btn4)) {

            btn4.animate().translationY(-90).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn2.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn3.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn5.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            btn4.setTranslationY(-90);
            btn.setTranslationY(0);
            btn2.setTranslationY(0);
            btn3.setTranslationY(0);
            btn5.setTranslationY(0);

            chattw.animate().translationY(-30).setDuration(200).setInterpolator(new DecelerateInterpolator());
            articletw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            diarytw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            meditationtw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            dashboardtw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            chattw.setVisibility(View.VISIBLE);
            chattw.startAnimation(in);

            articletw.setVisibility(View.INVISIBLE);
            diarytw.setVisibility(View.INVISIBLE);
            dashboardtw.setVisibility(View.INVISIBLE);
            meditationtw.setVisibility(View.INVISIBLE);

            selectedFragment = new HomeFragment();

        } else if (view.equals(btn5)) {

            btn5.animate().translationY(-90).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn2.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn3.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn4.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            btn5.setTranslationY(-90);
            btn.setTranslationY(0);
            btn2.setTranslationY(0);
            btn3.setTranslationY(0);
            btn4.setTranslationY(0);

            meditationtw.animate().translationY(-30).setDuration(200).setInterpolator(new DecelerateInterpolator());
            articletw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            diarytw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            chattw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            dashboardtw.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            meditationtw.setVisibility(View.VISIBLE);
            meditationtw.startAnimation(in);

            articletw.setVisibility(View.INVISIBLE);
            diarytw.setVisibility(View.INVISIBLE);
            dashboardtw.setVisibility(View.INVISIBLE);
            chattw.setVisibility(View.INVISIBLE);

            selectedFragment = new FavFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, selectedFragment).commit();
    }
}