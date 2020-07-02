package com.reivai.finalmoviecatalogue.model.movie;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

public class Movie implements Parcelable {
    private int id;
    private String photo;
    private String banner;
    private String title;
    private String release_date;
    private String overview;
    private Number popularity;
    private Double vote_average;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Number getPopularity() {
        return popularity;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Movie(JSONObject object){
        try {
            this.id = object.getInt("id");
            String poster = object.getString("poster_path");
            this.photo = "https://image.tmdb.org/t/p/w185" + poster;
            String backdrop = object.getString("backdrop_path");
            this.banner = "https://image.tmdb.org/t/p/w300" + backdrop;
            this.title = object.getString("title");
            this.release_date = object.getString("release_date");
            this.overview = object.getString("overview");
            this.vote_average = object.getDouble("vote_average");
            this.popularity = (Number) object.get("popularity");
        } catch (Exception e){
            e.printStackTrace();
            Log.d("Error Data", e.getMessage());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.photo);
        dest.writeString(this.banner);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeSerializable(this.popularity);
        dest.writeValue(this.vote_average);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.photo = in.readString();
        this.banner = in.readString();
        this.title = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.popularity = (Number) in.readSerializable();
        this.vote_average = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
