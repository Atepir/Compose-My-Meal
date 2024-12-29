package com.unistra.m2info.composemymeal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.unistra.m2info.composemymeal.layout.CustomRow
import com.unistra.m2info.composemymeal.layout.Sheet
import com.unistra.m2info.composemymeal.layout.SheetStack
import com.unistra.m2info.composemymeal.layout.SheetStackManager
import com.unistra.m2info.composemymeal.screens.FavoritesScreen
import com.unistra.m2info.composemymeal.screens.MealDetailScreen
import com.unistra.m2info.composemymeal.screens.SuggestionScreen


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

        SheetStackManager(sheetStack)

        NavHost(navController = navController, startDestination = "suggestion") {
            composable("suggestion") { SuggestionScreen(navController, sheetStack) }
            composable("favorites") { FavoritesScreen(navController, sheetStack) }
            composable("mealDetail/{mealId}") { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId")
                mealId?.let {
                    MealDetailScreen(mealId = it)
                }
            }
        }

    }
}