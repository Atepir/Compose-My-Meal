package com.unistra.m2info.composemymeal.layout

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unistra.m2info.composemymeal.R
import com.unistra.m2info.composemymeal.utils.OnBoardingHelper

@Composable
fun SlidingHandOnboarding(
    context: Context,
    content:  @Composable () -> Unit,
    onDismiss: @Composable () -> Unit
) {
    var showOnboarding by remember { mutableStateOf(true) }

    if (showOnboarding) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()

            // Overlay the hand animation
            HandSwipeAnimation(onDismiss = { showOnboarding = false })
        }
    } else {
        onDismiss()
        OnBoardingHelper.setOnboardingShown(context, true)
    }
}

@Composable
fun HandSwipeAnimation(onDismiss: () -> Unit) {
    val handOffsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Loop the animation to slide the hand back and forth
        while (true) {
            handOffsetX.animateTo(
                targetValue = -100f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
            handOffsetX.snapTo(0f)
        }
    }

    // Overlay for the hand animation
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)), // Dim background
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Swipe to navigate",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = handOffsetX.value.dp, y = 0.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tap),
                    contentDescription = "Hand icon",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onDismiss() }) {
                Text(text = "Got it!")
            }
        }
    }
}
