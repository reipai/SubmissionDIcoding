package com.reivai.finalmoviecatalogue.model.movie;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cz.msebera.android.httpclient.Header;

public class MovieModel extends ViewModel {

    private final MutableLiveData<ArrayList<Movie>> listmovie = new MutableLiveData<>();
    private static final String API_KEY = "bb13f2ea879f84f0b0ce1dc50edbf452";
    private String movieName;

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovie(final Context con) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> list = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" +API_KEY+ "&language=en-US&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray array = jsonObject.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Movie movies = new Movie(object);
                        list.add(movies);
                    }
                    listmovie.postValue(list);
                } catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", error.getMessage());
            }
        });
    }

    public void setSearchMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> list = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + movieName + "&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray array = jsonObject.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Movie movie = new Movie(object);
                        list.add(movie);
                    }
                    listmovie.postValue(list);

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

    public LiveData<ArrayList<Movie>> getMovie() {
        return listmovie;
    }

    public LiveData<ArrayList<Movie>> getSearchMovie() {
        return listmovie;
    }
}
