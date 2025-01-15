package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.unistra.m2info.composemymeal.viewmodels.IngredientsViewModel
import com.unistra.m2info.composemymeal.layout.CustomRow
import com.unistra.m2info.composemymeal.layout.SheetStack
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput



@Composable
fun IngredientsSheet(sheetStack: SheetStack, defaultIngredient: String = "Tomato") {
    val viewModel = remember { IngredientsViewModel() }
    val meals = viewModel.filteredMeals.value
    val isLoading = viewModel.isLoading.value
    val ingredients = viewModel.ingredients.value
    var selectedTabIndex by remember { mutableStateOf(0) }
    var searchText by remember { mutableStateOf("") }
    var handled by remember { mutableStateOf(false) }

    fun onTabSelected(index: Int) {
        selectedTabIndex = index
        viewModel.fetchMealsByIngredient(ingredients[index])
    }

    LaunchedEffect(Unit) {
        viewModel.fetchIngredients()
    }

    LaunchedEffect(ingredients, defaultIngredient) {
        val index = ingredients.indexOf(defaultIngredient)
        if (index != -1) {
            selectedTabIndex = index
            viewModel.fetchMealsByIngredient(ingredients[index])
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
            .pointerInput(Unit) {
                detectHorizontalDragGestures (
                    onDragEnd = { handled = false }, // Reset the flag when the drag ends
                    onHorizontalDrag = { _, dragAmount ->
                        if (!handled) {
                            when {
                                dragAmount < -30 && selectedTabIndex < ingredients.size - 1 -> {
                                    onTabSelected(selectedTabIndex + 1)
                                    handled = true
                                }
                                dragAmount > 30 && selectedTabIndex > 0 -> {
                                    onTabSelected(selectedTabIndex - 1)
                                    handled = true
                                }
                            }
                        }
                })
            }
    ) {
        if (ingredients.isNotEmpty()) {
            CustomRow(
                items = ingredients,
                selectedIndex = selectedTabIndex,
                onTabSelected = {it -> onTabSelected(it)}
            )
        } else {
            Text("Loading ingredients...", modifier = Modifier.padding(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.padding(top = 48.dp)
            ) {
                items(meals.filter { it.strMeal.contains(searchText, ignoreCase = true) }) { meal ->
                    MealCard(
                        meal = meal,
                        onClick = {
                            sheetStack.push { MealDetailScreen(mealId = meal.idMeal) }
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.padding(bottom = 48.dp))
                }
            }

            if ((meals.filter { it.strMeal.contains(searchText, ignoreCase = true) }).isEmpty()) {
                NoResults()
            }

            Box(
                modifier = Modifier.align(Alignment.BottomCenter).background(Color.Transparent).padding(bottom = 32.dp)
            ) {
                SearchInputField(searchText, { searchText = it }, label = "Search meals in this ingredient...")
            }
        }
    }
}







