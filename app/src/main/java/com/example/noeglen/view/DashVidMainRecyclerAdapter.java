package com.example.noeglen.view;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.HashMap;
import java.util.List;

//this was made using the recyclerView guide from codepath.com
public class DashVidMainRecyclerAdapter extends RecyclerView.Adapter<DashVidMainRecyclerAdapter.ViewHolder> {

   public class ViewHolder extends RecyclerView.ViewHolder {
      public TextView name;
      public TextView weekLabel;
      public ImageView image;
      public ImageView line1;
      public ImageView line2;
      public ImageView checkMark;

      public ViewHolder(View itemView) {
         super(itemView);
         name = itemView.findViewById(R.id.videoItemName);
         image = itemView.findViewById(R.id.videoItemImage);
         weekLabel = itemView.findViewById(R.id.weekLabel);
         checkMark = itemView.findViewById(R.id.seenImage);
         line1 = itemView.findViewById(R.id.line1);
         line2 = itemView.findViewById(R.id.line2);
      }
   }

   private List<VideoDTO> videoList;
   private ImageView imageView;
   private Context context;
   private MyCallBack myCallBack;
   private HashMap<String, Boolean> seenVideosList;

   public DashVidMainRecyclerAdapter(List<VideoDTO> videoList, Context context,HashMap<String, Boolean> seenVideosList ,MyCallBack myCallBack){
      this.videoList = videoList;
      this.context = context;
      this.myCallBack = myCallBack;
      this.seenVideosList = seenVideosList;
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
      TextView name = holder.name;
      TextView weekLabel = holder.weekLabel;
      imageView = holder.image;

      String wholeWeekName = videoItem.getWeek();
      String onlyWeekNumber = wholeWeekName.substring(0,5);
      name.setText(videoItem.getTitle());
      weekLabel.setText(onlyWeekNumber);

      //Removing first and last line
      if (videoItem == videoList.get(0)){
         line1.setVisibility(View.INVISIBLE);
      }else {
         line1.setVisibility(View.VISIBLE);
      }

      if (videoItem == videoList.get(videoList.size()-1)){
         line2.setVisibility(View.INVISIBLE);
      }else{
         line2.setVisibility(View.VISIBLE);
      }

      //showing checkMark if seen
      if (seenVideosList != null){ //checking that the list isn't null
         Boolean seen = seenVideosList.get(videoItem.getTitle());
         if (seen != null){ //checking that the value isn't null
            if (seen){ //checking if value is true
               holder.checkMark.setVisibility(View.VISIBLE);
            }else{
               holder.checkMark.setVisibility(View.INVISIBLE);
            }
         }else {
            holder.checkMark.setVisibility(View.INVISIBLE);
         }
      }else {
         holder.checkMark.setVisibility(View.INVISIBLE);
      }


      //Handling image clicklistener and loading image using glide
      Glide.with(context).load(videoItem.getImageUrl()).into(imageView);
      imageView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            myCallBack.onCallBack(videoItem);
         }
      });

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
