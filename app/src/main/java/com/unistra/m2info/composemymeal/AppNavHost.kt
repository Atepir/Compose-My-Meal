package com.unistra.m2info.composemymeal

import android.text.Layout
import android.transition.Fade
import androidx.compose.foundation.layout.*
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.unistra.m2info.composemymeal.layout.CustomRow
import com.unistra.m2info.composemymeal.layout.SheetStack
import com.unistra.m2info.composemymeal.layout.SheetStackManager
import com.unistra.m2info.composemymeal.components.FavoritesScreen
import com.unistra.m2info.composemymeal.components.MealDetailScreen
import com.unistra.m2info.composemymeal.components.SuggestionScreen
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.LayoutDirection
import com.unistra.m2info.composemymeal.layout.BottomNavigation

@Composable
fun AppNavHost() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()
        var sheetStack by remember { mutableStateOf(SheetStack()) }
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        val pages = listOf("Suggestion", "Favorites")

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp)
        ) {
            CustomRow(
                items = pages,
                selectedIndex = if (currentRoute == "favorites") 1 else 0,
                onTabSelected = { index ->
                    if (index == 0 && currentRoute != "suggestion") {
                        navController.navigate("suggestion") {
                            launchSingleTop = true
                        }
                    } else if (index == 1 && currentRoute != "favorites") {
                        navController.navigate("favorites") {
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            SheetStackManager(sheetStack = sheetStack) {
                NavHost(
                    navController = navController,
                    startDestination = "suggestion"
                ) {
                    composable(
                        route = "suggestion"
                    ) { SuggestionScreen(navController, sheetStack) }

                    composable(
                        route = "favorites"
                    ) { FavoritesScreen(navController, sheetStack) }
                }
            }
        }

        if (sheetStack.isEmpty) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .padding(bottom = 32.dp)
            ) {
                BottomNavigation(sheetStack = sheetStack, navController = navController)
            }
        }
    }
}
