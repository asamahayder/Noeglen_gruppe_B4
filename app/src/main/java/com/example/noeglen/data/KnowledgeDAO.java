package com.example.noeglen.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Klassen der henter data fra databasen når der tales om artikler. Laver en forbindelse med firebase og gemmer dataet som et objekt
 */

public class KnowledgeDAO implements IKnowledgeDAO {

    /**
     * @variable knowledgeArticle
     * Bliver brugt til at gemme det inviduelle objekt af artiklen som kan findes i databasen
     * @variable listOfKnowledgeArticles
     * Bliver brugt til at hente en liste af objekter af type artikel.
     * @variable db
     * Bliver brugt til at hente en instans af firebase, som bruges senere i Dref
     * @variable dRef
     * Bliver brugt til at hente en referance fra firebase til at hente data fra databasen
     */

    private KnowledgeDTO knowledgeArticle;
    private List<KnowledgeDTO> listOfKnowledgeArticles;

    private FirebaseFirestore db;
    private DocumentReference dRef;
    /**
     * Henter en specifik artikel fra databasen, ved brug af firebase variablerne db og dRef.
     *
     * @param collection
     * Firebase bruger en collection til at holde en liste a forskellige ting i. Derfor skal metoden bruge en parameter som finder
     * hvilken collection denne artikel ligger i.
     *
     * @param articleTitle
     * Selve artikelen som skal findes har brug for en parameter, det er så denne. Leder i collectionen for at finde denne artikel
     *
     * @return
     * Returnerer artikel objektet sum blev fundet på databasen
     */

    @Override
    public KnowledgeDTO getArticle(String collection, String articleTitle) {
        knowledgeArticle = new KnowledgeDTO();
        db = FirebaseFirestore.getInstance();
        dRef = db.collection(collection).document(articleTitle);

        dRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                knowledgeArticle = documentSnapshot.toObject(KnowledgeDTO.class);
            }
        });

        return knowledgeArticle;
    }

    /**
     * Henter en liste af objekter fra en specifik collection
     *
     * @param collection
     * collectionen er hvor alle objekter ligger i, som derefter bliver brugt til at lede i databasen med den parameter
     *
     * @return
     * returnerer listen af artikler fundet i databasen
     */

    @Override
    public List<KnowledgeDTO> getListOfArticles(final String collection) {
        listOfKnowledgeArticles = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection(collection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    int i = 0;
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        knowledgeArticle = snapshot.toObject(KnowledgeDTO.class);
                        listOfKnowledgeArticles.add(knowledgeArticle);
                    }
                }
            }
        });
        return listOfKnowledgeArticles;
    }
}
