package com.example.noeglen.view;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.noeglen.data.WeekDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//this was made using the recyclerView guide from codepath.com
public class DashVidMainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   class ViewHolder extends RecyclerView.ViewHolder {
      TextView name;
      TextView weekLabel;
      ImageView image;
      ImageView line1;
      ImageView line2;
      ImageView checkMark;

      ViewHolder(View itemView) {
         super(itemView);
         name = itemView.findViewById(R.id.videoItemName);
         image = itemView.findViewById(R.id.videoItemImage);
         weekLabel = itemView.findViewById(R.id.weekLabel);
         checkMark = itemView.findViewById(R.id.seenImage);
         line1 = itemView.findViewById(R.id.line1);
         line2 = itemView.findViewById(R.id.line2);
      }
   }

   class ViewHolderText extends RecyclerView.ViewHolder {

      TextView weekName;

      ViewHolderText(View itemView) {
         super(itemView);
         weekName = itemView.findViewById(R.id.weekTitle);
      }
   }

   private List<Object> objectList;
   private ArrayList<Integer> weekPositionList;
   private ImageView imageView;
   private Context context;
   private MyCallBack myCallBack;
   private HashMap<String, Boolean> seenVideosList;

   public DashVidMainRecyclerAdapter(List<Object> objectList, Context context, HashMap<String, Boolean> seenVideosList , ArrayList<Integer> weekPositionList, MyCallBack myCallBack){
      this.objectList = objectList;
      this.context = context;
      this.myCallBack = myCallBack;
      this.seenVideosList = seenVideosList;
      this.weekPositionList = weekPositionList;
   }

   @NonNull
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      Context context = parent.getContext();
      LayoutInflater inflater = LayoutInflater.from(context);
      View textView = inflater.inflate(R.layout.week_title_layout, parent,false);
      View videoItemView = inflater.inflate(R.layout.video_item_layout, parent, false);

      if (viewType == 0) {
         return new ViewHolderText(textView);
      } else {
         return new ViewHolder(videoItemView);
      }
   }

   @Override
   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      switch (holder.getItemViewType()){
         case 0:
            ViewHolderText viewHolderText = (ViewHolderText) holder;
            handleTextHolder(viewHolderText, position);
            break;
         default:
            ViewHolder viewHolder = (ViewHolder) holder;
            handleVideoHolder(viewHolder, position);
            break;
      }
   }

   @Override
   public int getItemViewType(int position) {
      if (weekPositionList.contains(position)){
         return 0;
      }else{
         return 1;
      }
   }

   public void handleTextHolder(@NonNull ViewHolderText holder, int position){
      WeekDTO weekItem = (WeekDTO) objectList.get(position);
      TextView weekLabel = holder.weekName;
      weekLabel.setText(weekItem.getWeekTitle());

   }


   public void handleVideoHolder(@NonNull ViewHolder holder, int position) {
      final VideoDTO videoItem = (VideoDTO) objectList.get(position);
      ImageView line1 = holder.line1;
      ImageView line2 = holder.line2;
      TextView name = holder.name;
      TextView weekLabel = holder.weekLabel;
      imageView = holder.image;

      String wholeWeekName = videoItem.getWeek();
      String onlyWeekNumber = wholeWeekName.substring(0,5);
      name.setText(videoItem.getTitle());
      weekLabel.setText(onlyWeekNumber);

      //Handling progression line visibility
      line1.setVisibility(View.VISIBLE);
      line2.setVisibility(View.VISIBLE);
      for (Integer weekPosition : weekPositionList) {
         if (weekPosition+1 != weekPositionList.size()+1 && weekPosition-1 != -1){ //This avoids index out of bounds error
            if (videoItem == objectList.get(weekPosition+1)){
               line1.setVisibility(View.INVISIBLE);
            }
            if (videoItem == objectList.get(weekPosition-1)){
               line2.setVisibility(View.INVISIBLE);
            }
         }else if (weekPosition.equals(weekPositionList.get(0))){ //handling first line
            if (videoItem == objectList.get(weekPosition+1)) line1.setVisibility(View.INVISIBLE);
         }
      }
      if (videoItem == objectList.get(objectList.size()-1)){ //handling last line
               line2.setVisibility(View.INVISIBLE);
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
      return objectList.size();
   }

   public void setImage(Bitmap bitmap){
      imageView.setImageBitmap(bitmap);
   }

   public void showErrorMessage(){
      //TODO maybe show some error of a kind to the user
   }
}
