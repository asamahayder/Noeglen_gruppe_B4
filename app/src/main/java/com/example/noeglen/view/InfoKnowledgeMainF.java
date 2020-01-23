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

public class InfoKnowledgeMainF extends Fragment implements InfoKnowledgeMainAdapter.OnArticleListener {

    private IMainActivity iMain;
    private static IKnowledgeDAO iArticle;
    private static List<KnowledgeDTO> listOfKnowledgeArticles;
    private RecyclerView rView;
    private InfoKnowledgeMainAdapter adapter;
    private ProgressBar infoKnowledgeMainProgressBar;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        iMain = (IMainActivity) getActivity();
        iArticle = new KnowledgeDAO();
        new LoadArticles().execute();
        return inflater.inflate(R.layout.fragment_infoknowledgemain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        infoKnowledgeMainProgressBar = getView().findViewById(R.id.infoKnowledgeMainProgressBar);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onArticleClick(int position) {
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        InfoKnowledgeF article = new InfoKnowledgeF();
        String json = gson.toJson(listOfKnowledgeArticles.get(position));
        bundle.putString("currentKnowledgeArticle",json);
        iMain.setFragment(article,getString(R.string.fragment_infoknowledge),true,bundle);
    }

    public class LoadArticles extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            listOfKnowledgeArticles = iArticle.getListOfArticles("Articles");

            while (listOfKnowledgeArticles == null || listOfKnowledgeArticles.size() < 1){
                Log.d(TAG, "doInBackground: article size = " + listOfKnowledgeArticles.size());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setRecyclerview();
            infoKnowledgeMainProgressBar.setVisibility(View.GONE);
        }
    }

    private void setRecyclerview() {
        if (getView() != null){
            rView = getView().findViewById(R.id.infoknowledgemain_recyclerview);
            adapter = new InfoKnowledgeMainAdapter(getContext(), listOfKnowledgeArticles, this);
            rView.setLayoutManager(new LinearLayoutManager(getContext()));
            rView.setAdapter(adapter);
        }
    }
}
