package com.mpm.notificationsaver.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

// Glassmorphism Color Tokens (HSL-based)
val Primary = Color(0xFF9562FF)      // hsl(262, 83%, 58%) - Vibrant purple
val Accent = Color(0xFF4A90E2)       // hsl(213, 93%, 68%) - Bright blue
val Background = Color(0xFFFBFBFB)   // hsl(240, 10%, 98%) - Very light gray
val Foreground = Color(0xFF0A0A0B)   // hsl(240, 10%, 4%) - Near black text
val GlassBorder = Color(0xFFD1D5DB)  // hsl(240, 5%, 84%) - Subtle gray borders

// Glass Effect Colors
val GlassBackground = Color(0xFFFFFFF7)  // Semi-transparent white
val GlassBackgroundDark = Color(0x1AFFFFFF)  // Light overlay for glass effect
val GlassSurface = Color(0xF5FFFFFF)  // Glass surface color
val GlassBorderLight = Color(0x33FFFFFF)  // Light border for glass

// Gradient Definitions
val PrimaryGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF9562FF), // hsl(262, 83%, 58%)
        Color(0xFF4A90E2)  // hsl(213, 93%, 68%)
    )
)

// Shadow Colors
val ShadowSoft = Color(0x0A000000)      // Soft shadow
val ShadowMedium = Color(0x14000000)    // Medium shadow
val ShadowStrong = Color(0x1F000000)    // Strong shadow

// Interactive States
val HoverBackground = Color(0x0F9562FF)  // Light purple hover
val PressedBackground = Color(0x1A9562FF) // Darker purple pressed

// Destructive Colors
val ErrorRed = Color(0xFFEF4444)        // Red for destructive actions
val ErrorRedBackground = Color(0x1AEF4444) // Light red background

// Legacy colors (keeping for compatibility)
val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)