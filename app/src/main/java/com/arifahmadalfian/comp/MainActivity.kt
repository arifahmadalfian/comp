package com.arifahmadalfian.comp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arifahmadalfian.comps.AnimationType
import com.arifahmadalfian.comps.LoadingButtonIndicator
import com.arifahmadalfian.comps.theme.Red600
import com.arifahmadalfian.comps.theme.fontFamily
import com.arifahmadalfian.comps.timer.InfoTime
import com.arifahmadalfian.comps.timer.TimeAnimation
import com.arifahmadalfian.comps.timer.TimeMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Surface {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {

                        /**
                         * ButtonLoading
                         */
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimationLoadingBounce()
                            AnimationLoadingFade()
                            AnimationLoadingCircular()
                        }

                        Spacer(modifier = Modifier.padding(16.dp))

                        /**
                         * Timer up & down
                         */
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TimeDownWithSlideTop()
                            TimeUpWithSlideBottom()
                        }

                        Spacer(modifier = Modifier.padding(16.dp))

                        /**
                         * CurrentTime
                         */
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CurrentTimeBounce()
                            CurrentTime()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimationLoadingBounce() {
    var loading by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LoadingButtonIndicator(
            onClick = {
                loading = !loading
                scope.launch {
                    duration = 3000
                    delay(1000)
                    duration -= 1000
                    delay(1000)
                    duration -= 1000
                    delay(1000)
                    duration -= 1000
                    loading = false
                }
                scope.launch {
                    delay(1000)

                }
            },
            loading = loading,
            animationType = AnimationType.Bounce(indicatorSize = 4f),
            text = "Bounce",
            textColor = Color.White,
        )

        Text(
            text = duration.toString(),
        )
    }

}

@Composable
fun AnimationLoadingFade() {
    var loading by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth(0.3f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LoadingButtonIndicator(
            onClick = {
                loading = !loading
                scope.launch {
                    duration = 3000
                    delay(1000)
                    duration -= 1000
                    delay(1000)
                    duration -= 1000
                    delay(1000)
                    duration -= 1000
                    loading = false
                }
                scope.launch {
                    delay(1000)

                }
            },
            loading = loading,
            animationType = AnimationType.Fade(),
            text = "Fade",
            textColor = Color.White,
        )

        Text(
            text = duration.toString(),
        )
    }

}

@Composable
fun AnimationLoadingCircular() {
    var loading by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LoadingButtonIndicator(
            onClick = {
                loading = !loading
                scope.launch {
                    duration = 3000
                    delay(1000)
                    duration -= 1000
                    delay(1000)
                    duration -= 1000
                    delay(1000)
                    duration -= 1000
                    loading = false
                }
                scope.launch {
                    delay(1000)

                }
            },
            loading = loading,
            animationType = AnimationType.Circular(),
            text = "Circular",
            textColor = Color.White,
        )

        Text(
            text = duration.toString(),
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun TimeDownWithSlideTop() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoTime(
            epoch = 40,
            timeMode = TimeMode.DOWN_TIMER,
            timeAnimation = TimeAnimation.SLIDE_TOP,
            fontFamily = fontFamily,
            backgroundColor = Red600,
            textColor = Color.White,
            icon = painterResource(id = R.drawable.ic_baseline_timer_24)
        ) {
            println(it)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun TimeUpWithSlideBottom() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoTime(
            epoch = 0,
            timeMode = TimeMode.UP_TIMER,
            timeAnimation = TimeAnimation.SLIDE_BOTTOM,
            fontFamily = fontFamily,
            backgroundColor = Red600,
            textColor = Color.White,
            icon = painterResource(id = R.drawable.ic_baseline_access_time_24),
            iconColor = ColorFilter.tint(color = Color.Green)
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CurrentTimeBounce() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoTime(
            epoch = 0,
            timeMode = TimeMode.UP_TIMER,
            timeAnimation = TimeAnimation.BOUNCE_BOTTOM,
            fontFamily = fontFamily,
            backgroundColor = Red600,
            timeDurationSlide = 800,
            textColor = Color.White
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun CurrentTime() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val time = System.currentTimeMillis() / 1000
        InfoTime(
            epoch = time,
            timeMode = TimeMode.UP_TIMER,
            timeAnimation = TimeAnimation.NONE,
            fontFamily = fontFamily,
            backgroundColor = Red600,
            textColor = Color.White,
        )
    }
}




