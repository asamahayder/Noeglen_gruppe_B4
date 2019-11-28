package com.example.noeglen.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noeglen.R;

public class DashVidMainRecyclerAdapter extends RecyclerView.Adapter<DashVidMainRecyclerAdapter.ViewHolder> {

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return null;
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

   }

   @Override
   public int getItemCount() {
      return 0;
   }

   public class ViewHolder extends RecyclerView.ViewHolder {

      private TextView tProgressTitle, tVideoTitle;
      private ImageView iVideo, iLineTop, iLineBottom, iCircle;
      private ConstraintLayout conLayout;

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