package com.mpm.notificationsaver.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Glass morphism effect modifiers
fun Modifier.glassEffect(
    shape: RoundedCornerShape = CardShape,
    borderWidth: Dp = 1.dp
) = this
    .clip(shape)
    .background(GlassSurface)
    .border(borderWidth, GlassBorder, shape)

fun Modifier.glassBackdrop(
    blur: Dp = 10.dp,
    opacity: Float = 0.8f
) = this
    .blur(blur)
    .background(Color.White.copy(alpha = opacity))

// Glass Card Composable
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = CardShape,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.glassEffect(shape),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = GlassSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        border = null
    ) {
        content()
    }
}

// Three-tier shadow system
val SoftShadow = 2.dp
val MediumShadow = 4.dp
val StrongShadow = 8.dp

// Glass morphism gradients
val GlassGradient = Brush.verticalGradient(
    colors = listOf(
        Color.White.copy(alpha = 0.9f),
        Color.White.copy(alpha = 0.6f)
    )
)

val PrimaryGlassGradient = Brush.linearGradient(
    colors = listOf(
        Primary.copy(alpha = 0.9f),
        Accent.copy(alpha = 0.9f)
    )
)