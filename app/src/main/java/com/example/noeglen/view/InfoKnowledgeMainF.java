package com.example.noeglen.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noeglen.R;
import com.example.noeglen.data.KnowledgeDAO;
import com.example.noeglen.data.KnowledgeDTO;
import com.example.noeglen.data.IKnowledgeDAO;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

/**
 * Hoved artikel fragmentet der indeholder en liste af artikler fra databasen
 */

public class InfoKnowledgeMainF extends Fragment implements InfoKnowledgeMainAdapter.OnArticleListener {

    /**
     * @variable iMain
     * Interfacet til main aktiviteten, hvro fragmenterne bliver ændret
     * @variable iArticle
     * artikel DAO klassen der henter artikler fra databasen
     * @variable listOfKnowledgeArticles
     * listen af artikler der kommer fra databasen og bliver loades ind i recyclerviewt
     * @variable infoKnowledgeMainProgressBar
     * loading baren der kommer op mens listen bliver loadet
     */

    private IMainActivity iMain;
    private static IKnowledgeDAO iArticle;
    private static List<KnowledgeDTO> listOfKnowledgeArticles;
    private ProgressBar infoKnowledgeMainProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //instantierer objekterne som bruges senere
        iMain = (IMainActivity) getActivity();
        iArticle = new KnowledgeDAO();

        //laver en ny tråd til at hente artikler fra databasen
        new LoadArticles().execute();
        return inflater.inflate(R.layout.fragment_infoknowledgemain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //kalder på loading baren mens der er ikke sket noget
        infoKnowledgeMainProgressBar = getView().findViewById(R.id.infoKnowledgeMainProgressBar);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Opfører sig som en onClick metode, men er lavet til recyclerviewet
     *
     * @param position
     * position er hvilken position man er på recyclerviewet
     */

    @Override
    public void onArticleClick(int position) {
        //laver et bundle tin at sende videre til et nyt fragment
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        InfoKnowledgeF article = new InfoKnowledgeF();
        //laver objektet der var trykket på om til et json objekt
        String json = gson.toJson(listOfKnowledgeArticles.get(position));
        //adder objektet til bundlet og sender det videre til det næste fragment
        bundle.putString("currentKnowledgeArticle",json);
        iMain.setFragment(article,getString(R.string.fragment_infoknowledge),true,bundle);
    }

    /**
     * AsyncTask klassen der loader artikler ind i liste objektet fra firebase
     */
    public class LoadArticles extends AsyncTask<Void, Void, Void>{

        /**
         * Kalder på iArticle til at hente fra databasen og gemmer det i en liste af artikel objekter
         */
        @Override
        protected Void doInBackground(Void... voids) {

            listOfKnowledgeArticles = iArticle.getListOfArticles("Articles");

            while (listOfKnowledgeArticles == null || listOfKnowledgeArticles.size() < 1){
            }
            return null;
        }

        /**
         * Når det er færdigt at hente fra firebase bliver recyclerViewet opdateret og artiklerne sat ind
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            setRecyclerview();
            infoKnowledgeMainProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Opdaterer recyclerviewet
     */
    private void setRecyclerview() {
        if (getView() != null){
            //Finder recyclerviewet
            RecyclerView rView = getView().findViewById(R.id.infoknowledgemain_recyclerview);
            //laver en adapter og kalder adapter objektet
            InfoKnowledgeMainAdapter adapter = new InfoKnowledgeMainAdapter(getContext(), listOfKnowledgeArticles, this);
            rView.setLayoutManager(new LinearLayoutManager(getContext()));
            //laver recyclerviewet
            rView.setAdapter(adapter);
        }
    }
}
