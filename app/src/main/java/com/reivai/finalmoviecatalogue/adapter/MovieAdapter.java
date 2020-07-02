package com.reivai.finalmoviecatalogue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.model.movie.Movie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final ArrayList<Movie> dataMovie;
    private OnItemClickCallback onItemClickCallback;

    public void setMovieData(ArrayList<Movie> items) {
        dataMovie.clear();
        dataMovie.addAll(items);
        notifyDataSetChanged();
    }

    public MovieAdapter(ArrayList<Movie> movieArrayList) {
        this.dataMovie = movieArrayList;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        Movie movie = dataMovie.get(position);
        holder.title.setText(movie.getTitle());
        holder.releasedate.setText(movie.getRelease_date());
        holder.overview.setText(movie.getOverview());

        Glide.with(holder.itemView.getContext())
                .load(movie.getPhoto())
                .into(holder.imgposter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(dataMovie.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataMovie.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgposter;
        private TextView title, releasedate, overview;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgposter = itemView.findViewById(R.id.imgmovie);
            title = itemView.findViewById(R.id.title_movie);
            releasedate = itemView.findViewById(R.id.releasedate_movie);
            overview = itemView.findViewById(R.id.overview_movie);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie movie);
    }
}
