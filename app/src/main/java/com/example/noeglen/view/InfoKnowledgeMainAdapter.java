package com.example.noeglen.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noeglen.R;
import com.example.noeglen.data.KnowledgeDTO;

import java.util.List;

public class InfoKnowledgeMainAdapter extends RecyclerView.Adapter<InfoKnowledgeMainAdapter.ViewHolder> {

    private List<KnowledgeDTO> articles;
    private OnArticleListener onArticleListener;
    private ImageView image;
    private Context context;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    public InfoKnowledgeMainAdapter(Context context, List<KnowledgeDTO> articles, OnArticleListener onArticleListener) {
        this.context = context;
        this.articles = articles;
        this.onArticleListener = onArticleListener;
        Log.d(TAG, "InfoKnowledgeMainAdapter: CONSTRUCTOR - articles size: " + articles.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_infomainknowledgearticles,parent,false);
        return new ViewHolder(view,onArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoKnowledgeMainAdapter.ViewHolder holder, int position) {
        try {
            holder.tTitle.setText(articles.get(position).getTitle());

            Log.d(TAG, "onBindViewHolder: context = " + context.toString());

            Glide
                    .with(context)
                    .load(articles.get(position).getImage())
                    .into(holder.image);
            holder.image.setBackground(context.getDrawable(android.R.color.transparent));

        } catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: NULLPOINTER EXCEPTION");
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
        TextView tTitle;
        ImageView image;

        public ViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
            super(itemView);
            this.onArticleListener = onArticleListener;
            this.tTitle = itemView.findViewById(R.id.infoknowledgemain_recyclerview_title);
            this.image = itemView.findViewById(R.id.infoknowledgemain_recyclerview_image);

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
