package com.mpm.notificationsaver.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mpm.notificationsaver.NotificationEntity

@Composable
fun NotificationScreen(notifications: List<NotificationEntity>) {
    var selectedNotification by remember { mutableStateOf<NotificationEntity?>(null) }

    LazyColumn {
        items(notifications) { notification ->
            NotificationItem(notification = notification) {
                selectedNotification = notification
            }
        }
    }

    selectedNotification?.let { notification ->
        NotificationDetailBottomSheet(notification = notification) {
            selectedNotification = null
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationEntity, onClick: (NotificationEntity) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(notification) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = notification.title, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            Text(text = notification.text, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        }
    }
}