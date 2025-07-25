package com.mpm.notificationsaver.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.mpm.notificationsaver.ui.theme.*

@Composable
fun SettingsScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToPrivacy: () -> Unit = {},
    onNavigateToSound: () -> Unit = {},
    onNavigateToWiFi: () -> Unit = {},
    onNavigateToBattery: () -> Unit = {},
    onNavigateToStorage: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var systemSoundsEnabled by remember { mutableStateOf(true) }
    var bluetoothEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { SettingsTopBar() },
        bottomBar = { SettingsBottomNavigationBar(onNavigateToHome = onNavigateToHome) },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Background)
        ) {
            // Profile Section
            ProfileCard()

            Spacer(modifier = Modifier.height(24.dp))

            // Account Section
            SettingsSection(title = "ACCOUNT") {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Profile",
                    onClick = onNavigateToProfile
                )
                SettingsItem(
                    icon = Icons.Default.Lock,
                    title = "Privacy & Security",
                    onClick = onNavigateToPrivacy
                )
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "Privacy Policy",
                    onClick = { /* Show privacy policy dialog */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Notifications Section
            SettingsSection(title = "NOTIFICATIONS") {
                SettingsItemWithSwitch(
                    icon = Icons.Default.Notifications,
                    title = "Push Notifications",
                    isChecked = pushNotificationsEnabled,
                    onCheckedChange = { pushNotificationsEnabled = it }
                )
                SettingsItem(
                    icon = Icons.Default.FavoriteBorder,
                    title = "Sound & Vibration",
                    onClick = onNavigateToSound
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Display & Sound Section
            SettingsSection(title = "DISPLAY & SOUND") {
                SettingsItemWithSwitch(
                    icon = Icons.Default.ArrowForward,
                    title = "Dark Mode",
                    isChecked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it }
                )
                SettingsItemWithSwitch(
                    icon = Icons.Default.ThumbUp,
                    title = "System Sounds",
                    isChecked = systemSoundsEnabled,
                    onCheckedChange = { systemSoundsEnabled = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Connectivity Section
            SettingsSection(title = "CONNECTIVITY") {
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "Wi-Fi",
                    onClick = onNavigateToWiFi
                )
                SettingsItemWithSwitch(
                    icon = Icons.Default.KeyboardArrowUp,
                    title = "Bluetooth",
                    isChecked = bluetoothEnabled,
                    onCheckedChange = { bluetoothEnabled = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Device Section
            SettingsSection(title = "DEVICE") {
                SettingsItem(
                    icon = Icons.Default.Star,
                    title = "Battery",
                    onClick = onNavigateToBattery
                )
                SettingsItem(
                    icon = Icons.Default.LocationOn,
                    title = "Storage",
                    onClick = onNavigateToStorage
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Permissions & Privacy Section
            SettingsSection(title = "PERMISSIONS & PRIVACY") {
                SettingsItem(
                    icon = Icons.Default.Notifications,
                    title = "Notification Access",
                    onClick = { /* Open notification settings */ }
                )
                SettingsItem(
                    icon = Icons.Default.Delete,
                    title = "Clear All Data",
                    onClick = { /* Clear all saved notifications */ }
                )
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "Data Usage Info",
                    onClick = { /* Show data usage explanation */ }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // App Info
            AppInfoCard()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SettingsTopBar() {
    // Glass morphism header
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
            // Settings icon with gradient background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PrimaryGradient),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ProfileCard() {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(SoftShadow, CardShape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryGlassGradient)
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large profile avatar with gradient
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.9f),
                                    Color.White.copy(alpha = 0.7f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👤",
                        fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "John Doe",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "john.doe@example.com",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Premium Member",
                        fontSize = 13.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            letterSpacing = 1.2.sp,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(SoftShadow, CardShape)
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(IconContainerShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Accent.copy(alpha = 0.2f),
                            Primary.copy(alpha = 0.2f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun SettingsItemWithSwitch(
    icon: ImageVector,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(IconContainerShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Accent.copy(alpha = 0.2f),
                            Primary.copy(alpha = 0.2f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Primary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = GlassBorder,
                checkedBorderColor = Primary,
                uncheckedBorderColor = GlassBorder
            )
        )
    }
}

@Composable
fun AppInfoCard() {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(SoftShadow, CardShape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(PrimaryGradient),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📱",
                    fontSize = 44.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "NotificationSaver",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Version 1.0",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Privacy-Focused",
                fontSize = 13.sp,
                color = Primary,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "🔒 Data stays on device",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SettingsBottomNavigationBar(
    onNavigateToHome: () -> Unit = {}
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
                isSelected = false,
                onClick = onNavigateToHome
            )
            
            // Settings Tab
            GlassNavItem(
                icon = Icons.Default.Settings,
                label = "Settings",
                isSelected = true,
                onClick = { /* Already on settings */ }
            )
        }
    }
}