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

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Suggestions", "Favorites")
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        // Afficher la barre d'onglets uniquement sur la homepage (Suggestions et Favorites)
        if (currentRoute == "suggestion" || currentRoute == "favorites") {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            if (index == 0) navController.navigate("suggestion")
                            else navController.navigate("favorites")
                        },
                        text = { Text(text = title) }
                    )
                }
            }
        }

        NavHost(navController = navController, startDestination = "suggestion") {
            composable("suggestion") { SuggestionScreen(navController) }
            composable("favorites") { FavoritesScreen() }
            composable("browse") { BrowseScreen(navController) }
            composable("ingredients") { IngredientsScreen() }
            composable("countries") { CountriesScreen() }
        }

    }
}