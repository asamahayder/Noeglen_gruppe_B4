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

import com.bumptech.glide.Glide;
import com.example.noeglen.R;
import com.example.noeglen.data.MyCallBack;
import com.example.noeglen.data.VideoDTO;

import java.util.List;

//this was made using the recyclerView guide from codepath.com
public class DashVidMainRecyclerAdapter extends RecyclerView.Adapter<DashVidMainRecyclerAdapter.ViewHolder> {

   public class ViewHolder extends RecyclerView.ViewHolder {
      public TextView name;
      public TextView weekLabel;
      public ImageView image;
      public ImageView line1;
      public ImageView line2;

      public ViewHolder(View itemView) {
         super(itemView);
         name = itemView.findViewById(R.id.videoItemName);
         image = itemView.findViewById(R.id.videoItemImage);
         weekLabel = itemView.findViewById(R.id.weekLabel);
         line1 = itemView.findViewById(R.id.line1);
         line2 = itemView.findViewById(R.id.line2);
      }
   }

   private List<VideoDTO> videoList;
   private ImageView imageView;
   private Context context;
   private MyCallBack myCallBack;

   public DashVidMainRecyclerAdapter(List<VideoDTO> videoList, Context context, MyCallBack myCallBack){
      this.videoList = videoList;
      this.context = context;
      this.myCallBack = myCallBack;
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
      final VideoDTO videoItem = videoList.get(position);
      ImageView line1 = holder.line1;
      ImageView line2 = holder.line2;

      //Removing first and last line
      /*if (position == 0){
         line1.setVisibility(View.INVISIBLE);
      }*/
      /*
      if (videoItem == videoList.get(videoList.size()-1)){
         holder.line2.setVisibility(View.INVISIBLE);
      }*/

      String wholeWeekName = videoItem.getWeek();
      String onlyWeekNumber = wholeWeekName.substring(0,5);
      holder.weekLabel.setText(onlyWeekNumber);


      TextView name = holder.name;
      imageView = holder.image;
      imageView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            myCallBack.onCallBack(videoItem);
         }
      });
      name.setText(videoItem.getTitle());
      Glide
              .with(context)
              .load(videoItem.getImageUrl())
              .into(imageView);
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
