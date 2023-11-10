package com.example.trywidget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class SettingActivity : Activity() {
    private var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }
        setContentView(R.layout.setting_activity)

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.send_button)

        button.setOnClickListener {
            val prefs = WidgetPreferences(this)
            prefs.saveStringPref(mAppWidgetId, editText.text.toString())


            val appWidgetManager = AppWidgetManager.getInstance(this)
            updateAppWidget(this, appWidgetManager, mAppWidgetId)

            val resultValue = Intent()
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
            setResult(RESULT_OK, resultValue)
            finish()
        }

        Log.d("SettingActivity","set button onclick")

    }

    internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget)
        val pref = WidgetPreferences(context)

        views.setTextViewText(R.id.text_view, LocalDateTime.now().toString()+"_"+pref.loadStringPref(appWidgetId))

        // ウィジェットを更新
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}