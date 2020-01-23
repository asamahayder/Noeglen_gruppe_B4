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

/**
 * Adapteren der indsætter favoritter på dashmain fragmentet
 */

public class DashMainRecyclerAdapter extends RecyclerView.Adapter<DashMainRecyclerAdapter.ViewHolder> {

    /**
     * @variable favorites
     * Listen af favoritter der bliver brugt i adapteren til at blive indsat
     * @variable context
     * Selve contexten vi er i, som er det view der skal bruges til at indsætte eller opdatere ting i
     * @variable onFavoriteListener
     * Er et interface der håndterer onclick hændelser
     */
    private List<FavoriteDTO> favorites;

    private Context context;
    private OnFavoriteListener onFavoriteListener;

    public DashMainRecyclerAdapter(List<FavoriteDTO> favorites, Context context, OnFavoriteListener onFavoriteListener) {
        this.favorites = favorites;
        this.context = context;
        this.onFavoriteListener = onFavoriteListener;
    }

    /**
     * Laver det nye view som skal indsættes i recyclerviewet
     *
     * @param parent
     *
     *
     * @param viewType
     * Den type af view man er i lige nu. Hvis man har forskellige views der skal indsættes bliver denne brugt til at håndtere hvilket view skal bruges
     *
     * @return
     * Returner ViewHolderen der indeholder alle variablerne fra layoutet som skal indsættes i
     */

    @Override
    public DashMainRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_dashmain,parent,false);
        return new ViewHolder(view,onFavoriteListener);
    }

    /**
     * Dette er metoden der indsætter i recyclerviewet, hvor den itererer igennem alle objekter der ligger i favorites
     *
     * @param holder
     * dette er ViewHolderen som kaldes for at hente textViews og ImageViews der skal indsættes i
     * @param position
     * position er hvilken position man er i selve listen hos favorites. Det er noget adapteren holder styr på selv
     */

    @Override
    public void onBindViewHolder(@NonNull DashMainRecyclerAdapter.ViewHolder holder, int position) {
        try {

            holder.favTitle.setText(favorites.get(position).getTitle());
            // Glide bliver brugt til at loade billedet hos favoritten
            Glide
                    .with(context)
                    .load(favorites.get(position).getIamgeURL())
                    .into(holder.favImage);

            // Adder et lille icon ovenfor billedet alt efter hvilken type af favorit det er (video, øvelse eller artikel)
            switch (favorites.get(position).getCURRENT_TYPE()){
                case 1:
                    holder.iconImage.setBackgroundResource(R.drawable.ic_video_icon);
                    holder.iconCircle.setBackgroundResource(R.drawable.circle_secondarylight);
                    break;
                case 2:
                    holder.iconImage.setBackgroundResource(R.drawable.ic_exercise);
                    holder.iconCircle.setBackgroundResource(R.drawable.circle_secondarynormal);
                    break;
                case 3:
                    holder.iconImage.setBackgroundResource(R.drawable.ic_article);
                    holder.iconCircle.setBackgroundResource(R.drawable.circle_white);
                    break;
            }

        } catch (NullPointerException ignored){
        }
    }

    /**
     * Metoden tjekker hvor mange elementer der er i favorit listen
     *
     * @return
     * returner hvor mange der var i selve listen
     */
    @Override
    public int getItemCount() {
        if (favorites != null){
            return favorites.size();
        }
        else {
            return 0;
        }
    }

    /**
     * ViewHolder klassen definerer alle textviews, imageviews og om der skal være en onclick metode eller ikke på viewet
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * @variable onFavoriteListener
         * Interfacet der håndterer om der var trykket på en favorit
         * @variable favImage
         * Dette er selve imageview baggrunden af favoritten der bliver loadet ind i recyclerviewet
         * @variable iconImage
         * Dette er icon imageviewet der skal loades ind i på recyclerviewet
         * @variable iconCircle
         * Også en del af iconet som skal loades på viewet
         * @variable favTitle
         * Textviewet hos titlen der skal loades ind på viewet
         */
        private OnFavoriteListener onFavoriteListener;
        private ImageView favImage, iconImage, iconCircle;
        private TextView favTitle;


        ViewHolder(@NonNull View itemView, OnFavoriteListener onFavoriteListener) {
            super(itemView);
            this.onFavoriteListener = onFavoriteListener;
            this.favImage = itemView.findViewById(R.id.dashmain_fav_image);
            this.favTitle = itemView.findViewById(R.id.dashmain_fav_titel);
            this.iconImage = itemView.findViewById(R.id.dashmain_fav_icontype);
            this.iconCircle = itemView.findViewById(R.id.dashmain_fav_iconcircle);

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
