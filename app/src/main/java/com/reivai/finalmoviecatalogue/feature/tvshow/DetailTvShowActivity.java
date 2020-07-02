package com.reivai.finalmoviecatalogue.feature.tvshow;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.database.TvShowFav;
import com.reivai.finalmoviecatalogue.model.tvshow.TvShow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static com.reivai.finalmoviecatalogue.MainActivity.tvShowFavDb;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    private int id;
    private TextView title, releasedate, vote_avg, popularity, overview;
    private ImageView imgposter, imgbackdrop;
    private String judul, tglRilis, voteavg, populer, deskripsi, poster, banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_tvshow);
        setContentView(R.layout.activity_detail_tv_show);

        bindData();
        getData();
        setData();
    }

    private void bindData() {
        title = findViewById(R.id.title_tvshow);
        releasedate = findViewById(R.id.releasedate_tvshow);
        vote_avg = findViewById(R.id.vote_tvshow);
        popularity = findViewById(R.id.popuarity_tvshow);
        overview = findViewById(R.id.overview_tvshow);
        imgposter = findViewById(R.id.imgtvshow);
        imgbackdrop = findViewById(R.id.backdrop_tvshow);
    }

    private void getData() {
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        id = tvShow.getId();
        judul = tvShow.getTitle();
        tglRilis = tvShow.getRelease_date();
        voteavg = Double.toString(tvShow.getVote_average());
        populer = String.valueOf(tvShow.getPopularity());
        deskripsi = tvShow.getOverview();
        poster = tvShow.getPhoto();
        banner = tvShow.getBanner();
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

        if (tvShowFavDb.tvShowFavDao().isFav(id) == 1) {
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
        TvShowFav tvShowFav = new TvShowFav();

        tvShowFav.setId(id);
        tvShowFav.setTitle(judul);
        tvShowFav.setRelease_date(tglRilis);
        tvShowFav.setOverview(deskripsi);
        tvShowFav.setPosterPath(poster);

        if (item.getItemId() == R.id.favorite) {
            if (item.isChecked()) {
                item.setChecked(false);
                tvShowFavDb.tvShowFavDao().delete(tvShowFav);
                item.setIcon(R.drawable.ic_favorite_24dp);
                Toast.makeText(this, R.string.removed_from_favorite, Toast.LENGTH_SHORT).show();
            } else {
                item.setChecked(true);
                tvShowFavDb.tvShowFavDao().addData(tvShowFav);
                item.setIcon(R.drawable.ic_favorite_black_24dp);
                Toast.makeText(this, R.string.added_to_favorite, Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
