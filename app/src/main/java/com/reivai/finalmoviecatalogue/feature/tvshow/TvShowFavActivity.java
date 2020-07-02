package com.reivai.finalmoviecatalogue.feature.tvshow;

import android.os.Bundle;

import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.adapter.TvShowFavAdapter;
import com.reivai.finalmoviecatalogue.database.TvShowFav;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.reivai.finalmoviecatalogue.MainActivity.tvShowFavDb;

public class TvShowFavActivity extends AppCompatActivity {
    private RecyclerView rvFavTvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(R.string.favorite_tv_show);

        rvFavTvshow = findViewById(R.id.rv_fav);
        rvFavTvshow.setLayoutManager(new LinearLayoutManager(this));

        getFavTvShow();
    }

    private void getFavTvShow() {
        List<TvShowFav> tvShowFavs = tvShowFavDb.tvShowFavDao().getFavData();

        TvShowFavAdapter adapter = new TvShowFavAdapter(tvShowFavs);
        rvFavTvshow.setAdapter(adapter);
    }
}
