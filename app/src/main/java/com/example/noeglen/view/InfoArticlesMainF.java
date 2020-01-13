package com.example.noeglen.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noeglen.R;
import com.example.noeglen.data.ArticleDAO;
import com.example.noeglen.data.ArticleDTO;
import com.example.noeglen.data.IArticleDAO;
import com.google.gson.Gson;

import java.util.List;

public class InfoArticlesMainF extends Fragment implements InfoArticlesMainAdapter.OnArticleListener {

    private IMainActivity iMain;
    private static IArticleDAO iArticle;
    private static List<ArticleDTO> articles;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infoarticlesmain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    private void initializeView() {
        iArticle = new ArticleDAO();
        new LoadArticles().execute();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onArticleClick(int position) {
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        InfoArticleF article = new InfoArticleF();
        //String json = gson.toJson(articles.get(position));
        String json = gson.toJson(new ArticleDTO("ehader","titles","assdsasdasdasdasdadasdasdasda" +
                "sssssssssssssssssssssssssssadasdasdasdasdasdasdasdasdasdasdasd /n asdasdasdasdasdasdasdasdasdasdasdasdasasdasd |\n asdasdasdasdasdasdasd \n asdasasd \n asdasdasd","asd"));
        bundle.putString("currentArticle",json);
        iMain.setFragment(article,getString(R.string.fragment_infoarticle),true,bundle);
    }

    public class LoadArticles extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            articles = iArticle.getListOfArticles("Articles");
            while (articles == null || articles.size() < 1){
                Log.d(TAG, "doInBackground: article size = " + articles.size());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setRecyclerview();
        }
    }

    private void setRecyclerview() {
        RecyclerView rView = getView().findViewById(R.id.infoarticlesmain_recyclerview);
        InfoArticlesMainAdapter adapter = new InfoArticlesMainAdapter(getContext(), articles, this);
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        rView.setAdapter(adapter);
    }
}
