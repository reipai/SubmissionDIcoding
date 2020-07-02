package com.reivai.finalmoviecatalogue.feature.tvshow;

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
import com.reivai.finalmoviecatalogue.adapter.TvShowAdapter;
import com.reivai.finalmoviecatalogue.model.movie.Movie;
import com.reivai.finalmoviecatalogue.model.movie.MovieModel;
import com.reivai.finalmoviecatalogue.model.tvshow.TvShow;
import com.reivai.finalmoviecatalogue.model.tvshow.TvShowModel;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TvShowFragment extends Fragment {

    private TvShowAdapter adapter;
    private ArrayList<TvShow> tvShowArrayList = new ArrayList<>();
    private ProgressBar progressBar;
    private RecyclerView rvTvshow;
    private TvShowModel tvShowModel;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvshow = view.findViewById(R.id.rvtvshow);
        progressBar = view.findViewById(R.id.pb_tvshow);

        tvShowModel = ViewModelProviders.of(this).get(TvShowModel.class);
        tvShowModel.getTvshow().observe(this, getTvshow);

        adapter = new TvShowAdapter(tvShowArrayList);
        adapter.notifyDataSetChanged();

        rvTvshow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvshow.setAdapter(adapter);

        tvShowModel.setTvshow(getContext());

        adapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow itemTvshow) {
                Intent i = new Intent(getContext(), DetailTvShowActivity.class);
                i.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, itemTvshow);
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

    private final Observer<ArrayList<TvShow>> getTvshow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            showLoading(true);
            if (tvShows != null) {
                adapter.setTvShowData(tvShows);
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
        rvTvshow.setLayoutManager(new LinearLayoutManager(getActivity()));

        TvShowModel tvShowSearchModel = ViewModelProviders.of(this).get(TvShowModel.class);
        tvShowSearchModel.getSearchTvShow().observe(this, getTvShowSearch);
        tvShowSearchModel.setTvShowName(s);
        tvShowSearchModel.setSearchTvShow();
        adapter = new TvShowAdapter(tvShowArrayList);
        adapter.notifyDataSetChanged();

        rvTvshow.setAdapter(adapter);
    }

    private final Observer<ArrayList<TvShow>> getTvShowSearch = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if(tvShows != null){
                adapter.setTvShowData(tvShows);
                progressBar.setVisibility(View.GONE);
            }
        }

    };
}