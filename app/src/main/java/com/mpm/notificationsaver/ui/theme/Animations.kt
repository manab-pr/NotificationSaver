package com.mpm.notificationsaver.ui.theme

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

// Animation Constants (300ms ease transitions)
const val ANIMATION_DURATION = 300
const val FAST_ANIMATION_DURATION = 150
const val SLOW_ANIMATION_DURATION = 500

// Easing Functions
val StandardEasing: Easing = FastOutSlowInEasing
val DecelerateEasing: Easing = LinearOutSlowInEasing

// Standard Animation Specs
fun <T> standardTween() = tween<T>(
    durationMillis = ANIMATION_DURATION,
    easing = StandardEasing
)

fun <T> fastTween() = tween<T>(
    durationMillis = FAST_ANIMATION_DURATION,
    easing = StandardEasing
)

fun <T> slowTween() = tween<T>(
    durationMillis = SLOW_ANIMATION_DURATION,
    easing = DecelerateEasing
)

// Glass morphism specific animations
val GlassFadeIn = fadeIn(animationSpec = standardTween())
val GlassFadeOut = fadeOut(animationSpec = standardTween())

val GlassSlideInFromRight = slideInHorizontally(
    animationSpec = standardTween(),
    initialOffsetX = { it }
)

val GlassSlideOutToRight = slideOutHorizontally(
    animationSpec = standardTween(),
    targetOffsetX = { it }
)

val GlassSlideInFromLeft = slideInHorizontally(
    animationSpec = standardTween(),
    initialOffsetX = { -it }
)

val GlassSlideOutToLeft = slideOutHorizontally(
    animationSpec = standardTween(),
    targetOffsetX = { -it }
)

// Touch interaction animations
fun Modifier.pressAnimation(
    pressed: Boolean,
    scaleDown: Float = 0.95f
) = this.scale(if (pressed) scaleDown else 1f)

fun Modifier.hoverAnimation(
    hovered: Boolean,
    scaleUp: Float = 1.05f
) = this.scale(if (hovered) scaleUp else 1f)

// Glass card entrance animation
fun Modifier.glassCardEntrance(
    visible: Boolean,
    delay: Int = 0
) = this.graphicsLayer {
    val progress = if (visible) 1f else 0f
    alpha = progress
    scaleX = 0.8f + (0.2f * progress)
    scaleY = 0.8f + (0.2f * progress)
    translationY = (1f - progress) * 50f
}

// Swipe gesture animation
fun Modifier.swipeOffset(offsetX: Float) = this.offset {
    IntOffset(offsetX.roundToInt(), 0)
}