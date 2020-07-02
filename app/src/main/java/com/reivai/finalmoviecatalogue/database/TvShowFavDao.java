package com.reivai.finalmoviecatalogue.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TvShowFavDao {

    @Insert
    void addData(TvShowFav tvShowFav);

    @Query("SELECT * FROM tvshow_fav")
    List<TvShowFav> getFavData();

    @Query("SELECT EXISTS (SELECT 1 FROM tvshow_fav WHERE id=:id)")
    int isFav(int id);

    @Delete
    void delete(TvShowFav tvShowFav);
}
