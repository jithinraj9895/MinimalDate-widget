package com.example.datewidget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateWidgetProvider : AppWidgetProvider() {

    companion object {

        const val ACTION_FONT_CHANGE = "com.example.datewidget.ACTION_FONT_CHANGE"

        private const val REQUEST_CODE = 1001

        private val fonts =
                arrayOf(
                        "sans-serif",
                        "serif",
                        "sans-serif-condensed",
                        "monospace",
                        "sans-serif-smallcaps"
                )

        fun updateAllWidgets(context: Context) {

            val manager = AppWidgetManager.getInstance(context)

            val componentName = ComponentName(context, DateWidgetProvider::class.java)

            val widgetIds = manager.getAppWidgetIds(componentName)

            for (widgetId in widgetIds) {
                updateWidget(context, manager, widgetId)
            }
        }

        private fun updateWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {

            val views = RemoteViews(context.packageName, R.layout.date_widget)

            val now = Date()

            val dayFormatter = SimpleDateFormat("EEEE", Locale.getDefault())

            val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

            val day = dayFormatter.format(now).uppercase(Locale.getDefault())

            val date = dateFormatter.format(now).uppercase(Locale.getDefault())

            val calendar = Calendar.getInstance()

            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

            val fontIndex = (currentHour / 5) % fonts.size

            // Hide all fonts
            views.setViewVisibility(R.id.font1, android.view.View.GONE)
            views.setViewVisibility(R.id.font2, android.view.View.GONE)
            views.setViewVisibility(R.id.font3, android.view.View.GONE)
            views.setViewVisibility(R.id.font4, android.view.View.GONE)
            views.setViewVisibility(R.id.font5, android.view.View.GONE)

            // Select the current font
            when (fontIndex) {
                0 -> {
                    views.setViewVisibility(R.id.font1, android.view.View.VISIBLE)
                    views.setTextViewText(R.id.dayText1, day)
                    views.setTextViewText(R.id.dateText1, date)
                }
                1 -> {
                    views.setViewVisibility(R.id.font2, android.view.View.VISIBLE)
                    views.setTextViewText(R.id.dayText2, day)
                    views.setTextViewText(R.id.dateText2, date)
                }
                2 -> {
                    views.setViewVisibility(R.id.font3, android.view.View.VISIBLE)
                    views.setTextViewText(R.id.dayText3, day)
                    views.setTextViewText(R.id.dateText3, date)
                }
                3 -> {
                    views.setViewVisibility(R.id.font4, android.view.View.VISIBLE)
                    views.setTextViewText(R.id.dayText4, day)
                    views.setTextViewText(R.id.dateText4, date)
                }
                4 -> {
                    views.setViewVisibility(R.id.font5, android.view.View.VISIBLE)
                    views.setTextViewText(R.id.dayText5, day)
                    views.setTextViewText(R.id.dateText5, date)
                }
            }

            manager.updateAppWidget(widgetId, views)
        }

        fun scheduleNextUpdate(context: Context) {

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent =
                    Intent(context, DateWidgetProvider::class.java).apply {
                        action = ACTION_FONT_CHANGE
                    }

            val pendingIntent =
                    PendingIntent.getBroadcast(
                            context,
                            REQUEST_CODE,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

            val now = Calendar.getInstance()
            val currentHour = now.get(Calendar.HOUR_OF_DAY)

            val nextBoundary = ((currentHour / 5) + 1) * 5

            val next =
                    Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, nextBoundary % 24)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)

                        if (nextBoundary >= 24) {
                            add(Calendar.DAY_OF_YEAR, 1)
                        }
                    }

            alarmManager.set(AlarmManager.RTC, next.timeInMillis, pendingIntent)
        }

        fun cancelScheduledUpdate(context: Context) {

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent =
                    Intent(context, DateWidgetProvider::class.java).apply {
                        action = ACTION_FONT_CHANGE
                    }

            val pendingIntent =
                    PendingIntent.getBroadcast(
                            context,
                            REQUEST_CODE,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

            alarmManager.cancel(pendingIntent)
        }
    }

    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) {

        updateAllWidgets(context)
        scheduleNextUpdate(context)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        updateAllWidgets(context)
        scheduleNextUpdate(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        cancelScheduledUpdate(context)
    }

    override fun onReceive(context: Context, intent: Intent) {

        super.onReceive(context, intent)

        if (intent.action == ACTION_FONT_CHANGE) {
            updateAllWidgets(context)
            scheduleNextUpdate(context)
        }
    }
}
