package com.mpm.notificationsaver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val database = NotificationDatabase.getInstance(context)
        val dao = database.notificationDao()

        val packageName = intent.getStringExtra("package") ?: return
        val title = intent.getStringExtra("title")
        val text = intent.getStringExtra("text")
        val timestamp = intent.getLongExtra("timestamp", System.currentTimeMillis())

        Log.d("NotificationReceiver", """
            Saving Notification:
            Package: $packageName
            Title: $title
            Text: $text
            Time: ${java.util.Date(timestamp)}
        """.trimIndent())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val notification = NotificationEntity(
                    packageName = packageName,
                    title = title,
                    text = text,
                    timestamp = timestamp
                )
                dao.insert(notification)
                Log.d("NotificationReceiver", "Successfully saved notification to database")
            } catch (e: Exception) {
                Log.e("NotificationReceiver", "Error saving notification", e)
            }
        }
    }
}