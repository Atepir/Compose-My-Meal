package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.unistra.m2info.composemymeal.BrowseSheet
import com.unistra.m2info.composemymeal.FavoritesManager
import com.unistra.m2info.composemymeal.layout.BottomNavigation
import com.unistra.m2info.composemymeal.layout.SheetStack

@Composable
fun FavoritesScreen(navController: NavController, sheetStack: SheetStack) {
    val favorites = FavoritesManager.favorites
    var handled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().padding(top = 56.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = { handled = false }, // Reset the flag when the drag ends
                    onHorizontalDrag = { _, dragAmount ->
                        if (!handled) {
                            when {
                                dragAmount > 50 -> {
                                    navController.navigate("suggestion")
                                    handled = true
                                }
                            }
                        }
                    })
            }
    ) {
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No favorite meals yet!", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.padding(8.dp).padding(bottom = 88.dp), // For BottomNavigation
            ) {
                items(favorites) { meal ->
                    MealCard(meal = meal, onClick = {
                        sheetStack.push { MealDetailScreen(meal.idMeal, sheetStack) }
                    })
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 24.dp)
        ) {
            BottomNavigation(sheetStack = sheetStack, navController = navController)
        }
    }

}
