package com.reivai.favmovie;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.reivai.favmovie.DbContract.FavMovieColoms.OVERVIEW;
import static com.reivai.favmovie.DbContract.FavMovieColoms.POSTER_PATH;
import static com.reivai.favmovie.DbContract.FavMovieColoms.RELEASE_DATE;
import static com.reivai.favmovie.DbContract.FavMovieColoms.TITLE;

public class MapHelp {

    public static ArrayList<FavMovieItem> favMovieItemArrayList(Cursor cursor) {
        ArrayList<FavMovieItem> favMovieItems = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id =  cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String releasedate = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String posterpath = cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH));

            favMovieItems.add(new FavMovieItem(id, title, releasedate, overview, posterpath));
        }
        return favMovieItems;
    }
}
