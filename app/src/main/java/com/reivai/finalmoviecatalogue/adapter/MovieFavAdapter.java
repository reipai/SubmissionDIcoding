package com.reivai.finalmoviecatalogue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.database.MovieFav;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.ViewHolder> {
    private List<MovieFav> movieFavs;

    public MovieFavAdapter(List<MovieFav> movieFavs) {
        this.movieFavs = movieFavs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieFav movie = movieFavs.get(position);

        holder.title.setText(movie.getTitle());
        holder.releaseDate.setText(movie.getRelease_date());
        holder.overview.setText(movie.getOverview());

        Glide.with(holder.itemView.getContext())
                .load(movie.getPosterPath())
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movieFavs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, releaseDate, overview;
        private ImageView poster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_fav);
            releaseDate = itemView.findViewById(R.id.releasedate_fav);
            overview = itemView.findViewById(R.id.overview_fav);
            poster = itemView.findViewById(R.id.imgFav);
        }
    }
}
