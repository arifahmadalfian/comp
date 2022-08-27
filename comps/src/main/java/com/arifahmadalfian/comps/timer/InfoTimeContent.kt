package com.arifahmadalfian.comps.timer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@ExperimentalAnimationApi
@Composable
fun InfoTimeContent(
    timeContent: String,
    textStyle: TextStyle,
    textColor: Color,
    timeAnimation: TimeAnimation,
    timeDurationSlide: Int,
    fontFamily: FontFamily,
    fontWeight: FontWeight
) {
    AnimatedContent(
        targetState = timeContent,
        transitionSpec = {
            addAnimation(
                timeDurationSlide = timeDurationSlide,
                timeAnimation = timeAnimation
            ).using(
                SizeTransform(clip = true)
            )
        }
    ) { targetCount ->
        Text(
            text = targetCount,
            style = textStyle,
            textAlign = TextAlign.Center,
            color = textColor,
            fontFamily = fontFamily,
            fontWeight = fontWeight
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun InfoTimeSpace(
    timeSpace: String,
    textStyle: TextStyle,
    textColor: Color,
    fontFamily: FontFamily,
    fontWeight: FontWeight
) {
    Text(
        text = timeSpace,
        style = textStyle,
        textAlign = TextAlign.Center,
        color = textColor,
        fontFamily = fontFamily,
        fontWeight = fontWeight
    )
}