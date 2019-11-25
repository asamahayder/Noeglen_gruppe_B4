package com.example.noeglen.View.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.noeglen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navList);


        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, new HomeFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navList = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_fav:
                    selectedFragment = new FavFragment();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, selectedFragment).commit();
            return true;
        }
    };
}
