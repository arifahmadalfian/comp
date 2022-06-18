package com.arifahmadalfian.comp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arifahmadalfian.comps.AnimationType
import com.arifahmadalfian.comps.LoadingButtonIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                        AnimationLoadingBounce()
                        AnimationLoadingFade()
                        AnimationLoadingCircular()
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
        modifier = Modifier
            .padding(all = 16.dp),
        loading = loading,
        animationType = AnimationType.Bounce,
        text = "Bounce",
        textColor = Color.White,
    )

    Text(
        text = duration.toString(),
    )
}

@Composable
fun AnimationLoadingFade() {
    var loading by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

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
        modifier = Modifier
            .padding(all = 16.dp),
        loading = loading,
        animationType = AnimationType.Fade,
        text = "Fade",
        textColor = Color.White,
    )

    Text(
        text = duration.toString(),

        )
}

@Composable
fun AnimationLoadingCircular() {
    var loading by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

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
        modifier = Modifier
            .padding(all = 16.dp),
        loading = loading,
        animationType = AnimationType.Circular,
        text = "Bounce",
        textColor = Color.White,
    )

    Text(
        text = duration.toString(),
    )
}




