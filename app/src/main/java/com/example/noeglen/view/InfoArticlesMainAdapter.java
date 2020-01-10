package com.example.noeglen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noeglen.R;
import com.example.noeglen.data.ArticleDTO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class InfoArticlesMainAdapter extends RecyclerView.Adapter<InfoArticlesMainAdapter.ViewHolder> {

    private List<ArticleDTO> articles;
    private OnArticleListener onArticleListener;
    private ImageView image;
    private Context context;

    private static final String TAG = "INFO_MAIN_ARTICLES";

    public InfoArticlesMainAdapter(Context context, List<ArticleDTO> articles, OnArticleListener onArticleListener) {
        this.context = context;
        this.articles = articles;
        this.onArticleListener = onArticleListener;
        Log.d(TAG, "InfoArticlesMainAdapter: CONSTRUCTOR - articles size: " + articles.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_infomainarticles,parent);
        return new ViewHolder(view,onArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoArticlesMainAdapter.ViewHolder holder, int position) {
        try {
            holder.tTitle.setText(articles.get(position).getTitle());
            holder.tSubtext.setText(articles.get(position).getHeader());
            image = holder.image;

            new AsyncTaskGetImage(this).execute(articles.get(position).getImage());

        } catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: NULLPOINTER EXCEPTION");
        }
    }

    @Override
    public int getItemCount() {
        if (articles == null){
            return 0;
        }
        else {
            return articles.size();
        }
    }

    public void setImage(Bitmap bitmap){
        image.setImageBitmap(bitmap);
        Log.d(TAG, "setImage: SETTING IMAGE ON RECYCLERVIEW");
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnArticleListener onArticleListener;
        TextView tTitle, tSubtext;
        ImageView image;

        public ViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
            super(itemView);
            this.onArticleListener = onArticleListener;
            this.tTitle = itemView.findViewById(R.id.infoarticlesmain_recyclerview_title);
            this.tSubtext = itemView.findViewById(R.id.infoarticlesmain_recyclerview_subtext);
            this.image = itemView.findViewById(R.id.infoarticlesmain_recyclerview_image);

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

    private class AsyncTaskGetImage extends AsyncTask<String, String, Exception>{

        private final WeakReference<InfoArticlesMainAdapter> activityRef;
        private Bitmap image;

        public AsyncTaskGetImage(InfoArticlesMainAdapter activityRef) {
            this.activityRef = new WeakReference<>(activityRef);
        }

        @Override
        protected Exception doInBackground(String... strings) {
            try {
                image = getImageFromWeb(strings[9]);
            } catch (Exception e){
                return e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Exception e) {
            if (activityRef.get() != null) {
                if (e != null) {
                    Log.d(TAG, "onPostExecute: ERROR");
                } else {
                    activityRef.get().setImage(image);
                }
            }
        }

        public Bitmap getImageFromWeb(String url){
            Bitmap image = null;
            try {
                URL imageURL = new URL(url);
                URLConnection connection = imageURL.openConnection();
                connection.connect();
                InputStream inputStream =  connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                image = BitmapFactory.decodeStream(bufferedInputStream);
                bufferedInputStream.close();
                inputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return image;
        }
    }
}
