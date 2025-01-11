package com.unistra.m2info.composemymeal

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    val sheetStack = remember {SheetStack()}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        val pages = listOf("Suggestion", "Favorites")
        var selectedTabIndex by remember { mutableStateOf(0) }
        navController.currentDestination?.let {
            Spacer(modifier = Modifier.fillMaxWidth().padding(8.dp))
            CustomRow(
                items = pages,
                selectedIndex = if (it.route == "favorites") 1 else 0,
                onTabSelected = { index ->
                    run {
                        if (index == 0) navController.navigate("suggestion")
                        else navController.navigate("favorites")
                        println("Tab selected $index")
                    }
                    selectedTabIndex = index
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
    ) {
        SheetStackManager(sheetStack = sheetStack) {
            NavHost(
                navController = navController, startDestination = "suggestion",
            ) {
                composable("suggestion",
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(200)) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(10)) },
                ) { SuggestionScreen(navController, sheetStack) }
                composable("favorites",
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(200)) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(10)) },
                ) { FavoritesScreen(navController, sheetStack) }
            }
        }
    }
}