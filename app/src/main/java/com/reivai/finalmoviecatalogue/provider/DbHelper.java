package com.reivai.finalmoviecatalogue.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "db_fav_movie";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s" +
            " (%s INTEGER PRIMARY KEY NOT NULL, " +
            " %s TEXT NOT NULL, " +
            " %s TEXT NOT NULL, " +
            " %s TEXT NOT NULL, " +
//            " %s TEXT NOT NULL, " +
//            " %s TEXT NOT NULL, " +
//            " %s TEXT NOT NULL, " +
            " %s TEXT NOT NULL)",
            DbContract.FavColumns.TABLE_NAME,
            DbContract.FavColumns.ID,
            DbContract.FavColumns.TITLE,
            DbContract.FavColumns.RELEASE_DATE,
//            DbContract.FavColumns.VOTE_AVERAGE,
//            DbContract.FavColumns.POPULARITY,
            DbContract.FavColumns.OVERVIEW,
            DbContract.FavColumns.POSTER_PATH
//            DbContract.FavColumns.BANNER_PATH
    );

    public DbHelper(Context con) {
        super(con, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.FavColumns.TABLE_NAME);
        onCreate(db);
    }
}
