package com.mpm.notificationsaver.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Glassmorphism Shape System
// Rounded corner consistency: 2xl (1rem) and 3xl (1.5rem)
val GlassShapes = Shapes(
    small = RoundedCornerShape(12.dp),     // 0.75rem
    medium = RoundedCornerShape(16.dp),    // 1rem (2xl)
    large = RoundedCornerShape(24.dp)      // 1.5rem (3xl)
)

// Individual shape definitions for specific use cases
val CardShape = RoundedCornerShape(16.dp)         // Standard glass card
val ButtonShape = RoundedCornerShape(12.dp)       // Buttons and interactive elements
val BottomSheetShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 24.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)
val ModalShape = RoundedCornerShape(24.dp)        // Modals and dialogs
val IconContainerShape = RoundedCornerShape(12.dp) // Icon backgrounds

// Legacy shapes (keeping for compatibility)
val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)