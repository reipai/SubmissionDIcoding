package com.reivai.finalmoviecatalogue.feature.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetSevices extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteView(this.getApplicationContext());
    }
}
