package com.reivai.finalmoviecatalogue.feature.movie;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.adapter.MovieFavAdapter;
import com.reivai.finalmoviecatalogue.database.MovieFav;
import com.reivai.finalmoviecatalogue.provider.FavMovieCallback;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.reivai.finalmoviecatalogue.MainActivity.movieFavDb;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.CONTENT_URI;

public class MovieFavActivity extends AppCompatActivity {

    private RecyclerView rvFavMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(R.string.favorite_movie);

        rvFavMovie = findViewById(R.id.rv_fav);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(this));

        getFavMovie();
    }

    private void getFavMovie() {
        List<MovieFav> movieFavs = movieFavDb.movieFavDao().getFavData();
        MovieFavAdapter adapter = new MovieFavAdapter(movieFavs);
        rvFavMovie.setAdapter(adapter);
    }

    public static class DataObserver extends ContentObserver {
        Context con;

        public DataObserver(Handler handler, Context con) {
            super(handler);
            this.con = con;
        }

        @Override
        public void onChange(boolean changeSelf) {
            super.onChange(changeSelf);
            new MovieFavAsync(con, (FavMovieCallback) con).execute();
        }
    }

    private static class MovieFavAsync extends AsyncTask<Void, Void, Cursor> {

        private WeakReference<Context> contextWeakReference;

        private MovieFavAsync(Context con, FavMovieCallback callback) {
            contextWeakReference = new WeakReference<>(con);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context con = contextWeakReference.get();
            return con.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favMovies) {

        }
    }
}
