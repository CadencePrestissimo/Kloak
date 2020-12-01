package com.example.kloak;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.widget.RemoteViews;
import android.widget.TextView;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {


      Clock cl=new Clock();
      String buttonText =cl.clock();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            Intent intent=new Intent(context,MainActivity.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            views.setOnClickPendingIntent(R.id.app_layout,pendingIntent);
            views.setCharSequence(R.id.textView2234, "setText", buttonText);
            appWidgetManager.updateAppWidget( appWidgetId,views);


        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
    }

}

