package com.example.noeglen.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noeglen.R;
import com.example.noeglen.data.MyCallBack;
import com.example.noeglen.data.LicenseDAO;
import com.example.noeglen.data.LicenseDTO;
import com.example.noeglen.data.VideoDAO;
import com.example.noeglen.data.VideoDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText insertEmail;
    EditText insertPass;
    EditText insertLicense;
    TextView registerMenuBtn;
    TextView loginMenuBtn;
    TextView registerSelect;
    TextView loginBtn;
    TextView TVlicense;
    ProgressBar progressBar;
    private ArrayList<VideoDTO> videoList;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    LicenseDAO iLicenseDAO;
    Map<String, String> licenseInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_login);

        /** Henter firebase instans samt bruger info */
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        /** Hvis brugeren er logget ind og lukkede applikationen tjekker nedenstående kode for om brugeren behøver at logge ind eller ej. */
        if (mUser != null) {
            Intent welcome = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(welcome);
            finish();
        }

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        insertEmail = findViewById(R.id.EmailInsert);
        insertPass = findViewById(R.id.PassInsert);
        insertLicense = findViewById(R.id.LicenseInsert);
        insertLicense.setVisibility(View.GONE);

        TVlicense = findViewById(R.id.LicenseTV);
        TVlicense.setVisibility(View.GONE);

        registerSelect = findViewById(R.id.RegisterTV);
        registerSelect.setVisibility(View.GONE);
        loginBtn = findViewById(R.id.LoginTV);

        loginMenuBtn = findViewById(R.id.LoginBtn);
        registerMenuBtn = findViewById(R.id.RegBtn);

        /** Skifter ud med hinanden */
        registerSelect.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        registerMenuBtn.setOnClickListener(this);
        loginMenuBtn.setOnClickListener(this);
        registerSelect.setVisibility(View.GONE);
        loginMenuBtn.setVisibility(View.GONE);
        /** Henter licens objekt der bruges til at tjekke om licensen der bruges til at lave en bruger er gyldig */
        iLicenseDAO = new LicenseDAO();
    }

    @Override
    public void onClick(View v) {

        /** Register indsætnings form */
        if (v.equals(registerMenuBtn)) {

            registerMenuBtn.setVisibility(View.GONE);
            loginBtn.setVisibility(View.GONE);
            loginMenuBtn.setVisibility(View.VISIBLE);
            registerSelect.setVisibility(View.VISIBLE);
            insertPass.setText("");
            insertEmail.setText("");
            insertLicense.setText("");
            insertLicense.setVisibility(View.VISIBLE);
            TVlicense.setVisibility(View.VISIBLE);

            /** Login indsætnings form */
        } else if (v.equals(loginMenuBtn)) {

            registerMenuBtn.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.VISIBLE);
            loginMenuBtn.setVisibility(View.GONE);
            registerSelect.setVisibility(View.GONE);
            insertPass.setText("123456");
            insertEmail.setText("test@user.com");
            insertLicense.setVisibility(View.GONE);
            TVlicense.setVisibility(View.GONE);

            /** Log in metode */
        } else if (v.equals(loginBtn) && registerSelect.getVisibility() == View.GONE) {

            signIn(insertEmail.getText().toString(), insertPass.getText().toString());

            /** Register metode og licenstjek*/
        } else if (v.equals(registerSelect) && loginBtn.getVisibility() == View.GONE){

            licenseCheck(insertLicense.getText().toString(), insertEmail.getText().toString(), insertPass.getText().toString());

        }
    }

    /**
     * Denne metode bruges til at tjekke om licensen der bruges til at lave en bruger er gyldig
     * @param license = licensen som fås fra virksomheden når man køber appen
     * @param email = brugerens e-mail
     * @param password = brugerens kodeord
     */
    private void licenseCheck(final String license, final String email, final String password ) {

        /**
         * Tjekker om der er indsat de rette informationer
         */

        if (!validateForm()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        registerSelect.setVisibility(View.GONE);

        /**
         * Henter call back metoden. Denne bruges til at sikre at den information vi skal bruge er hentet inden koden den fortsætter.
         * Først tjekker den om den indtastede licens findes i databasen. Hvis ikke er den ugyldig. Næst tjekker den for om den indtastede
         * Licens bruges af en anden person. Hvis ikke kører den vores lav en bruger metode.
         */

        final LicenseDAO licenseDAO = new LicenseDAO();
        licenseDAO.getLicense(license, new MyCallBack() {
            @Override
            public void onCallBack(Object object) {
                LicenseDTO licens = (LicenseDTO) object;

                if (licens == null) {
                    progressBar.setVisibility(View.GONE);
                    registerSelect.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Ubrulig licens", Toast.LENGTH_SHORT).show();

                } else {

                    if (licens.getUserID() == null) {
                        createAccount(email, password, license);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        registerSelect.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Licens allerede i brug", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //--- Disse metoder er taget fra Googles firebase preset authentication med lidt ændringer
    //--- https://github.com/firebase/quickstart-android/blob/90389865dc8a64495b1698c4793cd4deecc4d0ee/auth/app/src/main/java/com/google/firebase/quickstart/auth/java/CustomAuthActivity.java#L98-L115
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Som sagt er disse metoder taget fra firebase egne presets der virker med deres database.
     * Denne tager imod to text felter der indeholder e-mail og kodeord og pass'er dem videre
     * til firebase authentication metode som tjekker om der findes et user objekt med samme informatioer
     */

    private void signIn(String email, String password) {

        if (!validateForm()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.GONE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getAllVideosFromDataBase();

                        } else {

                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            System.out.println("failure");
                            progressBar.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    /** Igen virker denne på samme måde som sign in metoden hvor i stedet for at tjekker om der findes den
     * specifikke information indsætter den hvad der står i diverse EditText views i deres user database
     * bestående af en email og et kodeord.
     *
     * En anden ting som sker i denne metode som vi har valgt at indsætte er det Map objekt som indeholder
     * userID og licens. Dette tilføjes til vores eget table i databasen og holder styr på når en licens bliver brugt.
     * Her indsætter vi et bruger ID hos den respektive licens.**/
    private void createAccount(String email, String password, final String license) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    licenseInfo.put("UserID", mAuth.getUid());
                    licenseInfo.put("license", license);
                    final LicenseDAO licenseDAO = new LicenseDAO();
                    licenseDAO.insertLicense(license, licenseInfo);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    registerSelect.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /**
     * Igen en brugt metode fra firebases eget bibliotek som bare tjekker om diverse EditText view indeholder noget text
     * @return
     */
    private boolean validateForm() {
        boolean valid = true;

        String email = insertEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            insertEmail.setError("Krævet.");
            valid = false;
        } else {
            insertEmail.setError(null);
        }

        String password = insertPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            insertPass.setError("Krævet.");
            valid = false;
        } else {
            insertPass.setError(null);
        }

        if (insertLicense.getVisibility() == View.VISIBLE) {
            String license = insertLicense.getText().toString();
            if (TextUtils.isEmpty(license)) {
                insertLicense.setError("Krævet.");
                valid = false;
            } else {
                insertLicense.setError(null);
            }
        }

        return valid;
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * For at gøre det bedre for brugeren at bruge applikationen henter vi videoerne der skal bruges ved login
     * Dette forsager et lidt længere tidsforbrug ved login men sker så kun ved login i stedet for inde i appen
     */
    public void getAllVideosFromDataBase(){
        VideoDAO videoDAO = new VideoDAO();
        videoDAO.getAllVideos(new MyCallBack() {
            @Override
            public void onCallBack(Object object) {
                setVideoList((ArrayList<VideoDTO>) object);
                saveVideoList();
                handleCheckWelcomeScreen();
            }
        });
    }

    public void setVideoList(ArrayList<VideoDTO> videoList){
        this.videoList = videoList;
    }

    /**
     * Når brugeren signer in kaldes denne metode for at sende dem hen til vores dashboard
     */
    public void handleGoToDashBoard(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     *
     */
    public void saveVideoList(){
        Gson gson = new Gson();
        String listInJSON = gson.toJson(videoList);
        String preferenceKey = getString(R.string.sharedPreferencesKey);
        String videoListKey = getString(R.string.videoListKey);

        SharedPreferences sharedPreferences = getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(videoListKey, listInJSON);
        editor.apply();
    }

    /**
     * Her tjekkes om brugeren logger ind for første gang. Vi henter bare en boolean værdi og bagefter indsætter
     * værdien til at det ikke er første gang brugeren logger ind.
     */
    public void handleCheckWelcomeScreen(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferencesKey),MODE_PRIVATE);
        String isFirstTime = sharedPreferences.getString(getString(R.string.firstTimeKey),null);

        if (isFirstTime == null || isFirstTime.equals("true")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getString(R.string.firstTimeKey),"false");
            editor.apply();
            Intent intent = new Intent(LoginActivity.this,WelcomeTextActivity.class);
            startActivity(intent);
            this.finish();
        }else{
            handleGoToDashBoard();
        }
    }
}