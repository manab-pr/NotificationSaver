package com.mpm.notificationsaver.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Glassmorphism Light Theme Color Scheme
private val GlassmorphismLightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Accent,
    background = Background,
    surface = GlassSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Foreground,
    onSurface = Foreground,
    outline = GlassBorder,
    outlineVariant = GlassBorder.copy(alpha = 0.5f),
    surfaceVariant = GlassBackground,
    onSurfaceVariant = Foreground.copy(alpha = 0.7f),
    error = ErrorRed,
    onError = Color.White
)

// Dark theme (keeping for future implementation)
private val DarkColorScheme = darkColorScheme(
    primary = Purple200,
    secondary = Teal200
)

private val LightColorScheme = lightColorScheme(
    primary = Purple500,
    secondary = Teal200
)

@Composable
fun NotificationSaverTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useGlassmorphism: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = when {
        useGlassmorphism && !darkTheme -> GlassmorphismLightColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val shapes = if (useGlassmorphism) GlassShapes else Shapes

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}