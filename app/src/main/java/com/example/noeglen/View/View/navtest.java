package com.example.noeglen.View.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.example.noeglen.R;

public class navtest extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    Button btn2;
    Button btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navtest);

        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();

    }


    @Override
    public void onClick(View view) {
        Fragment selectedFragment = null;

        if(view.equals(btn)){
            btn.animate().setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn.setTranslationY(-120);
            btn2.setTranslationY(0);
            btn3.setTranslationY(0);
            selectedFragment = new HomeFragment();
        } else if (view.equals(btn2)){
            btn2.animate().setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn2.setTranslationY(-120);
            btn.setTranslationY(0);
            btn3.setTranslationY(0);
            selectedFragment = new FavFragment();
        } else if (view.equals(btn3)){
            btn3.animate().setDuration(200).setInterpolator(new DecelerateInterpolator());
            btn3.setTranslationY(-120);
            btn.setTranslationY(0);
            btn2.setTranslationY(0);
            selectedFragment = new SearchFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, selectedFragment).commit();
    }
}