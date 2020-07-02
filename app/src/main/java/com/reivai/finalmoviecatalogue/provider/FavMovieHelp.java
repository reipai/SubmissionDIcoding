package com.reivai.finalmoviecatalogue.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.OVERVIEW;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.POSTER_PATH;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.RELEASE_DATE;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.TABLE_NAME;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.TITLE;

public class FavMovieHelp {

    private static final String DATABASE_NAME = TABLE_NAME;
    private final DbHelper dbHelper;
    private static FavMovieHelp INSTANCE;

    private SQLiteDatabase database;

    private FavMovieHelp(Context con){
        dbHelper = new DbHelper(con);
    }

    public static FavMovieHelp getInstance(Context con) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavMovieHelp(con);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<FavMovie> query() {
        ArrayList<FavMovie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);

        cursor.moveToFirst();
        FavMovie favMovie;

        if (cursor.getCount() > 0) {
            do {
                favMovie = new FavMovie();
                favMovie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favMovie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favMovie.setReleasedate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                favMovie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favMovie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                arrayList.add(favMovie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(FavMovie favMovie) {
        ContentValues values = new ContentValues();
        values.put(TITLE, favMovie.getTitle());
        values.put(RELEASE_DATE, favMovie.getReleasedate());
        values.put(OVERVIEW, favMovie.getOverview());
        values.put(POSTER_PATH, favMovie.getPosterPath());

        return database.insert(DATABASE_NAME, null, values);
    }

    public int update(FavMovie favMovie) {
        ContentValues values = new ContentValues();
        values.put(TITLE, favMovie.getTitle());
        values.put(RELEASE_DATE, favMovie.getReleasedate());
        values.put(OVERVIEW, favMovie.getOverview());
        values.put(POSTER_PATH, favMovie.getPosterPath());

        return database.update(DATABASE_NAME, values, _ID + "= '" + favMovie.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_NAME,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public long inProvider(ContentValues values) {
        return database.insert(DATABASE_NAME, null, values);
    }

    public int upProvider(String id, ContentValues values) {
        return database.update(DATABASE_NAME, values, _ID + " = ?", new String[]{id});
    }

    public int delProvider(String id) {
        return database.delete(DATABASE_NAME, _ID + " = ?", new String[]{id});
    }
}
