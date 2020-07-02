package com.reivai.favmovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.reivai.favmovie.DbContract.getColomsInt;
import static com.reivai.favmovie.DbContract.getColomsString;

public class FavMovieItem implements Parcelable {
    private int id;
    private String title;
    private String releasedate;
    private String overview;
    private String posterpath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.releasedate);
        dest.writeString(this.overview);
        dest.writeString(this.posterpath);
    }

    public FavMovieItem(int id, String title, String releasedate, String overview, String posterpath) {
        this.id = id;
        this.title = title;
        this.releasedate = releasedate;
        this.overview = overview;
        this.posterpath = posterpath;
    }

    public FavMovieItem(Cursor cursor) {
        this.id = getColomsInt(cursor, _ID);
        this.title = getColomsString(cursor, DbContract.FavMovieColoms.TITLE);
        this.releasedate = getColomsString(cursor, DbContract.FavMovieColoms.RELEASE_DATE);
        this.overview = getColomsString(cursor, DbContract.FavMovieColoms.OVERVIEW);
        this.posterpath = getColomsString(cursor, DbContract.FavMovieColoms.POSTER_PATH);
    }

    protected FavMovieItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.releasedate = in.readString();
        this.overview = in.readString();
        this.posterpath = in.readString();
    }

    public static final Parcelable.Creator<FavMovieItem> CREATOR = new Parcelable.Creator<FavMovieItem>() {
        @Override
        public FavMovieItem createFromParcel(Parcel source) {
            return new FavMovieItem(source);
        }

        @Override
        public FavMovieItem[] newArray(int size) {
            return new FavMovieItem[size];
        }
    };
}
