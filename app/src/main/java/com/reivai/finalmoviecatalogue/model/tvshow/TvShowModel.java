package com.reivai.finalmoviecatalogue.model.tvshow;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.reivai.finalmoviecatalogue.model.movie.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cz.msebera.android.httpclient.Header;

public class TvShowModel extends ViewModel {

    private final MutableLiveData<ArrayList<TvShow>> listTvshow = new MutableLiveData<>();
    private static final String API_KEY = "bb13f2ea879f84f0b0ce1dc50edbf452";
    private String tvShowName;

    public void setTvShowName(String tvShowName) {
        this.tvShowName = tvShowName;
    }

    public void setTvshow(final Context con) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> list = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/popular?api_key=" +API_KEY+ "&language=en-US&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray array = responseObject.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        TvShow tvShow = new TvShow(object);
                        list.add(tvShow);
                    }
                    listTvshow.postValue(list);
                } catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setSearchTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> list = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + tvShowName + "&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray array = jsonObject.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        TvShow tvShow = new TvShow(object);
                        list.add(tvShow);
                    }
                    listTvshow.postValue(list);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getTvshow() {
        return listTvshow;
    }

    public LiveData<ArrayList<TvShow>> getSearchTvShow() {
        return listTvshow;
    }
}
