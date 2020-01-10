package com.example.noeglen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noeglen.R;
import com.example.noeglen.data.VideoDTO;

import java.util.List;

//this was made using the recyclerView guide from codepath.com
public class DashVidMainRecyclerAdapter extends RecyclerView.Adapter<DashVidMainRecyclerAdapter.ViewHolder> {

   public class ViewHolder extends RecyclerView.ViewHolder {
      public TextView name;
      public ImageView image;

      public ViewHolder(View itemView) {
         super(itemView);
         name = itemView.findViewById(R.id.videoItemName);
         image = itemView.findViewById(R.id.videoItemImage);
      }
   }

   private List<VideoDTO> videoList;
   private ImageView imageView;

   public DashVidMainRecyclerAdapter(List<VideoDTO> videoList){
      this.videoList = videoList;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      Context context = parent.getContext();
      LayoutInflater inflater = LayoutInflater.from(context);

      View videoItemView = inflater.inflate(R.layout.video_item_layout, parent, false);

      ViewHolder viewHolder = new ViewHolder(videoItemView);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      VideoDTO videoItem = videoList.get(position);

      TextView name = holder.name;
      imageView = holder.image;
      name.setText(videoItem.getTitle());
      new AsyncTaskGetImage(this).execute(videoItem.getImageUrl());
   }

   @Override
   public int getItemCount() {
      return videoList.size();
   }

   public void setImage(Bitmap bitmap){
      imageView.setImageBitmap(bitmap);
   }

   public void showErrorMessage(){
      //TODO maybe show some error of a kind to the user
   }
}
