package com.mpm.notificationsaver.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.mpm.notificationsaver.NotificationEntity
import com.mpm.notificationsaver.ui.theme.*

// Data classes for the UI
data class NotificationUIItem(
    val id: String,
    val appName: String,
    val appIcon: ImageVector, // In real app, this would be a drawable resource
    val title: String,
    val text: String,
    val timeAgo: String,
    val iconBackgroundColor: Color
)

@Composable
fun NotificationScreenWithBottomBar(
    notifications: List<NotificationEntity>,
    onNavigateToSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedNotification by remember { mutableStateOf<NotificationEntity?>(null) }

    Scaffold(
        topBar = { NotificationTopBar() },
        bottomBar = { CustomBottomNavigationBar(onNavigateToSettings = onNavigateToSettings) },
        modifier = modifier
    ) { paddingValues ->
        NotificationList(
            notifications = notifications,
            onNotificationClick = { selectedNotification = it },
            modifier = Modifier.padding(paddingValues)
        )
    }

    selectedNotification?.let { notification ->
        NotificationDetailBottomSheet(notification = notification) {
            selectedNotification = null
        }
    }
}

@Composable
fun NotificationTopBar() {
    var showDropdown by remember { mutableStateOf(false) }
    
    // Glass morphism header with sticky positioning
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(10f)
            .shadow(MediumShadow, CardShape)
            .glassEffect()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bell icon with gradient background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PrimaryGradient),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Notifications",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))

            // More options with dropdown
            Box {
                IconButton(onClick = { showDropdown = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = { showDropdown = false },
                    modifier = Modifier
                        .glassEffect()
                        .background(GlassSurface)
                ) {
                    DropdownMenuItem(
                        text = { Text("Mark all as read") },
                        onClick = { 
                            showDropdown = false
                            // Handle mark all as read
                        },
                        leadingIcon = {
                            Icon(Icons.Default.CheckCircle, contentDescription = null)
                        }
                    )
                    DropdownMenuItem(
                        text = { 
                            Text(
                                "Clear all", 
                                color = ErrorRed
                            ) 
                        },
                        onClick = { 
                            showDropdown = false
                            // Handle clear all
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Delete, 
                                contentDescription = null,
                                tint = ErrorRed
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationList(
    notifications: List<NotificationEntity>,
    onNotificationClick: (NotificationEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    if (notifications.isEmpty()) {
        // Empty state when no notifications
        EmptyNotificationState(modifier = modifier)
    } else {
        // Convert NotificationEntity to NotificationUIItem for display
        val uiNotifications = notifications.mapIndexed { index, notification ->
            NotificationUIItem(
                id = notification.id.toString(),
                appName = getAppNameFromPackage(notification.packageName),
                appIcon = getIconForApp(index), // You'd map actual app icons here
                title = notification.title,
                text = notification.text,
                timeAgo = formatTimeAgo(notification.timestamp),
                iconBackgroundColor = getColorForPackage(notification.packageName)
            )
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Background),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(uiNotifications) { notificationUI ->
                NotificationItemNew(
                    notification = notificationUI,
                    onClick = {
                        // Find original notification and call onClick
                        notifications.find { it.id.toString() == notificationUI.id }?.let {
                            onNotificationClick(it)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EmptyNotificationState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Large notification bell icon with glass effect
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                GlassSurface.copy(alpha = 0.3f),
                                GlassSurface.copy(alpha = 0.1f)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = GlassBorder.copy(alpha = 0.3f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "No notifications",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Main message
            Text(
                text = "No Notifications",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle message
            Text(
                text = "You're all caught up! 🎉\nNo new notifications have been received.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Optional glass card with tips
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(SoftShadow, CardShape)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tip",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Make sure notification access is enabled in Settings to capture notifications from other apps.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

// Helper function to get emoji for app
fun getEmojiForApp(appName: String): String {
    return when {
        appName.contains("WhatsApp", ignoreCase = true) -> "📱"
        appName.contains("Gmail", ignoreCase = true) -> "📧"
        appName.contains("Phone", ignoreCase = true) -> "📞"
        appName.contains("Spotify", ignoreCase = true) -> "🎵"
        appName.contains("Instagram", ignoreCase = true) -> "📷"
        appName.contains("YouTube", ignoreCase = true) -> "🎥"
        appName.contains("Twitter", ignoreCase = true) -> "🐦"
        appName.contains("Facebook", ignoreCase = true) -> "👥"
        appName.contains("Telegram", ignoreCase = true) -> "📩"
        appName.contains("Discord", ignoreCase = true) -> "🎮"
        else -> "🔔"
    }
}

@Composable
fun NotificationItemNew(
    notification: NotificationUIItem,
    onClick: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var showDeleteButton by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        // Delete button background (shown when swiped)
        if (showDeleteButton) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(CardShape)
                    .background(ErrorRedBackground)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = ErrorRed,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        // Main notification card with glass effect
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(SoftShadow, CardShape)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (offsetX < -200) {
                                // Handle delete action
                                showDeleteButton = false
                                offsetX = 0f
                            } else {
                                showDeleteButton = offsetX < -50
                                offsetX = 0f
                            }
                        }
                    ) { _, dragAmount ->
                        val newOffset = offsetX + dragAmount
                        if (newOffset <= 0) {
                            offsetX = newOffset
                            showDeleteButton = newOffset < -50
                        }
                    }
                }
                .clickable { onClick() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // App icon with gradient background and emoji
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(IconContainerShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    notification.iconBackgroundColor,
                                    notification.iconBackgroundColor.copy(alpha = 0.8f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Using emoji-style icons for apps
                    Text(
                        text = getEmojiForApp(notification.appName),
                        fontSize = 24.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Notification content
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = notification.appName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = notification.timeAgo,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = notification.title,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = notification.text,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CustomBottomNavigationBar(
    onNavigateToSettings: () -> Unit = {}
) {
    // Glass morphism bottom navigation bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(StrongShadow, BottomSheetShape)
            .glassEffect(BottomSheetShape)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Home Tab
            GlassNavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = true,
                onClick = { /* Handle home navigation */ }
            )
            
            // Settings Tab
            GlassNavItem(
                icon = Icons.Default.Settings,
                label = "Settings",
                isSelected = false,
                onClick = onNavigateToSettings
            )
        }
    }
}

@Composable
fun GlassNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) PrimaryGradient else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
    val iconColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
    val textColor = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant
    
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .then(
                    if (isSelected) {
                        Modifier.background(backgroundColor)
                    } else {
                        Modifier.background(Color.Transparent)
                    }
                )
                .border(
                    width = if (isSelected) 0.dp else 1.dp,
                    color = if (isSelected) Color.Transparent else GlassBorder,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            color = textColor
        )
    }
}

// Helper functions
fun getIconForApp(index: Int): ImageVector {
    return when (index % 5) {
        0 -> Icons.Default.Person
        1 -> Icons.Default.Email
        2 -> Icons.Default.Phone
        3 -> Icons.Default.ThumbUp
        4 -> Icons.Default.ShoppingCart
        else -> Icons.Default.Notifications
    }
}

fun getColorForApp(index: Int): Color {
    return when (index % 8) {
        0 -> Primary                     // Purple
        1 -> Accent                      // Blue
        2 -> Color(0xFF34A853)          // Green for WhatsApp
        3 -> Color(0xFF1DB954)          // Spotify green
        4 -> Color(0xFFE1306C)          // Instagram gradient color
        5 -> Color(0xFFFF3040)          // YouTube red
        6 -> Color(0xFF1DA1F2)          // Twitter blue
        7 -> Color(0xFF4267B2)          // Facebook blue
        else -> Primary
    }
}

fun formatTimeAgo(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60_000 -> "just now"
        diff < 3_600_000 -> "${diff / 60_000}m ago"
        diff < 86_400_000 -> "${diff / 3_600_000}h ago"
        diff < 604_800_000 -> "${diff / 86_400_000}d ago"
        else -> "${diff / 604_800_000}w ago"
    }
}

// Glass morphism bottom sheet component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailBottomSheet(
    notification: NotificationEntity,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = GlassSurface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shape = BottomSheetShape,
        dragHandle = null,
        modifier = Modifier.glassEffect(BottomSheetShape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            // Custom drag handle and close button row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                )

                // Close button
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Notification header with app icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // App icon with glass effect
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(ModalShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    getColorForPackage(notification.packageName),
                                    getColorForPackage(notification.packageName).copy(alpha = 0.8f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getEmojiForApp(getAppNameFromPackage(notification.packageName)),
                        fontSize = 28.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // App name and time
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = getAppNameFromPackage(notification.packageName),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = notification.packageName,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }

                Text(
                    text = formatTimeAgo(notification.timestamp),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Notification content
            Text(
                text = notification.text,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Dismiss button with glass effect
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .glassEffect(ButtonShape),
                    shape = ButtonShape,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = GlassBackground
                    ),
                    border = null
                ) {
                    Text(
                        text = "Dismiss",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Reply button with gradient
                Button(
                    onClick = { /* Handle reply action */ },
                    modifier = Modifier
                        .weight(1f)
                        .shadow(SoftShadow, ButtonShape),
                    shape = ButtonShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Reply",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

// Additional helper functions
fun getIconForPackage(packageName: String): ImageVector {
    return when {
        packageName.contains("whatsapp", ignoreCase = true) -> Icons.Default.Call
        packageName.contains("gmail", ignoreCase = true) -> Icons.Default.Email
        packageName.contains("phone", ignoreCase = true) -> Icons.Default.Phone
        packageName.contains("spotify", ignoreCase = true) -> Icons.Default.ShoppingCart
        packageName.contains("instagram", ignoreCase = true) -> Icons.Default.Star
        else -> Icons.Default.Notifications
    }
}

fun getColorForPackage(packageName: String): Color {
    return when {
        packageName.contains("whatsapp", ignoreCase = true) -> Color(0xFF25D366)
        packageName.contains("gmail", ignoreCase = true) -> Color(0xFFEA4335)
        packageName.contains("phone", ignoreCase = true) -> Color(0xFF4285F4)
        packageName.contains("spotify", ignoreCase = true) -> Color(0xFF1DB954)
        packageName.contains("instagram", ignoreCase = true) -> Color(0xFFE4405F)
        else -> Color(0xFF5B6BFF)
    }
}

fun getAppNameFromPackage(packageName: String): String {
    return when {
        packageName.contains("whatsapp", ignoreCase = true) -> "WhatsApp"
        packageName.contains("gmail", ignoreCase = true) -> "Gmail"
        packageName.contains("phone", ignoreCase = true) -> "Phone"
        packageName.contains("spotify", ignoreCase = true) -> "Spotify"
        packageName.contains("instagram", ignoreCase = true) -> "Instagram"
        else -> packageName.substringAfterLast(".").capitalize()
    }
}