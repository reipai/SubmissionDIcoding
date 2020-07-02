package com.reivai.finalmoviecatalogue.feature.movie;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.database.MovieFav;
import com.reivai.finalmoviecatalogue.feature.widget.WidgetProvider;
import com.reivai.finalmoviecatalogue.model.movie.Movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static com.reivai.finalmoviecatalogue.MainActivity.movieFavDb;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.CONTENT_URI;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.ID;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.OVERVIEW;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.POSTER_PATH;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.RELEASE_DATE;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.TITLE;


public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private int id;
    private TextView title, releasedate, vote_avg, popularity, overview;
    private ImageView imgposter, imgbackdrop;
    private String judul, tglRilis, voteavg, populer, deskripsi, poster, banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_movie);
        setContentView(R.layout.activity_detail_movie);

        bindData();
        getData();
        setData();
    }

    private void bindData() {
        title = findViewById(R.id.title_movie);
        releasedate = findViewById(R.id.releasedate_movie);
        vote_avg = findViewById(R.id.vote_movie);
        popularity = findViewById(R.id.popuarity_movie);
        overview = findViewById(R.id.overview_movie);
        imgposter = findViewById(R.id.imgmovie);
        imgbackdrop = findViewById(R.id.backdrop_movie);
    }

    private void getData() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        id = movie.getId();
        judul = movie.getTitle();
        tglRilis = movie.getRelease_date();
        voteavg = Double.toString(movie.getVote_average());
        populer = String.valueOf(movie.getPopularity());
        deskripsi = movie.getOverview();
        poster = movie.getPhoto();
        banner = movie.getBanner();
    }

    private void setData() {
        title.setText(judul);
        releasedate.setText(tglRilis);
        vote_avg.setText(voteavg);
        popularity.setText(populer);
        overview.setText(deskripsi);

        Glide.with(this)
                .load(poster)
                .into(imgposter);

        Glide.with(this)
                .load(banner)
                .into(imgbackdrop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);

        if (movieFavDb.movieFavDao().isFav(id) == 1) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp));
            menu.getItem(0).setChecked(true);
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_24dp));
            menu.getItem(0).setChecked(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MovieFav movieFav = new MovieFav();

        movieFav.setId(id);
        movieFav.setTitle(judul);
        movieFav.setOverview(deskripsi);
        movieFav.setRelease_date(tglRilis);
        movieFav.setPosterPath(poster);

        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(TITLE, judul);
        values.put(OVERVIEW, deskripsi);
        values.put(RELEASE_DATE, tglRilis);
        values.put(POSTER_PATH, poster);

        if (item.getItemId() == R.id.favorite) {
            if (item.isChecked()) {
                item.setChecked(false);
                movieFavDb.movieFavDao().delete(movieFav);
                WidgetProvider.updatedWidget(this);

                Uri uri = Uri.parse(CONTENT_URI + "/" + id);
                getContentResolver().delete(uri, null, null);

                item.setIcon(R.drawable.ic_favorite_24dp);
                Toast.makeText(this, R.string.removed_from_favorite, Toast.LENGTH_LONG).show();
            } else {
                item.setChecked(true);
                movieFavDb.movieFavDao().addData(movieFav);
                WidgetProvider.updatedWidget(this);

                getContentResolver().insert(CONTENT_URI, values);

                item.setIcon(R.drawable.ic_favorite_black_24dp);
                Toast.makeText(this, R.string.added_to_favorite, Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
