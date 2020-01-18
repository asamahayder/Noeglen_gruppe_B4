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
import com.example.noeglen.data.ExerciseDTO;
import com.example.noeglen.data.FavoritesDTO;
import com.example.noeglen.data.KnowledgeDTO;
import com.example.noeglen.data.VideoDTO;

import java.util.List;

public class DashMainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FavoritesDTO favorites;
    private List<KnowledgeDTO> listOfKnowledgeArticles;
    private List<VideoDTO> listOfVideos;
    private List<ExerciseDTO> listOfExercises;

    private Context context;
    private OnFavoriteListener onFavoriteListener;

    private static final String TAG = "DashMainRecyclerAdapter";

    private static final int TYPE_VIDEO = 1;
    private static final int TYPE_EXERCISE = 2;
    private static final int TYPE_KNOWLEDGE = 3;
    private static int CURRENT_TYPE = 0;


    public DashMainRecyclerAdapter(FavoritesDTO favorites, Context context, OnFavoriteListener onFavoriteListener) {
        this.favorites = favorites;
        this.context = context;
        this.onFavoriteListener = onFavoriteListener;

        listOfVideos = favorites.getListOfVideoDTOS();
        listOfKnowledgeArticles = favorites.getListOfKnowledgeDTOS();
        listOfExercises = favorites.getListOfExerciseDTOS();

        if (listOfVideos != null){
            Log.d(TAG, "DashMainRecyclerAdapter: listOfVideos = " + listOfVideos.size());
        }
        else {
            Log.d(TAG, "DashMainRecyclerAdapter: listOfVideos = NULL");
        }
        if (listOfExercises != null){
            Log.d(TAG, "DashMainRecyclerAdapter: listOfExercises = " + listOfExercises.size());
        }
        else {
            Log.d(TAG, "DashMainRecyclerAdapter: listOfExercises = NULL");
        }
        if (listOfKnowledgeArticles != null){
            Log.d(TAG, "DashMainRecyclerAdapter: listOfKnowledgeArticles = " + listOfKnowledgeArticles.size());
        }
        else {
            Log.d(TAG, "DashMainRecyclerAdapter: listOfKnowledgeArticles = NULL");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_dashmain,parent,false);

        if (viewType == TYPE_VIDEO){
            return new VideoViewHolder(view,onFavoriteListener);
        }
        if (viewType == TYPE_EXERCISE){
            return new ExerciseViewHolder(view,onFavoriteListener);
        }
        if (viewType == TYPE_KNOWLEDGE){
            return new KnowledgeViewHolder(view,onFavoriteListener);
        }
        throw new RuntimeException("There is no type that matches the type" + viewType);
    }

    @Override
    public int getItemViewType(int position) {
        boolean viewTypeFound = false;
        if (listOfVideos != null && !listOfVideos.isEmpty() && position == 0){
            viewTypeFound = true;
            CURRENT_TYPE = TYPE_VIDEO;
            Log.d(TAG, "getItemViewType: VIEWTYPE = VIDEO");
            return CURRENT_TYPE;
        }
        if (listOfExercises != null && !listOfExercises.isEmpty() && listOfVideos != null && (position == listOfVideos.size() + 1 || listOfVideos.isEmpty())){
            viewTypeFound = true;
            CURRENT_TYPE = TYPE_EXERCISE;
            Log.d(TAG, "getItemViewType: VIEWTYPE = EXERCISE");
            return CURRENT_TYPE;
        }
        if (listOfKnowledgeArticles != null && !listOfKnowledgeArticles.isEmpty() && listOfExercises != null && (position == listOfExercises.size() + 1 || listOfExercises.isEmpty())){
            viewTypeFound = true;
            CURRENT_TYPE = TYPE_KNOWLEDGE;
            Log.d(TAG, "getItemViewType: VIEWTYPE = KNOWLEDGE");
            return CURRENT_TYPE;
        }
        if (!viewTypeFound){
            Log.d(TAG, "getItemViewType: No viewtype found, maybe favorites are empty?");
            Log.d(TAG, "getItemViewType: position = " + position);
        }
        else {
            Log.d(TAG, "getItemViewType: viewtype found and it's = " + CURRENT_TYPE);
            Log.d(TAG, "getItemViewType: position = " + position);
        }
        return CURRENT_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof VideoViewHolder){
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.favTitle.setText(listOfVideos.get(position).getTitle());
                Glide
                        .with(context)
                        .load(listOfVideos.get(position).getImageUrl())
                        .into(videoViewHolder.favImage);
            }
            if (holder instanceof ExerciseViewHolder){
                ExerciseViewHolder exerciseViewHolder = (ExerciseViewHolder)holder;
                exerciseViewHolder.favTitle.setText(listOfExercises.get(position).getTitle());
                Glide
                        .with(context)
                        .load(listOfExercises.get(position).getImage())
                        .into(exerciseViewHolder.favImage);
            }
            if (holder instanceof KnowledgeViewHolder){
                KnowledgeViewHolder knowledgeViewHolder = (KnowledgeViewHolder)holder;
                knowledgeViewHolder.favTitle.setText(listOfKnowledgeArticles.get(position).getTitle());
                Glide
                        .with(context)
                        .load(listOfKnowledgeArticles.get(position).getImage())
                        .into(knowledgeViewHolder.favImage);
            }
        } catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: NULLPOINTEREXCEPTION = " + e.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        int amountOfFavorites = 0;
        if (listOfExercises != null){
            amountOfFavorites += listOfExercises.size();
        }
        if (listOfKnowledgeArticles != null){
            amountOfFavorites += listOfKnowledgeArticles.size();
        }
        if (listOfVideos != null){
            amountOfFavorites += listOfVideos.size();
        }
        return amountOfFavorites;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnFavoriteListener onFavoriteListener;
        private ImageView favImage;
        private TextView favTitle;


        public VideoViewHolder(@NonNull View itemView, OnFavoriteListener onFavoriteListener) {
            super(itemView);
            this.onFavoriteListener = onFavoriteListener;
            this.favImage = itemView.findViewById(R.id.dashmain_fav_image);
            this.favTitle = itemView.findViewById(R.id.dashmain_fav_titel);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFavoriteListener.onFavoriteClick(getAdapterPosition(),CURRENT_TYPE);
        }
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnFavoriteListener onFavoriteListener;
        private ImageView favImage;
        private TextView favTitle;


        public ExerciseViewHolder(@NonNull View itemView, OnFavoriteListener onFavoriteListener) {
            super(itemView);
            this.onFavoriteListener = onFavoriteListener;
            this.favImage = itemView.findViewById(R.id.dashmain_fav_image);
            this.favTitle = itemView.findViewById(R.id.dashmain_fav_titel);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFavoriteListener.onFavoriteClick(getAdapterPosition(),CURRENT_TYPE);
        }
    }

    public class KnowledgeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnFavoriteListener onFavoriteListener;
        private ImageView favImage;
        private TextView favTitle;


        public KnowledgeViewHolder(@NonNull View itemView, OnFavoriteListener onFavoriteListener) {
            super(itemView);
            this.onFavoriteListener = onFavoriteListener;
            this.favImage = itemView.findViewById(R.id.dashmain_fav_image);
            this.favTitle = itemView.findViewById(R.id.dashmain_fav_titel);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFavoriteListener.onFavoriteClick(getAdapterPosition(),CURRENT_TYPE);
        }
    }

    public interface OnFavoriteListener {
        void onFavoriteClick(int position, int CURRENT_TYPE);
    }
}
