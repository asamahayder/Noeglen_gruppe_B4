package com.example.noeglen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.noeglen.R;
import com.example.noeglen.data.ArticleDTO;

import java.util.List;

public class InfoArticlesMainAdapter extends RecyclerView.Adapter<InfoArticlesMainAdapter.ViewHolder> {

    private List<ArticleDTO> articles;
    private OnArticleListener onArticleListener;
    private ImageView image;
    private Context context;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    public InfoArticlesMainAdapter(Context context, List<ArticleDTO> articles, OnArticleListener onArticleListener) {
        this.context = context;
        this.articles = articles;
        this.onArticleListener = onArticleListener;
        Log.d(TAG, "InfoArticlesMainAdapter: CONSTRUCTOR - articles size: " + articles.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_infomainarticles,parent,false);
        return new ViewHolder(view,onArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoArticlesMainAdapter.ViewHolder holder, int position) {
        try {
            holder.tTitle.setText(articles.get(position).getTitle());
            holder.tSubtext.setText(articles.get(position).getHeader());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new RoundedCorners(48));

            Log.d(TAG, "onBindViewHolder: context = " + context.toString());

            Glide
                    .with(context)
                    .load(articles.get(position).getImage())
                    .apply(requestOptions)
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
        TextView tTitle, tSubtext;
        ImageView image;

        public ViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
            super(itemView);
            this.onArticleListener = onArticleListener;
            this.tTitle = itemView.findViewById(R.id.infoarticlesmain_recyclerview_title);
            this.tSubtext = itemView.findViewById(R.id.infoarticlesmain_recyclerview_subtext);
            this.image = itemView.findViewById(R.id.infoarticlesmain_recyclerview_image);

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
