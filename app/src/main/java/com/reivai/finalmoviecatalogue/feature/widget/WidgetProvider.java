package com.reivai.finalmoviecatalogue.feature.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.reivai.finalmoviecatalogue.R;

public class WidgetProvider extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.reivai.finalmoviecatalogue.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.reivai.finalmoviecatalogue.EXTRA_ITEM";

    static void updateAppWidget(Context con, AppWidgetManager widgetManager, int widgetId) {
        Intent i = new Intent(con, WidgetSevices.class);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(con.getPackageName(), R.layout.fav_widget);
        views.setRemoteAdapter(R.id.stack, i);
        views.setEmptyView(R.id.stack ,R.id.empty);

        Intent toast = new Intent(con, WidgetProvider.class);
        toast.setAction(TOAST_ACTION);
        toast.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pi = PendingIntent.getBroadcast(con, 0, toast, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack, pi);

        widgetManager.updateAppWidget(widgetId, views);
    }

    @Override
    public void onReceive(Context con, Intent i) {
        super.onReceive(con, i);
        if (i.getAction() != null) {
            if (i.getAction().equals(TOAST_ACTION)) {
                int viewIndeks = i.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(con, "Touch View " + viewIndeks, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onUpdate(Context con, AppWidgetManager widgetManager, int[] widgetsId) {
        for (int widgetId : widgetsId) {
            updateAppWidget(con, widgetManager, widgetId);
        }
    }

    @Override
    public void onEnabled(Context con) {

    }

    @Override
    public void onDisabled(Context con) {

    }

    public static void updatedWidget(Context con) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(con);
        int[] widgetsId = widgetManager.getAppWidgetIds(new ComponentName(con, WidgetProvider.class));
        widgetManager.notifyAppWidgetViewDataChanged(widgetsId, R.id.stack);
    }
}
