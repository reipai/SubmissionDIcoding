package com.reivai.finalmoviecatalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reivai.finalmoviecatalogue.database.FavoriteDb;
import com.reivai.finalmoviecatalogue.feature.FavoriteActivity;
import com.reivai.finalmoviecatalogue.feature.movie.MovieFragment;
import com.reivai.finalmoviecatalogue.feature.reminder.ActivityReminder;
import com.reivai.finalmoviecatalogue.feature.tvshow.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    public static FavoriteDb movieFavDb, tvShowFavDb;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment frag;

                    switch (item.getItemId()) {
                        case R.id.nav_movie:
                            frag = new MovieFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.con_layout, frag, frag.getClass().getSimpleName())
                                    .commit();
                            return true;

                        case R.id.nav_tvshow:
                            frag = new TvShowFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.con_layout, frag, frag.getClass().getSimpleName())
                                    .commit();
                            return true;
                    }

                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav_view = findViewById(R.id.bot_nav);
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            nav_view.setSelectedItemId(R.id.nav_movie);
        }

        movieFavDb = Room.databaseBuilder(getApplicationContext(), FavoriteDb.class, "movie_fav").allowMainThreadQueries().build();

        tvShowFavDb = Room.databaseBuilder(getApplicationContext(), FavoriteDb.class, "tvshow_fav").allowMainThreadQueries().build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lang_setting:
                Intent changeLang = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changeLang);
                break;

            case R.id.reminder_setting:
                Intent remind = new Intent(this, ActivityReminder.class);
                startActivity(remind);
                break;

            case R.id.favorite:
                Intent favMovie = new Intent(this, FavoriteActivity.class);
                startActivity(favMovie);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
