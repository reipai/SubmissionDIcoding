package com.reivai.finalmoviecatalogue.feature.tvshow;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.adapter.MovieFavAdapter;
import com.reivai.finalmoviecatalogue.adapter.TvShowFavAdapter;
import com.reivai.finalmoviecatalogue.database.MovieFav;
import com.reivai.finalmoviecatalogue.database.TvShowFav;
import com.reivai.finalmoviecatalogue.feature.movie.MovieFavFragment;
import com.reivai.finalmoviecatalogue.provider.FavMovieCallback;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.reivai.finalmoviecatalogue.MainActivity.movieFavDb;
import static com.reivai.finalmoviecatalogue.MainActivity.tvShowFavDb;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.CONTENT_URI;

public class TvShowFavFragment extends Fragment {

    private RecyclerView rvFavMovie;

    public TvShowFavFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_favorite, container, false);

        rvFavMovie = v.findViewById(R.id.rv_fav);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<TvShowFav> tvShowFavs = tvShowFavDb.tvShowFavDao().getFavData();
        TvShowFavAdapter adapter = new TvShowFavAdapter(tvShowFavs);
        rvFavMovie.setAdapter(adapter);

        return v;
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
            new TvShowFavFragment.TvShowFavAsync(con, (FavMovieCallback) con).execute();
        }
    }

    private static class TvShowFavAsync extends AsyncTask<Void, Void, Cursor> {

        private WeakReference<Context> contextWeakReference;

        private TvShowFavAsync(Context con, FavMovieCallback callback) {
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
