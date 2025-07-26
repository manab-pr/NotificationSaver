package com.mpm.notificationsaver.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mpm.notificationsaver.NotificationEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailBottomSheet(notification: NotificationEntity?, onDismiss: () -> Unit) {
    if (notification != null) {
        val sheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Package: ${notification.packageName}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Title: ${notification.title}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Text: ${notification.text}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Timestamp: ${notification.timestampFormatted()}")
            }
        }
    }
}