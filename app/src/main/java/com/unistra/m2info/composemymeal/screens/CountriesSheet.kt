package com.unistra.m2info.composemymeal.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.unistra.m2info.composemymeal.CountriesViewModel
import com.unistra.m2info.composemymeal.layout.CustomRow
import com.unistra.m2info.composemymeal.layout.SheetStack


@Composable
fun CountriesSheet(sheetStack: SheetStack, defaultCountry: String = "France") {
    val viewModel = remember { CountriesViewModel() }
    val meals = viewModel.meals.value
    val isLoading = viewModel.isLoading.value
    val countries = viewModel.countries.value
    var selectedTabIndex by remember { mutableStateOf(0) }
    var searchText by remember { mutableStateOf("") }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (countries.isNotEmpty()) {
            CustomRow(
                items = countries,
                selectedIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                    viewModel.fetchMealsByCountry(countries[index])
                }
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
            Box(modifier = Modifier.weight(1f)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(meals.filter { it.strMeal.contains(searchText, ignoreCase = true) }) { meal ->
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
                onValueChange = { searchText = it },
                placeholder = { Text("Search meals") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}


