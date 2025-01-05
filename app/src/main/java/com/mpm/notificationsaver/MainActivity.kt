package com.mpm.notificationsaver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpm.notificationsaver.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotificationAdapter
    private lateinit var database: NotificationDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!isNotificationListenerEnabled()) {
            // Show dialog to request permission
            showNotificationListenerDialog()
        }

        // Initialize database
        database = NotificationDatabase.getInstance(applicationContext)

        // Initialize RecyclerView and adapter
        adapter = NotificationAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe notifications
        lifecycleScope.launch {
            database.notificationDao().getAllNotifications().collect { notifications ->
                Log.d("MainActivity", "Notifications: ${notifications.size}")
                adapter.submitList(notifications)
            }
        }
    }
    private fun isNotificationListenerEnabled(): Boolean {
        val packageName = packageName
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return flat?.contains(packageName) == true
    }

    private fun showNotificationListenerDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("This app needs notification access to save notifications. Please enable it in Settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}