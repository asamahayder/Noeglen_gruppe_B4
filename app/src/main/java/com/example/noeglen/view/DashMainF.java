package com.example.noeglen.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.example.noeglen.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class DashMainF extends Fragment implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private IMainActivity iMain;
    private CardView iVidDash, iDiaryDash, iExerciseDash;
    private String fragmentTag;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private static int Request = 4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashmain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();

        toolbar = getView().findViewById(R.id.toolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = getView().findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));


        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initializeView() {
        iVidDash = Objects.requireNonNull(getView()).findViewById(R.id.iDashVid);
        iDiaryDash = Objects.requireNonNull(getView()).findViewById(R.id.iDashDiary);
        iExerciseDash = Objects.requireNonNull(getView()).findViewById(R.id.iDashExercise);
        iVidDash.setOnClickListener(this);
        iDiaryDash.setOnClickListener(this);
        iExerciseDash.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iDashVid:
                fragmentTag = getString(R.string.fragment_dashvidmain);
                break;
            case R.id.iDashDiary:
                fragmentTag = getString(R.string.fragment_diarymain);
                break;
            case R.id.iDashExercise:
                fragmentTag = getString(R.string.fragment_exermain);
                break;
        }
        iMain.inflateFragment(fragmentTag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.phoneContact:
                phonePermission();
                break;
            case R.id.emailContact:
                openMail();
                break;
            case R.id.chat:
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.darkMode:
                FirebaseAuth.getInstance().signOut();
                Intent login = new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
                getActivity().finish();
                break;

        }
        return true;
    }

    private void phonePermission() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, Request);
        } else {
            String phoneNumber = getResources().getString(R.string.phoneNumber);
            String uri = "tel:" + phoneNumber;
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Request) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                phonePermission();
            }
        }
    }

    private void openMail() {

        String emailAddress = getResources().getString(R.string.emailAddress);

        System.out.println(emailAddress);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Vælg en email klient"));
    }
}
