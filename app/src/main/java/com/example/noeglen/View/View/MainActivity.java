package com.example.noeglen.View.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.noeglen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navList);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, new DashFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navList = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_dash:
                    selectedFragment = new DashFragment();
                    break;
                case R.id.nav_fav:
                    selectedFragment = new FavFragment();
                    break;
                case R.id.nav_diary:
                    selectedFragment = new DiaryFragment();
                    break;
                case R.id.nav_info:
                    selectedFragment = new InfoFragment();
                    break;
                case R.id.nav_exer:
                    selectedFragment = new ExerFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, selectedFragment).commit();
            return true;
        }
    };
}
