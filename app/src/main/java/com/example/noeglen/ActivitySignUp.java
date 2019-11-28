package com.example.noeglen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

//Denne klasse indeholder to måder at lave en login funktionalitet vha. google. Dene ene med onActivityResult kan både skabe brugere og login
// på samme tid, hvilket er smart men også kan virke forvirrende. Den anden kan bare skabe brugere og måske logger den også automatisk ind bagefter.

public class ActivitySignUp extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    //metode 2
    //private FirebaseAuth mAuth;

    //hører til metode 1
    //private boolean loggedin;

    //for trying to get videolist
    private List<String> videoIDs;

    private YouTubePlayerView youTubePlayerView;
    private String videoID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

       // tryGetListFromFireStore();
        initYoutubeVideo();




        //hører til metode 2
        //mAuth = FirebaseAuth.getInstance();

        //hører til metode 1
        /*// Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                1);*/
    }

    //hører til metode 1. Dette er hvad jeg går efter
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                loggedin = true;

            } else {
                //TODO ijplement better handling
                loggedin = false;
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                System.out.println("OH no");
            }
        }

        if (loggedin){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, String> map = new HashMap<>();
            map.put("lastName", "Hayder");
            db.collection("users").document("asamahayder").set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    System.out.println("#####################the transfer was a success");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("########################the transfer was NOT a success");
                }
            });
        }
    }*/

    //hører til metode to
    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String email = "randommail@random.random";
        String password = "randompassword";
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("#############user created succesfully");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("#################could not create user");
                            Toast.makeText(ActivitySignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        // ...
                    }
                });

        //Todo implement this
        //updateUI(currentUser);
    }*/


    public void tryGetListFromFireStore(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("videos").document("videolist");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("document exist");
                        DocumentSnapshot documentSnapshot = task.getResult();
                        setVideoIDs ((List<String>) documentSnapshot.get("videoIDs"));
                        System.out.println("size of array: " + getVideoIDs().size());
                        System.out.println("Here is the content:");
                        for (int i = 0; i < getVideoIDs().size(); i++) {
                            System.out.println(getVideoIDs().get(i));
                        }
                    } else {
                        System.out.println("no such document");
                    }
                } else {
                    System.out.println("failed: " + task.getException());
                }
            }
        });
    }

    public void initYoutubeVideo(){
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        if (youTubePlayerView == null){
            System.out.println("hej med dig");
        }
        youTubePlayerView.initialize("AIzaSyB1ZHv40LuyAjJ7ygFNU7ImVVEUTTsf0uw", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo("Do7Nai2oSZU");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }


    public List<String> getVideoIDs() {
        return videoIDs;
    }

    public void setVideoIDs(List<String> videoIDs) {
        this.videoIDs = videoIDs;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            System.out.println("kommer vi her?");
            youTubePlayer.cueVideo("Do7Nai2oSZU");
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        System.out.println("oh no!");
    }
}
