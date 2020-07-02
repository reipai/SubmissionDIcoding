package com.reivai.finalmoviecatalogue.feature.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.reivai.finalmoviecatalogue.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import cz.msebera.android.httpclient.Header;

public class ReceiverReminder extends BroadcastReceiver {
    private static final String API_KEY = "bb13f2ea879f84f0b0ce1dc50edbf452";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private String title, message;

    public ReceiverReminder() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        message = intent.getStringExtra(EXTRA_MESSAGE);

        if (message.equalsIgnoreCase("EXTRA_MESSAGE")){
            getNewRelease(context);
        } else {
            title = "Movie Catalogue";
            showNotifReminder(context, title, message);
        }
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    private void showNotifReminder(Context con, String title, String msg) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "remindManager channel";

        int reqCode = msg.equalsIgnoreCase("EXTRA_MESSAGE") ? 102 : 101;

        NotificationManager notifManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri remindSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(con, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_movie)
                .setContentTitle(title)
                .setContentText(msg)
                .setColor(ContextCompat.getColor(con, android.R.color.transparent))
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setSound(remindSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            nc.enableVibration(true);
            nc.setVibrationPattern(new long[] {1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notifManager != null) {
                notifManager.createNotificationChannel(nc);
            }
        }

        Notification notif = builder.build();

        if (notifManager != null){
            notifManager.notify(reqCode, notif);
        }
    }

    public void setDailyRemind(Context con, String time, String msg) {
        String TIME_FORMAT = "HH:mm";

        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(con, ReceiverReminder.class);
        i.putExtra(EXTRA_MESSAGE, msg);

        String[] arrayTime = time.split(":");

        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrayTime[0]));
        myCalendar.set(Calendar.MINUTE, Integer.parseInt(arrayTime[1]));
        myCalendar.set(Calendar.SECOND, 0);

        PendingIntent pi =  PendingIntent.getBroadcast(con, 101, i, 0);

        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        }
    }

    public void setNewReleaseRemind(Context con, String time, String msg) {
        String TIME_FORMAT = "HH:mm";

        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(con, ReceiverReminder.class);
        i.putExtra(EXTRA_MESSAGE, msg);

        String[] arrayTime = time.split(":");

        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrayTime[0]));
        myCalendar.set(Calendar.MINUTE, Integer.parseInt(arrayTime[1]));
        myCalendar.set(Calendar.SECOND, 0);

        PendingIntent pi = PendingIntent.getBroadcast(con, 102, i, 0);

        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        }
    }

    public void cancelDailyRemind(Context con) {
        AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(con, ReceiverReminder.class);
        PendingIntent pi = PendingIntent.getBroadcast(con, 101, i, 0);
        pi.cancel();

        if (am != null) {
            am.cancel(pi);
        }
    }

    public void cancelNewReleaseRemind(Context con) {
        AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(con, ReceiverReminder.class);
        PendingIntent pi = PendingIntent.getBroadcast(con, 102, i, 0);
        pi.cancel();

        if (am != null) {
            am.cancel(pi);
        }
    }

    public void getNewRelease(final Context context) {
        AsyncHttpClient client = new AsyncHttpClient();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateCurrent = dateFormat.format(new Date());

        String url = "https://api.themoviedb.org/3/movie/discover?api_key=" + API_KEY + "&primary_release_date.gte="
                + dateCurrent + "&primary_release_date.lte=" + dateCurrent;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");

                    title = "New Released Movie";
                    message = "";

                    for (int i = 0; i<array.length(); i++) {
                        JSONObject release = array.getJSONObject(i);
                        message = message + release.getString("title") + ", ";
                    }

                    showNotifReminder(context, title, message);
                } catch (Exception e) {
                    Log.d("ERR", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("NoCon", error.getMessage());
            }
        });
    }
}
