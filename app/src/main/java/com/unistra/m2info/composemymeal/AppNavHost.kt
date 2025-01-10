package com.unistra.m2info.composemymeal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Suggestions", "Favorites")
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
        CustomRow(
            items = pages,
            selectedIndex = selectedTabIndex,
            onTabSelected = { index ->
                run {
                    if (index == 0) navController.navigate("suggestion")
                    else navController.navigate("favorites")
                }
                selectedTabIndex = index
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        SheetStackManager(sheetStack = sheetStack) {
            NavHost(navController = navController, startDestination = "suggestion") {
                composable("suggestion") { SuggestionScreen(navController, sheetStack) }
                composable("favorites") { FavoritesScreen(navController, sheetStack) }
            }
        }
    }
}