package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unistra.m2info.composemymeal.layout.SheetStack
import com.unistra.m2info.composemymeal.viewmodels.SearchMealViewModel

@Composable
fun SearchAllSheet(
    sheetStack: SheetStack,
    viewModel: SearchMealViewModel = viewModel()
    ) {
    var searchText by remember { mutableStateOf("") }
    var isLoading = viewModel.isLoading.value

    LaunchedEffect(Unit) {
        viewModel.fetchAllMeals()
    }

    var meals = viewModel.filteredMeals.value

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(top = 24.dp)
            .padding(16.dp)
    ) {
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
                modifier = Modifier.padding(top = 20.dp)
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

            Box(
                modifier = Modifier.align(Alignment.BottomCenter).background(Color.Transparent).padding(bottom = 32.dp)
            ) {
                SearchInputField(searchText, { searchText = it }, label = "Find your favorite recipe...")
            }
        }
    }
}