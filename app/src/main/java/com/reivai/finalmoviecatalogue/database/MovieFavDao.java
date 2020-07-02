package com.reivai.finalmoviecatalogue.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MovieFavDao {

    @Insert
    void addData(MovieFav movieFav);

    @Query("SELECT * FROM movie_fav")
    List<MovieFav> getFavData();

    @Query("SELECT EXISTS (SELECT 1 FROM movie_fav WHERE id=:id)")
    int isFav(int id);

    @Delete
    void delete(MovieFav movieFav);
}
