package com.example.noeglen.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noeglen.R;
import com.example.noeglen.data.ArticleDTO;

import java.util.List;

public class InfoArticlesMainAdapter extends RecyclerView.Adapter<InfoArticlesMainAdapter.ViewHolder> {

    private List<ArticleDTO> articles;
    private OnArticleListener onArticleListener;

    private static final String TAG = "InfoArticlesMainAdapter";

    public InfoArticlesMainAdapter(List<ArticleDTO> articles, OnArticleListener onArticleListener) {
        this.articles = articles;
        this.onArticleListener = onArticleListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_infomainarticles,parent);
        return new ViewHolder(view,onArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoArticlesMainAdapter.ViewHolder holder, int position) {

        try {
            holder.tTitle.setText(articles.get(position).getTitle());
            holder.tSubtext.setText(articles.get(position).getHeader());

        } catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: Nullpointer exception");
        }
    }

    @Override
    public int getItemCount() {
        if (articles == null){
            return 0;
        }
        else {
            return articles.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnArticleListener onArticleListener;
        TextView tTitle, tSubtext;

        public ViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
            super(itemView);
            this.onArticleListener = onArticleListener;
            this.tTitle = itemView.findViewById(R.id.infoarticlesmain_recyclerview_title);
            this.tSubtext = itemView.findViewById(R.id.infoarticlesmain_recyclerview_subtext);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onArticleListener.onArticleClick(getAdapterPosition());
        }
    }

    public interface OnArticleListener{
        void onArticleClick(int position);
    }
}
