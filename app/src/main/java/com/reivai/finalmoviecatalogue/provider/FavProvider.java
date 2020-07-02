package com.reivai.finalmoviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.reivai.finalmoviecatalogue.feature.movie.MovieFavActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.reivai.finalmoviecatalogue.provider.DbContract.AUTHORITY;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.CONTENT_URI;
import static com.reivai.finalmoviecatalogue.provider.DbContract.FavColumns.TABLE_NAME;

public class FavProvider extends ContentProvider {
    private static final int MOVIES = 1;
    private static final int MOVIES_ID = 2;
    private static final UriMatcher matchUri = new UriMatcher(UriMatcher.NO_MATCH);
    private FavMovieHelp favMovieHelp;

    static {
        matchUri.addURI(AUTHORITY, TABLE_NAME, MOVIES);

        matchUri.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIES_ID);
    }

    @Override
    public boolean onCreate() {
        favMovieHelp = FavMovieHelp.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        favMovieHelp.open();
        Cursor cursor;
        switch (matchUri.match(uri)) {
            case MOVIES:
                cursor = favMovieHelp.queryProvider();
                break;

            case MOVIES_ID:
                cursor = favMovieHelp.queryByIdProvider(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        favMovieHelp.open();
        long added;

        switch (matchUri.match(uri)) {
            case MOVIES:
                added = favMovieHelp.inProvider(values);
                break;

            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MovieFavActivity.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        favMovieHelp.open();
        int updated;
        switch (matchUri.match(uri)) {
            case MOVIES_ID:
                updated = favMovieHelp.upProvider(uri.getLastPathSegment(), values);
                break;

            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MovieFavActivity.DataObserver(new Handler(), getContext()));
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        favMovieHelp.open();
        int deleted;
        switch (matchUri.match(uri)) {
            case MOVIES_ID:
                deleted = favMovieHelp.delProvider(uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MovieFavActivity.DataObserver(new Handler(), getContext()));
        return deleted;
    }
}
