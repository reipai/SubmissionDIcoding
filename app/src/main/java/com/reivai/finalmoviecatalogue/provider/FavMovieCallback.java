package com.reivai.finalmoviecatalogue.provider;

import java.util.ArrayList;

public interface FavMovieCallback {
    void preExecute();

    void postExecute(ArrayList<FavMovie> favMovies);
}
