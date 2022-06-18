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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arifahmadalfian.comps.theme.BlueButtonBottom
import com.arifahmadalfian.comps.theme.BlueButtonTop


private const val IndicatorSize = 3
private const val IndicatorWidth = 6
private const val NumIndicators = 3

enum class AnimationType {
    Bounce,
    Fade,
    Circular
}

private val AnimationType.animationDuration: Int
    get() = when (this) {
        AnimationType.Bounce -> 300
        AnimationType.Fade -> 600
        AnimationType.Circular -> 0
    }

private val AnimationType.animationDelay: Int
    get() = animationDuration / NumIndicators

private val AnimationType.initialValue: Float
    get() = when (this) {
        AnimationType.Bounce -> IndicatorSize / 2f
        AnimationType.Fade -> 1f
        AnimationType.Circular -> 0f
    }

private val AnimationType.targetValue: Float
    get() = when (this) {
        AnimationType.Bounce -> -IndicatorSize / 2f
        AnimationType.Fade -> 0.2f
        AnimationType.Circular -> 0f
    }

@Composable
fun LoadingButtonIndicator(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    brush: Brush = Brush.verticalGradient(listOf(BlueButtonTop, BlueButtonBottom)),
    indicatorSpacing: Dp = 5.dp,
    animationType: AnimationType = AnimationType.Circular,
    text: String,
    textColor: Color,
) {
    val contentAlpha by animateFloatAsState(targetValue = if (loading) 0f else 1f)
    val loadingAlpha by animateFloatAsState(targetValue = if (loading) 1f else 0f)
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = Modifier
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
                Text(text = text, color = textColor)
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
            AnimationType.Bounce, AnimationType.Fade -> {
                val animatedValues = List(IndicatorSize) { index ->
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
                            .width(IndicatorWidth.dp)
                            .aspectRatio(1f)
                            .then(
                                when (animationType) {
                                    AnimationType.Bounce -> Modifier.offset(y = animatedValue.dp)
                                    AnimationType.Fade -> Modifier.graphicsLayer {
                                        alpha = animatedValue
                                    }
                                    AnimationType.Circular -> Modifier.graphicsLayer { alpha = 1f }
                                }
                            ),
                        color = color,
                    )
                }
            }
            AnimationType.Circular -> {
                LoadingCircular(color = color)
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
) {
    CircularProgressIndicator(
        modifier = modifier.size(IndicatorWidth.dp * 3),
        strokeWidth = IndicatorWidth.dp / 2,
        color = color,
    )
}
