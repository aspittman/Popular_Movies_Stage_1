package com.affinityapps.popularmoviesstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Gallery;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieItemsViewHolder> {

    private ArrayList<Movie> movieArrayList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
        this.context = context;
    }

    public class MovieItemsViewHolder extends RecyclerView.ViewHolder {

        private ArrayList<Movie> movieArrayList;
        private ImageView movieImage;

        public MovieItemsViewHolder(@NonNull View itemView, ArrayList<Movie> movieArrayList) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movie_image_icon);
            this.movieArrayList = movieArrayList;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MovieItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items, parent, false);
        MovieItemsViewHolder galleryItemsViewHolder = new MovieItemsViewHolder(view, movieArrayList);
        return galleryItemsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemsViewHolder holder, int position) {

        Movie movie = movieArrayList.get(position);
        String movieImageUrl = movie.getMovieImageUrl();
        Picasso.get().load(movieImageUrl).
                placeholder(R.drawable.ic_local_movies_black_24dp).
                error(R.drawable.ic_block_black_24dp).
                fit().
                centerInside().
                into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }


}
