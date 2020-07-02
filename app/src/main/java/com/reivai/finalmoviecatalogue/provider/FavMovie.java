package com.reivai.finalmoviecatalogue.provider;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.reivai.finalmoviecatalogue.provider.DbContract.getColomsInt;
import static com.reivai.finalmoviecatalogue.provider.DbContract.getColomsString;

public class FavMovie implements Parcelable {
    private int id;
    private String title;
    private String releasedate;
    private String overview;
    private String posterPath;

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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
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
        dest.writeString(this.posterPath);
    }

    public FavMovie() {
    }

    public FavMovie(int id, String title, String releasedate, String overview, String posterPath) {
        this.id = id;
        this.title = title;
        this.releasedate = releasedate;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public FavMovie(Cursor cursor) {
        this.id = getColomsInt(cursor, _ID);
        this.title = getColomsString(cursor, DbContract.FavColumns.TITLE);
        this.releasedate = getColomsString(cursor, DbContract.FavColumns.RELEASE_DATE);
        this.overview = getColomsString(cursor, DbContract.FavColumns.OVERVIEW);
        this.posterPath = getColomsString(cursor, DbContract.FavColumns.POSTER_PATH);
    }

    protected FavMovie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.releasedate = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
    }

    public static final Parcelable.Creator<FavMovie> CREATOR = new Parcelable.Creator<FavMovie>() {
        @Override
        public FavMovie createFromParcel(Parcel source) {
            return new FavMovie(source);
        }

        @Override
        public FavMovie[] newArray(int size) {
            return new FavMovie[size];
        }
    };
}
