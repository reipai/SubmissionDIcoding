package com.reivai.finalmoviecatalogue.feature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.adapter.ViewPagerAdapter;
import com.reivai.finalmoviecatalogue.feature.movie.MovieFavFragment;
import com.reivai.finalmoviecatalogue.feature.tvshow.TvShowFavFragment;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite2);

        TabLayout tabs = findViewById(R.id.tabs);
        ViewPager pager = findViewById(R.id.viewPager);

        String movie = getResources().getString(R.string.title_movie);
        String tvshow = getResources().getString(R.string.title_tvshow);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFavFragment(), movie);
        adapter.addFragment(new TvShowFavFragment(), tvshow);

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
    }
}
