package com.reivai.finalmoviecatalogue.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MovieFav.class, TvShowFav.class}, version = 1)
public abstract class FavoriteDb extends RoomDatabase {
    public abstract MovieFavDao movieFavDao();

    public abstract TvShowFavDao tvShowFavDao();
}
