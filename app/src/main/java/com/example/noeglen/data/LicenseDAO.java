package com.example.noeglen.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class LicenseDAO {

    private LicenseDTO license;
    private FirebaseFirestore db;
    private DocumentReference dRef;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    public void getLicense(String authLicense, final MyCallBack myCallBack) {

        license = new LicenseDTO();

        db = FirebaseFirestore.getInstance();
        dRef = db.collection("License").document(authLicense);

        dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                license = documentSnapshot.toObject(LicenseDTO.class);
                Log.d(TAG, "onSuccess: SUCCESSFULLY RETRIEVED FROM FIREBASE");
                myCallBack.onCallBack(license);
            }
        });
    }

    public void insertLicense(String authLicense, Map<String, String> userinfo){

        db = FirebaseFirestore.getInstance();
        dRef = db.collection("License").document(authLicense);
        dRef.set(userinfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error writing document", e);
                }
            });
    }
}