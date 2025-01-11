package com.unistra.m2info.composemymeal.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import com.unistra.m2info.composemymeal.IngredientsViewModel
import com.unistra.m2info.composemymeal.layout.CustomRow
import com.unistra.m2info.composemymeal.layout.SheetStack
import androidx.compose.material3.Text

@Composable
fun IngredientsSheet(sheetStack: SheetStack, defaultIngredient: String = "Tomato") {
    val viewModel = remember { IngredientsViewModel() }
    val meals = viewModel.filteredMeals.value
    val isLoading = viewModel.isLoading.value
    val ingredients = viewModel.ingredients.value
    var selectedTabIndex by remember { mutableStateOf(0) }
    var searchText by remember { mutableStateOf("") }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (ingredients.isNotEmpty()) {
            CustomRow(
                items = ingredients,
                selectedIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                    viewModel.fetchMealsByIngredient(ingredients[index])
                }
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
            Box(modifier = Modifier.weight(1f)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(meals) { meal ->
                        MealCard(
                            meal = meal,
                            onClick = {
                                sheetStack.push { MealDetailScreen(mealId = meal.idMeal) }
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    viewModel.fetchMealsBySearchQuery(it)
                },
                placeholder = { Text("Search meals") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}







