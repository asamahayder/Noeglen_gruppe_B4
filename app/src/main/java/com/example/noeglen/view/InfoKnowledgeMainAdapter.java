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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.noeglen.R;
import com.example.noeglen.data.KnowledgeDTO;

import java.util.List;

/**
 * Adapteren der loader recyclerviewet af artikel objekter
 */

public class InfoKnowledgeMainAdapter extends RecyclerView.Adapter<InfoKnowledgeMainAdapter.ViewHolder> {

    /**
     * @variable articles
     * listen af artikler der skal indsættes i recyclerviewet
     * @variable onArticleListener
     * interfacet der håndterer onClick på hver artikel i recyclerviewet
     * @variable context
     * en context variabel til den nuværende view
     */
    private List<KnowledgeDTO> articles;
    private OnArticleListener onArticleListener;
    private Context context;

    public InfoKnowledgeMainAdapter(Context context, List<KnowledgeDTO> articles, OnArticleListener onArticleListener) {
        this.context = context;
        this.articles = articles;
        this.onArticleListener = onArticleListener;
    }

    /**
     * Opretter den ViewHolder der indeholder alle textviews osv.
     *
     * @param parent
     *
     * @param viewType
     * Dette er variabel der holder styr på hvilket view man er i
     *
     * @return
     * returnerer den nye viewholder som skal bruges
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_infomainknowledgearticles,parent,false);
        return new ViewHolder(view,onArticleListener);
    }

    /**
     * Indsætter alt på recyclerviewet ved brug at viewholderen der bliver skabt før
     *
     * @param holder
     * er den viewholder man har lavet til denne adapter, den har så alle textviews, imageviews som skal indsættes i
     *
     * @param position
     * position bliver brugt til at holde styr på hvor man er kommet til i listen af artikler
     */

    @Override
    public void onBindViewHolder(@NonNull InfoKnowledgeMainAdapter.ViewHolder holder, int position) {
        try {
            //Indsætter teksten på textviewet
            holder.tTitle.setText(articles.get(position).getTitle());

            //Loader billedet på imageviewet med glide biblioteket
            Glide
                    .with(context)
                    .load(articles.get(position).getImage())
                    .into(holder.image);


        } catch (NullPointerException ignored){
        }
    }

    /**
     * Finder ud af hvor mange elementer der findes i listen af artikler
     * @return
     * returnerer hvor mange der var i listen
     */

    @Override
    public int getItemCount() {
        if (articles == null){
            return 0;
        }
        else {
            return articles.size();
        }
    }

    /**
     * ViewHolder klassen er den som finder hvilke textviews og imageviews skal indsættes i
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * @variable onArticleListener
         * interfacet der håndterer onclick på hver artikel element i listen
         * @variable tTitle
         * titel textviewet der skal bruges i onBindView
         * @variable image
         * imageviewet som er baggrunden af artiklen, der bliver brugt i onBindView
         */

        OnArticleListener onArticleListener;
        TextView tTitle;
        ImageView image;

        ViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
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
