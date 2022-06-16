package com.arifahmadalfian.comps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arifahmadalfian.comps.BlueButtonBottom
import com.arifahmadalfian.comps.BlueButtonTop

@Composable
fun ButtonGradient(
    text: String,
    textColor: Color = Color.White,
    gradient: Brush = Brush.verticalGradient(listOf(BlueButtonTop, BlueButtonBottom)),
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 16.dp, vertical =9.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
