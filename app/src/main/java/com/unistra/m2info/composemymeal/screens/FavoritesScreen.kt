package com.unistra.m2info.composemymeal.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.unistra.m2info.composemymeal.layout.SheetStack

@Composable
fun FavoritesScreen(navController: NavController, sheetStack: SheetStack) {
    // Placeholder for Favorites Screen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Favorites Screen")
    }
}