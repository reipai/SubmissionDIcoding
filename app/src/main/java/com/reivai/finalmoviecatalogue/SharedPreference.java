package com.reivai.finalmoviecatalogue;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final String MOVIE_CATALOGUE = "MOVIE_CATALOGUE";

    public static final String TITLE_MOVIE = "TITLE_MOVIE";
    public static final String DAILY_REMINDER = "DAILY_REMINDER";
    public static final String NEW_RELEASE_REMINDER = "NEW_RELEASE_REMINDER";

    private final SharedPreferences pref;
    SharedPreferences.Editor edit;

    public SharedPreference(Context con) {
        pref = con.getSharedPreferences(MOVIE_CATALOGUE, con.MODE_PRIVATE);
        edit = pref.edit();
    }

    public void stringSave(String key, String value) {
        edit.putString(key, value);
        edit.commit();
    }

    public void booleanSave(String key, boolean value) {
        edit.putBoolean(key, value);
        edit.commit();
    }

    public String getNewReleaseTitle() {
        return pref.getString(TITLE_MOVIE, "");
    }

    public Boolean getDailyReminder() {
        return pref.getBoolean(DAILY_REMINDER, false);
    }

    public Boolean getNewReleaseReminder() {
        return pref.getBoolean(NEW_RELEASE_REMINDER, false);
    }
}
