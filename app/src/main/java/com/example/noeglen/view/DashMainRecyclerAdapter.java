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
import com.example.noeglen.data.FavoriteDTO;

import java.util.List;

public class DashMainRecyclerAdapter extends RecyclerView.Adapter<DashMainRecyclerAdapter.ViewHolder> {

    private List<FavoriteDTO> favorites;

    private Context context;
    private OnFavoriteListener onFavoriteListener;

    private static final String TAG = "DashMainRecyclerAdapter";


    public DashMainRecyclerAdapter(List<FavoriteDTO> favorites, Context context, OnFavoriteListener onFavoriteListener) {
        this.favorites = favorites;
        this.context = context;
        this.onFavoriteListener = onFavoriteListener;

        if (favorites != null){
            Log.d(TAG, "DashMainRecyclerAdapter: favoriteListSize = " + favorites.size());
        }
        else {
            Log.d(TAG, "DashMainRecyclerAdapter: listOfKnowledgeArticles = NULL");
        }
    }

    @Override
    public DashMainRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_dashmain,parent,false);
        return new ViewHolder(view,onFavoriteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DashMainRecyclerAdapter.ViewHolder holder, int position) {
        try {

            holder.favTitle.setText(favorites.get(position).getTitle());
            Glide
                    .with(context)
                    .load(favorites.get(position).getIamgeURL())
                    .into(holder.favImage);

            //FIXME ADD ICONS FOR VIDEO AND EXERCISE
            switch (favorites.get(position).getCURRENT_TYPE()){
                case 1:
                    //holder.iconImage.setBackgroundResource();
                    break;
                case 2:
                    //holder.iconImage.setBackgroundResource();
                    break;
                case 3:
                    holder.iconImage.setBackgroundResource(R.drawable.article_trans_back);
                    break;
            }

        } catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: NULLPOINTEREXCEPTION = " + e.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        if (favorites != null){
            return favorites.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnFavoriteListener onFavoriteListener;
        private ImageView favImage, iconImage;
        private TextView favTitle;


        public ViewHolder(@NonNull View itemView, OnFavoriteListener onFavoriteListener) {
            super(itemView);
            this.onFavoriteListener = onFavoriteListener;
            this.favImage = itemView.findViewById(R.id.dashmain_fav_image);
            this.favTitle = itemView.findViewById(R.id.dashmain_fav_titel);
            this.iconImage = itemView.findViewById(R.id.dashmain_fav_icontype);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFavoriteListener.onFavoriteClick(getAdapterPosition(), favorites.get(getAdapterPosition()).getCURRENT_TYPE());
        }
    }

    public interface OnFavoriteListener {
        void onFavoriteClick(int position, int CURRENT_TYPE);
    }
}
