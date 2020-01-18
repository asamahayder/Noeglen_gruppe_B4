package com.example.noeglen.data;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LicenseDAO implements ILicenseDAO {

    private LicenseDTO license;
    private FirebaseFirestore db;
    private DocumentReference dRef;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    public LicenseDTO getLicense(String collection, String authLicense) {

        license = new LicenseDTO();

        db = FirebaseFirestore.getInstance();
        dRef = db.collection(collection).document(authLicense);

        dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                license = documentSnapshot.toObject(LicenseDTO.class);
                Log.d(TAG, "onSuccess: SUCCESSFULLY RETRIEVED FROM FIREBASE");
                System.out.println(license.getUserID());
                System.out.println(license.getLicense());
            }
        });

        System.out.println(license.getLicense());
        System.out.println(license.getUserID());
        return license;
    }

    public void getLicense2(String authLicense, final MyCallBack myCallBack) {

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
}