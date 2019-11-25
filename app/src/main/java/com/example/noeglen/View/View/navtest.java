package com.example.noeglen.View.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.example.noeglen.R;

public class navtest extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navtest);

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
        Fragment selectedFragment = null;

        if(view.equals(btn)){
            //Animations
            btn.animate().translationY(-120).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn2.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn3.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn4.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn5.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            //Relative position
            btn.setTranslationY(-120);
            btn2.setTranslationY(0);
            btn3.setTranslationY(0);
            btn4.setTranslationY(0);
            btn5.setTranslationY(0);
            btn.animate().setInterpolator(new DecelerateInterpolator());
            //Fragment selector
            selectedFragment = new HomeFragment();

        } else if (view.equals(btn2)){

            btn2.animate().translationY(-120).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn3.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn4.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn5.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            btn2.setTranslationY(-120);
            btn.setTranslationY(0);
            btn3.setTranslationY(0);
            btn4.setTranslationY(0);
            btn5.setTranslationY(0);

            selectedFragment = new FavFragment();

        } else if (view.equals(btn3)){
            btn3.animate().translationY(-120).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn2.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn4.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn5.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            btn3.setTranslationY(-120);
            btn.setTranslationY(0);
            btn2.setTranslationY(0);
            btn4.setTranslationY(0);
            btn5.setTranslationY(0);
            selectedFragment = new SearchFragment();

        } else if (view.equals(btn4)) {
            btn4.animate().translationY(-120).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn2.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn3.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn5.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            btn4.setTranslationY(-120);
            btn.setTranslationY(0);
            btn2.setTranslationY(0);
            btn3.setTranslationY(0);
            btn5.setTranslationY(0);
            selectedFragment = new HomeFragment();

        } else if (view.equals(btn5)) {

            btn5.animate().translationY(-120).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn2.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn3.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn4.animate().translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator());

            btn5.setTranslationY(-120);
            btn.setTranslationY(0);
            btn2.setTranslationY(0);
            btn3.setTranslationY(0);
            btn4.setTranslationY(0);
            selectedFragment = new FavFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, selectedFragment).commit();
    }
}