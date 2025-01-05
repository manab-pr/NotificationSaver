package com.mpm.notificationsaver

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.content.Intent

class NotificationListener : NotificationListenerService() {

override fun onNotificationPosted(sbn: StatusBarNotification) {
    val packageName = sbn.packageName
    val extras = sbn.notification.extras
    val title = extras.getString("android.title")
    val text = extras.getString("android.text")
    val time = sbn.postTime

    // Log all notifications
    Log.d("NotificationListener", """
        New Notification:
        Package: $packageName
        Title: $title
        Text: $text
        Time: ${java.util.Date(time)}
    """.trimIndent())

    // Save all notifications to database
    val intent = Intent("com.mpm.notificationsaver.SAVE_NOTIFICATION")
    intent.putExtra("package", packageName)
    intent.putExtra("title", title)
    intent.putExtra("text", text)
    intent.putExtra("timestamp", time)
    sendBroadcast(intent)
}

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // Optionally handle notification removal
    }
}