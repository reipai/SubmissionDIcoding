package com.reivai.favmovie;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private final ArrayList<FavMovieItem> favMovieItemArrayList = new ArrayList<>();
    private final Activity activity;

    public FavAdapter(Activity activity){
        this.activity = activity;
    }

    public ArrayList<FavMovieItem> getFavMovieItemArrayList() {
        return favMovieItemArrayList;
    }

    public void setFavMovieItemArrayList(ArrayList<FavMovieItem> favMovieItemArrayList) {
        this.favMovieItemArrayList.clear();
        this.favMovieItemArrayList.addAll(favMovieItemArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.title.setText(getFavMovieItemArrayList().get(position).getTitle());
        holder.releaseDate.setText(getFavMovieItemArrayList().get(position).getReleasedate());
        holder.overview.setText(getFavMovieItemArrayList().get(position).getOverview());

        Glide.with(holder.itemView.getContext())
                .load(getFavMovieItemArrayList().get(position).getPosterpath())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.posterPath);
    }

    @Override
    public int getItemCount() {
        return favMovieItemArrayList.size();
    }


    public class FavViewHolder extends RecyclerView.ViewHolder {
        final TextView title, releaseDate, overview;
        ImageView posterPath;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_fav);
            releaseDate = itemView.findViewById(R.id.releasedate_fav);
            overview = itemView.findViewById(R.id.overview_fav);
            posterPath = itemView.findViewById(R.id.imgFav);
        }
    }
}
