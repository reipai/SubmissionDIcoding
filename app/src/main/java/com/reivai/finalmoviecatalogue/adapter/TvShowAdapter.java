package com.reivai.finalmoviecatalogue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.database.TvShowFav;
import com.reivai.finalmoviecatalogue.model.tvshow.TvShow;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private ArrayList<TvShow> dataTvshow;
    private OnItemClickCallback onItemClickCallback;

    public void setTvShowData(ArrayList<TvShow> items) {
        dataTvshow.clear();
        dataTvshow.addAll(items);
        notifyDataSetChanged();
    }

    public TvShowAdapter(ArrayList<TvShow> tvShowArrayList) {
        this.dataTvshow = tvShowArrayList;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tvshow, viewGroup, false);
        return new TvShowViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowViewHolder holder, int position) {
        TvShow tvShow = dataTvshow.get(position);
        holder.title.setText(tvShow.getTitle());
        holder.releasedate.setText(tvShow.getRelease_date());
        holder.overview.setText(tvShow.getOverview());

        Glide.with(holder.itemView.getContext())
                .load(tvShow.getPhoto())
                .into(holder.imgposter);

        holder.imgposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(dataTvshow.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataTvshow.size();
    }

    static class TvShowViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgposter;
        private TextView title, releasedate, overview;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgposter = itemView.findViewById(R.id.imgtvshow);
            title = itemView.findViewById(R.id.title_tvshow);
            releasedate = itemView.findViewById(R.id.releasedate_tvshow);
            overview = itemView.findViewById(R.id.overview_tvshow);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShow tvShow);
    }
}
