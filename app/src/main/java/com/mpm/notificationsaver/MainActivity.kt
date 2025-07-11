package com.mpm.notificationsaver

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mpm.notificationsaver.repository.NotificationRepository
import com.mpm.notificationsaver.ui.screens.NotificationScreen
import com.mpm.notificationsaver.ui.theme.NotificationSaverTheme
import com.mpm.notificationsaver.viewmodel.NotificationViewModel
import com.mpm.notificationsaver.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy { NotificationDatabase.getInstance(this) }
    private val repository by lazy { NotificationRepository(database.notificationDao()) }
    private val viewModel: NotificationViewModel by viewModels {
        ViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isNotificationListenerEnabled()) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
        setContent {
            NotificationSaverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val notifications by viewModel.notifications.collectAsState()
                    NotificationScreen(notifications = notifications)
                }
            }
        }
    }

    private fun isNotificationListenerEnabled(): Boolean {
        return Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
            .contains(packageName)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotificationSaverTheme {
        NotificationScreen(notifications = emptyList())
    }
}