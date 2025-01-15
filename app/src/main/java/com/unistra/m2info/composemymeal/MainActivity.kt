package com.unistra.m2info.composemymeal

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.unistra.m2info.composemymeal.layout.SlidingHandOnboarding
import com.unistra.m2info.composemymeal.ui.theme.ComposeMyMealTheme
import com.unistra.m2info.composemymeal.utils.FavoritesManager
import com.unistra.m2info.composemymeal.utils.OnBoardingHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FavoritesManager.initialize(this)
        enableEdgeToEdge()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        setContent {
            ComposeMyMealTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!OnBoardingHelper.isOnboardingShown(this))
                        SlidingHandOnboarding(this, content = { AppNavHost()}) {
                            AppNavHost()
                        }
                    else AppNavHost()
                }
            }
        }
    }
}