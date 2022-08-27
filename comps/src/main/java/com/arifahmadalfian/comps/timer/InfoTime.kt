package com.arifahmadalfian.comps.timer

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat

@ExperimentalAnimationApi
@Composable
fun InfoTime(
    epoch: Long,
    textStyle: TextStyle = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize),
    textColor: Color = Color.Black,
    fontFamily: FontFamily = FontFamily.Default,
    fontWeight: FontWeight = FontWeight.SemiBold,
    backgroundColor: Color = Color.White,
    backgroundPadding: Dp = 0.dp,
    backgroundElevation: Dp = 4.dp,
    backgroundShape: Shape = RoundedCornerShape(16.dp),
    timeAnimation: TimeAnimation = TimeAnimation.SLIDE_TOP,
    timeMode: TimeMode = TimeMode.NOW_TIMER,
    timeDurationSlide: Int = 500,
    timeSpace: String = " : ",
    icon: Painter? = null,
    iconColor: ColorFilter? = null,
    finish: ((Boolean) -> Unit)? = null
) {
    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("HH:mm:ss")

    val time = when (timeMode) {
        TimeMode.NOW_TIMER -> {
            sdf.format(epoch * 1000L)
        }
        TimeMode.UP_TIMER, TimeMode.DOWN_TIMER -> {
            val hour: Long = epoch / (60 * 60) % 24
            val minute: Long = epoch / 60 % 60
            val second: Long = epoch % 60
            String.format("%02d:%02d:%02d", hour, minute, second)
        }
    }

    val times = time.split(":")

    var tensOfHours: String by remember { mutableStateOf(times[0].take(1)) }
    var unitOfHours: String by remember { mutableStateOf(times[0].takeLast(1)) }
    var tensOfMinutes: String by remember { mutableStateOf(times[1].take(1)) }
    var unitOfMinutes: String by remember { mutableStateOf(times[1].takeLast(1)) }
    var tensOfSeconds: String by remember { mutableStateOf(times[2].take(1)) }
    var unitOfSeconds: String by remember { mutableStateOf(times[2].takeLast(1)) }

    LaunchedEffect(key1 = true) {
        var seconds = epoch
        while (seconds >= 0) {
            val mTime = when (timeMode) {
                TimeMode.NOW_TIMER -> {
                    sdf.format(seconds * 1000L)
                }
                TimeMode.UP_TIMER, TimeMode.DOWN_TIMER -> {
                    val mHour: Long = seconds / (60 * 60) % 24
                    val mMinute: Long = seconds / 60 % 60
                    val mSecond: Long = seconds % 60
                    String.format("%02d:%02d:%02d", mHour, mMinute, mSecond)
                }
            }
            val mTimes = mTime.split(":")

            tensOfHours = mTimes[0].take(1)
            unitOfHours = mTimes[0].takeLast(1)
            tensOfMinutes = mTimes[1].take(1)
            unitOfMinutes = mTimes[1].takeLast(1)
            tensOfSeconds = mTimes[2].take(1)
            unitOfSeconds = mTimes[2].takeLast(1)

            delay(1000)

            when (timeMode) {
                TimeMode.NOW_TIMER, TimeMode.UP_TIMER -> seconds++
                TimeMode.DOWN_TIMER -> seconds--
            }
        }
        if (seconds < 0) if (finish != null) {
            finish(true)
        }
    }

    Card(
        backgroundColor = backgroundColor,
        shape = backgroundShape,
        elevation = backgroundElevation,
    ) {
        /**
         * to fill the time does not change change the width size (alpha = 0)
         */
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .padding(all = backgroundPadding)
                .alpha(0f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            /**
             * Icon Time hide
             */
            icon?.let {
                Image(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    painter = it,
                    colorFilter = iconColor ?: ColorFilter.tint(textColor),
                    contentDescription = null
                )
            }

            Text(
                text = "00${timeSpace}00${timeSpace}00",
                style = textStyle,
                textAlign = TextAlign.Center,
                color = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )
        }

        /**
         * Content Time
         */
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .padding(all = backgroundPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            /**
             * Icon Time
             */
            icon?.let {
                Image(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    painter = it,
                    colorFilter = iconColor ?: ColorFilter.tint(textColor),
                    contentDescription = null
                )
            }

            /**
             * Tens of hours
             */
            InfoTimeContent(
                timeContent = tensOfHours,
                textStyle = textStyle,
                timeAnimation = timeAnimation,
                timeDurationSlide = timeDurationSlide,
                textColor = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )

            /**
             * Unit of hours
             */
            InfoTimeContent(
                timeContent = unitOfHours,
                textStyle = textStyle,
                timeAnimation = timeAnimation,
                timeDurationSlide = timeDurationSlide,
                textColor = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )

            InfoTimeSpace(
                timeSpace = timeSpace,
                textStyle = textStyle,
                textColor = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )

            /**
             * Tens of minutes
             */
            InfoTimeContent(
                timeContent = tensOfMinutes,
                textStyle = textStyle,
                timeAnimation = timeAnimation,
                timeDurationSlide = timeDurationSlide,
                textColor = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )

            /**
             * Unit of minutes
             */
            InfoTimeContent(
                timeContent = unitOfMinutes,
                textStyle = textStyle,
                timeAnimation = timeAnimation,
                timeDurationSlide = timeDurationSlide,
                textColor = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )

            InfoTimeSpace(
                timeSpace = timeSpace,
                textStyle = textStyle,
                textColor = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )

            /**
             * Tens of seconds
             */
            InfoTimeContent(
                timeContent = tensOfSeconds,
                textStyle = textStyle,
                timeAnimation = timeAnimation,
                timeDurationSlide = timeDurationSlide,
                textColor = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )

            /**
             * Unit of seconds
             */
            InfoTimeContent(
                timeContent = unitOfSeconds,
                textStyle = textStyle,
                timeAnimation = timeAnimation,
                timeDurationSlide = timeDurationSlide,
                textColor = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )
        }
    }
}

@ExperimentalAnimationApi
fun addAnimation(timeDurationSlide: Int, timeAnimation: TimeAnimation): ContentTransform {
    val duration = when (timeAnimation) {
        TimeAnimation.NONE -> 0
        else -> timeDurationSlide
    }

    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height ->
        when (timeAnimation) {
            TimeAnimation.NONE -> 0
            TimeAnimation.SLIDE_TOP -> -height
            TimeAnimation.SLIDE_BOTTOM -> height
            TimeAnimation.BOUNCE_TOP -> -height
            TimeAnimation.BOUNCE_BOTTOM -> height
        }
    } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with slideOutVertically(animationSpec = tween(durationMillis = duration)) { height ->
        when (timeAnimation) {
            TimeAnimation.NONE -> 0
            TimeAnimation.SLIDE_TOP -> height
            TimeAnimation.SLIDE_BOTTOM -> -height
            TimeAnimation.BOUNCE_TOP -> -height
            TimeAnimation.BOUNCE_BOTTOM -> height
        }
    } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
}
