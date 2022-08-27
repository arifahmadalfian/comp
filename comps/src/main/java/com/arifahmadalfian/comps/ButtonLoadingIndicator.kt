package com.arifahmadalfian.comps

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arifahmadalfian.comps.theme.BlueButtonBottom
import com.arifahmadalfian.comps.theme.BlueButtonTop
import com.arifahmadalfian.comps.theme.fontFamily

sealed class AnimationType {
    data class Bounce(
        var indicatorSize: Float = 3f,
        var indicatorWidth: Int = 6,
        var numIndicator: Int = 3,
        var duration: Int = 300
    ) : AnimationType()

    data class Fade(
        var indicatorSize: Float = 3f,
        var indicatorWidth: Int = 6,
        var numIndicator: Int = 3,
        var duration: Int = 600
    ) : AnimationType()

    data class Circular(
        var indicatorSize: Float = 3f,
        var indicatorWidth: Int = 6,
        var numIndicator: Int = 3,
        var duration: Int = 0
    ) : AnimationType()
}

private val AnimationType.animationDuration: Int
    get() = when (this) {
        is AnimationType.Bounce -> this.duration
        is AnimationType.Fade -> this.duration
        is AnimationType.Circular -> this.duration
    }

private val AnimationType.animationDelay: Int
    get() = when (this) {
        is AnimationType.Bounce -> animationDuration / this.numIndicator
        is AnimationType.Fade -> animationDuration / this.numIndicator
        is AnimationType.Circular -> animationDuration / this.numIndicator
    }

private val AnimationType.initialValue: Float
    get() = when (this) {
        is AnimationType.Bounce -> this.indicatorSize / 2f
        is AnimationType.Fade -> 1f
        is AnimationType.Circular -> 0f
    }

private val AnimationType.targetValue: Float
    get() = when (this) {
        is AnimationType.Bounce -> -this.indicatorSize / 2f
        is AnimationType.Fade -> 0.2f
        is AnimationType.Circular -> 0f
    }

private val AnimationType.indicator: Float
    get() = when (this) {
        is AnimationType.Bounce -> this.indicatorSize
        is AnimationType.Fade -> this.indicatorSize
        is AnimationType.Circular -> this.indicatorSize
    }

private val AnimationType.indicatorWidth: Int
    get() = when (this) {
        is AnimationType.Bounce -> this.indicatorWidth
        is AnimationType.Fade -> this.indicatorWidth
        is AnimationType.Circular -> this.indicatorWidth
    }

@Composable
fun LoadingButtonIndicator(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    brush: Brush = Brush.verticalGradient(listOf(BlueButtonTop, BlueButtonBottom)),
    indicatorSpacing: Dp = 5.dp,
    animationType: AnimationType = AnimationType.Circular(),
    text: String,
    textColor: Color,
) {
    val contentAlpha by animateFloatAsState(targetValue = if (loading) 0f else 1f)
    val loadingAlpha by animateFloatAsState(targetValue = if (loading) 1f else 0f)
    Button(
        onClick = {
            if (!loading) {
                onClick()
            }
        },
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = modifier
                .background(brush)
                .padding(horizontal = 16.dp, vertical = 9.dp),
            contentAlignment = Alignment.Center,
        ) {
            LoadingIndicator(
                animating = loading,
                modifier = Modifier.graphicsLayer { alpha = loadingAlpha },
                color = textColor,
                indicatorSpacing = indicatorSpacing,
                animationType = animationType
            )
            Box(
                modifier = Modifier
                    .graphicsLayer { alpha = contentAlpha }
            ) {
                Text(
                    text = text,
                    color = textColor,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator(
    animating: Boolean,
    modifier: Modifier = Modifier,
    color: Color,
    indicatorSpacing: Dp = 5.dp,
    animationType: AnimationType
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (animationType) {
            is AnimationType.Bounce, is AnimationType.Fade -> {
                val animatedValues = List(animationType.indicator.toInt()) { index ->
                    var animatedValue by remember(
                        key1 = animating,
                        key2 = animationType
                    ) { mutableStateOf(0f) }
                    LaunchedEffect(key1 = animating, key2 = animationType) {
                        if (animating) {
                            animate(
                                initialValue = animationType.initialValue,
                                targetValue = animationType.targetValue,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(durationMillis = animationType.animationDuration),
                                    repeatMode = RepeatMode.Reverse,
                                    initialStartOffset = StartOffset(animationType.animationDelay * index),
                                ),
                            ) { value, _ -> animatedValue = value }
                        }
                    }
                    animatedValue
                }
                animatedValues.forEach { animatedValue ->
                    LoadingDot(
                        modifier = Modifier
                            .padding(horizontal = indicatorSpacing)
                            .width(animationType.indicatorWidth.dp)
                            .aspectRatio(1f)
                            .then(
                                when (animationType) {
                                    is AnimationType.Bounce -> Modifier.offset(y = animatedValue.dp)
                                    is AnimationType.Fade -> Modifier.graphicsLayer {
                                        alpha = animatedValue
                                    }
                                    is AnimationType.Circular -> Modifier.graphicsLayer {
                                        alpha = 1f
                                    }
                                }
                            ),
                        color = color,
                    )
                }
            }
            is AnimationType.Circular -> {
                LoadingCircular(
                    color = color,
                    indicatorWidth = animationType.indicatorWidth.toFloat(),
                )
            }
        }
    }
}

@Composable
private fun LoadingDot(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = color)
    )
}

@Composable
private fun LoadingCircular(
    color: Color,
    modifier: Modifier = Modifier,
    indicatorWidth: Float
) {
    CircularProgressIndicator(
        modifier = modifier.size(indicatorWidth.dp * 3),
        strokeWidth = indicatorWidth.dp / 2,
        color = color,
    )
}
