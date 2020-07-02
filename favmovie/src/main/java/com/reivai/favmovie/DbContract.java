package com.reivai.favmovie;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final String AUTHORITY = "com.reivai.finalmoviecatalogue";
    private static final String SCHEME = "content";

    private DbContract() {

    }

    public static final class FavMovieColoms implements BaseColumns {
        public static final String TABLE_NAME = "fav_movie";
        public static final String TITLE = "title";
        public static final String RELEASE_DATE = "release_date";
        public static final String OVERVIEW = "overview";
        public static final String POSTER_PATH = "poster_path";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static String getColomsString(Cursor cursor, String colomName) {
        return cursor.getString(cursor.getColumnIndex(colomName));
    }

    public static int getColomsInt(Cursor cursor, String colomName) {
        return cursor.getInt(cursor.getColumnIndex(colomName));
    }

    public static long getColomsLong(Cursor cursor, String colomName) {
        return cursor.getLong(cursor.getColumnIndex(colomName));
    }
}
