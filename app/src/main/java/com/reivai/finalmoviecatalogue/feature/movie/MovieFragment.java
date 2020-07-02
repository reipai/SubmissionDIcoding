package com.reivai.finalmoviecatalogue.feature.movie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.adapter.MovieAdapter;
import com.reivai.finalmoviecatalogue.model.movie.Movie;
import com.reivai.finalmoviecatalogue.model.movie.MovieModel;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieFragment extends Fragment {

    private MovieAdapter adapter;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private ProgressBar progressBar;
    private RecyclerView rvmovie;
    private MovieModel movieModel;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvmovie = view.findViewById(R.id.rvmovie);
        progressBar = view.findViewById(R.id.pb_movie);

        movieModel = ViewModelProviders.of(this).get(MovieModel.class);
        movieModel.getMovie().observe(this, getMovie);

        adapter = new MovieAdapter(movieArrayList);
        adapter.notifyDataSetChanged();

        rvmovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvmovie.setAdapter(adapter);

        movieModel.setMovie(getContext());

        adapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie itemMovie) {
                Intent i = new Intent(getContext(), DetailMovieActivity.class);
                i.putExtra(DetailMovieActivity.EXTRA_MOVIE, itemMovie);
                startActivity(i);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
        }
        if(searchView != null){
            searchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    progressBar.setVisibility(View.VISIBLE);
                    searchRecylerView(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            return false;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private final Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            showLoading(true);
            if (movies != null) {
                adapter.setMovieData(movies);
                showLoading(false);
            }
        }
    };

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void searchRecylerView(String s){
        rvmovie.setLayoutManager(new LinearLayoutManager(getActivity()));

        MovieModel movieSearchModel = ViewModelProviders.of(this).get(MovieModel.class);
        movieSearchModel.getSearchMovie().observe(this, getMovieSearch);
        movieSearchModel.setMovieName(s);
        movieSearchModel.setSearchMovie();
        adapter = new MovieAdapter(movieArrayList);
        adapter.notifyDataSetChanged();

        rvmovie.setAdapter(adapter);
    }

    private final Observer<ArrayList<Movie>> getMovieSearch = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if(movies != null){
                adapter.setMovieData(movies);
                progressBar.setVisibility(View.GONE);

            }
        }

    };
}