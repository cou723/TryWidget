package com.example.trywidget

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.time.LocalDateTime

class WidgetProvider : AppWidgetProvider() {

    companion object {
        private const val BUTTON_CLICKED = "buttonClicked"
    }

    private fun setText(myId:Int,context: Context, views: RemoteViews) {
        val pref = WidgetPreferences(context)

        views.setTextViewText(R.id.text_view, LocalDateTime.now().toString()+"_"+pref.loadStringPref(myId))
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for (appWidgetId in appWidgetIds) {
            val intent = Intent(context, WidgetProvider::class.java).apply {
                action = BUTTON_CLICKED
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }

            val pendingIntent =
                PendingIntent.getBroadcast(context, appWidgetId, intent, FLAG_IMMUTABLE)

            val views = RemoteViews(context.packageName, R.layout.widget).apply {
                setOnClickPendingIntent(R.id.button, pendingIntent)
            }
            setText(appWidgetId,context,views)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (BUTTON_CLICKED == intent.action) {
            val widgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val views = RemoteViews(context.packageName, R.layout.widget)

            setText(widgetId,context,views)
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}
