package com.example.trywidget

import android.content.Context
import android.content.SharedPreferences

class WidgetPreferences(context: Context) {
    private val PREFS_NAME = "com.example.app.widgetprefs"
    private val PREF_PREFIX_KEY = "appwidget_"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    // ウィジェットの設定を保存する
    fun saveStringPref(appWidgetId: Int, text: String) {
        val editor = prefs.edit()
        editor.putString(PREF_PREFIX_KEY +  "str", text)
        editor.apply()
    }

    // ウィジェットの設定を読み込む
    fun loadStringPref(appWidgetId: Int): String? {
        return prefs.getString(PREF_PREFIX_KEY +"str", null)
    }
}
