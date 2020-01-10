package com.example.noeglen.view;

import android.content.Context;
import android.os.Bundle;
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

import java.util.List;

public class InfoArticlesMainF extends Fragment implements InfoArticlesMainAdapter.OnArticleListener {

    private RecyclerView rView;
    private InfoArticlesMainAdapter adapter;

    private IMainActivity iMain;
    private IArticleDAO iArticle;
    private List<ArticleDTO> articles;

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
        articles = iArticle.getListOfArticles("Articles");

        // FIXME Dette er for testing
        for (int i = 0; i < 12; i++) {
            ArticleDTO article = new ArticleDTO("test","test","test","https://firebasestorage.googleapis.com/v0/b/noeglen-18170.appspot.com/o/Articles%2F1.png?alt=media&token=d24de751-e8ca-4c54-94f0-07420430b5af");
            articles.add(article);
        }

        rView = getView().findViewById(R.id.infoarticlesmain_recyclerview);
        adapter = new InfoArticlesMainAdapter(getContext(), articles, this);
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        rView.setAdapter(adapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMain = (IMainActivity) getActivity();
    }

    @Override
    public void onArticleClick(int position) {
        iMain.inflateFragment(getString(R.string.fragment_infoarticle));
    }
}
