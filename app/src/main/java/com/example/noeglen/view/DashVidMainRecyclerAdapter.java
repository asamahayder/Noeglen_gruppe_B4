package com.example.noeglen.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noeglen.R;
import com.example.noeglen.data.VideoDTO;

import java.util.List;

public class DashVidMainRecyclerAdapter extends RecyclerView.Adapter<DashVidMainRecyclerAdapter.ViewHolder>{

   private List<VideoDTO> videoList;
   private Context mContext;

   public DashVidMainRecyclerAdapter(List<VideoDTO> videoList, Context mContext) {
      this.videoList = videoList;
      this.mContext = mContext;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashvidmain_recyclerview,parent,false);
      ViewHolder holder = new ViewHolder(view);
      return holder;
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      String imgURL = "https://img.youtube.com/vi/" + videoList.get(position).getVideoID() + "/3.jpg";
      Glide.with(mContext)
              .asBitmap()
              .load(imgURL)
              .into(holder.iVideo);

      holder.tVideoTitle.setText(videoList.get(position).getTitle());
      holder.tProgressTitle.setText("TEST");

      Drawable bak = holder.iLineTop.getDrawable();
      bak.setColorFilter(new PorterDuffColorFilter(Color.parseColor("black"), PorterDuff.Mode.MULTIPLY));

   }

   @Override
   public int getItemCount() {
      return videoList.size() ;
   }

   public class ViewHolder extends RecyclerView.ViewHolder {

      public TextView tProgressTitle, tVideoTitle;
      public ImageView iVideo, iLineTop, iLineBottom, iCircle;
      public ConstraintLayout conLayout;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);

         tProgressTitle = itemView.findViewById(R.id.dashVidMain_customLayoutList_progressTitle);
         tVideoTitle = itemView.findViewById(R.id.dashVidMain_customLayoutList_tVideoTitle);

         iVideo = itemView.findViewById(R.id.dashVidMain_customLayoutList_iVIdeo);
         iLineTop = itemView.findViewById(R.id.dashVidMain_customLayoutList_iTopLine);
         iLineBottom = itemView.findViewById(R.id.dashVidMain_customLayoutList_iBottomLine);
         iCircle = itemView.findViewById(R.id.dashVidMain_customLayoutList_iCircle);
      }
   }
}