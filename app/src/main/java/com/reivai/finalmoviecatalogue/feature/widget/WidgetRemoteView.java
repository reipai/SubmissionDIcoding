package com.reivai.finalmoviecatalogue.feature.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.database.MovieFav;

import java.util.List;

import static com.reivai.finalmoviecatalogue.MainActivity.movieFavDb;

public class WidgetRemoteView implements RemoteViewsService.RemoteViewsFactory {

    private List<MovieFav> movieFavs;
    private final Context con;

    public WidgetRemoteView(Context context) {
        con = context;
    }
    @Override
    public void onCreate() {
        movieFavs = movieFavDb.movieFavDao().getFavData();
    }

    @Override
    public void onDataSetChanged() {
        movieFavs = movieFavDb.movieFavDao().getFavData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieFavs.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(con.getPackageName(), R.layout.item_widget);

        if (movieFavs.size() > 0) {
            MovieFav movies = movieFavs.get(position);

            try {
                Bitmap bitmap = Glide.with(con)
                        .asBitmap()
                        .load(movies.getPosterPath())
                        .submit(512, 512)
                        .get();
                remoteViews.setImageViewBitmap(R.id.imgView, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            bundle.putInt(WidgetProvider.EXTRA_ITEM, position);
            Intent i = new Intent();
            i.putExtras(bundle);
            remoteViews.setOnClickFillInIntent(R.id.imgView, i);

            return remoteViews;
        } else {
            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
