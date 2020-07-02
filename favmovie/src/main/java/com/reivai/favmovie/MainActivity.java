package com.reivai.favmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.reivai.favmovie.DbContract.FavMovieColoms.CONTENT_URI;
import static com.reivai.favmovie.MapHelp.favMovieItemArrayList;

public class MainActivity extends AppCompatActivity implements CallbackFav {

    private FavAdapter adapter;
    private DataObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Favorite Movie Catalogue");
        RecyclerView rvFav = findViewById(R.id.rv_favourite);
        adapter = new FavAdapter(this);

        rvFav.setLayoutManager(new LinearLayoutManager(this));
        rvFav.setHasFixedSize(true);
        rvFav.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        observer = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, observer);
        new getData(this, this).execute();
    }

    @Override
    public void postExecute(Cursor note) {

        ArrayList<FavMovieItem> listFavMovie = favMovieItemArrayList(note);
        if (listFavMovie.size() > 0) {
            adapter.setFavMovieItemArrayList(listFavMovie);
        } else {
            Toast.makeText(this, "Tidak ada data", Toast.LENGTH_SHORT).show();
            adapter.setFavMovieItemArrayList(new ArrayList<FavMovieItem>());
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> contextWeakReference;
        private final WeakReference<CallbackFav> callbackFavWeakReference;

        private getData(Context con, CallbackFav callbackFav){
            contextWeakReference = new WeakReference<>(con);
            callbackFavWeakReference = new WeakReference<>(callbackFav);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return contextWeakReference.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            callbackFavWeakReference.get().postExecute(data);
        }
    }

    static class DataObserver extends ContentObserver {
        Context con;

        public DataObserver(Handler handler, Context con) {
            super(handler);
            this.con = con;
        }

        @Override
        public void onChange(boolean changeSelf) {
            super.onChange(changeSelf);
            new getData(con, (MainActivity) con).execute();
        }
    }
}
