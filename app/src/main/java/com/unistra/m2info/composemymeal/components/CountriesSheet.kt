package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.unistra.m2info.composemymeal.viewmodels.CountriesViewModel
import com.unistra.m2info.composemymeal.layout.CustomRow
import com.unistra.m2info.composemymeal.layout.SheetStack


@Composable
fun CountriesSheet(sheetStack: SheetStack, defaultCountry: String = "France") {
    val viewModel = remember { CountriesViewModel() }
    val meals = viewModel.meals.value
    val isLoading = viewModel.isLoading.value
    val countries = viewModel.countries.value.filter { it != "Unknown" }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var searchText by remember { mutableStateOf("") }
    var handled by remember { mutableStateOf(false) }

    fun onTabSelected(index: Int) {
        selectedTabIndex = index
        viewModel.fetchMealsByCountry(countries[index])
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCountries()
    }

    LaunchedEffect(countries, defaultCountry) {
        val index = countries.indexOf(defaultCountry)
        if (index != -1) {
            selectedTabIndex = index
            viewModel.fetchMealsByCountry(countries[index])
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
                                dragAmount < -30 && selectedTabIndex < countries.size - 1 -> {
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
        if (countries.isNotEmpty()) {
            CustomRow(
                items = countries,
                selectedIndex = selectedTabIndex,
                onTabSelected = {it -> onTabSelected(it)}
            )
        } else {
            Text("Loading countries...", modifier = Modifier.padding(16.dp))
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
                    Spacer(modifier = Modifier.padding(bottom = 100.dp))
                }
            }

            if ((meals.filter { it.strMeal.contains(searchText, ignoreCase = true) }).isEmpty()) {
                NoResults()
            }

            Box(
                modifier = Modifier.align(Alignment.BottomCenter).background(Color.Transparent).padding(bottom = 32.dp)
            ) {
                SearchInputField(searchText, { searchText = it }, label = "Search meals in this country...")
            }
        }
    }
}


