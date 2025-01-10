package com.unistra.m2info.composemymeal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unistra.m2info.composemymeal.layout.SheetStack
import com.unistra.m2info.composemymeal.screens.CountriesSheet
import com.unistra.m2info.composemymeal.screens.IngredientsSheet

@Composable
fun BrowseSheet(
    sheetStack: SheetStack,
    ingredientViewModel: IngredientsViewModel = remember { IngredientsViewModel() },
    countryViewModel: CountriesViewModel = remember { CountriesViewModel() }
) {
    val popularIngredients = listOf("Tomato", "Carrot", "Broccoli", "Apple", "Orange", "Banana")
    val popularCountries = listOf("France", "Italy", "Spain", "Japan", "India", "Mexico")
    val allIngredients = ingredientViewModel.ingredients.value
    val allCountries = countryViewModel.countries.value
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        ingredientViewModel.fetchIngredients()
        countryViewModel.fetchCountries()
    }

    val filteredIngredients = if (searchText.isBlank()) popularIngredients else allIngredients.filter {
        it.contains(searchText, ignoreCase = true)
    }
    val filteredCountries = if (searchText.isBlank()) popularCountries else allCountries.filter {
        it.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Browse",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Conteneur scrollable pour les résultats
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Grille des ingrédients
            if (filteredIngredients.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text(
                        text = "Ingredients",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { sheetStack.push { IngredientsSheet(sheetStack, filteredIngredients.first()) } }
                    )
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "More", Modifier.size(30.dp))
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filteredIngredients) { ingredient ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { sheetStack.push { IngredientsSheet(sheetStack, ingredient) } }
                                .padding(8.dp)
                        ) {
                            val iconRes = when (ingredient) {
                                "Tomato" -> R.drawable.tomato
                                "Carrot" -> R.drawable.carrot
                                "Broccoli" -> R.drawable.broccoli
                                "Apple" -> R.drawable.apple
                                "Orange" -> R.drawable.orange
                                "Banana" -> R.drawable.banana
                                else -> R.drawable.france
                            }
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = ingredient,
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                text = ingredient,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            // Grille des pays
            if (filteredCountries.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text(
                        text = "Countries",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { sheetStack.push { CountriesSheet(sheetStack, filteredCountries.first()) } }
                    )
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "More", Modifier.size(30.dp))
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filteredCountries) { country ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { sheetStack.push { CountriesSheet(sheetStack, country) } }
                                .padding(8.dp)
                        ) {
                            val iconRes = when (country) {
                                "France" -> R.drawable.france
                                "Italy" -> R.drawable.italy
                                "Spain" -> R.drawable.spain
                                "Japan" -> R.drawable.japan
                                "India" -> R.drawable.india
                                "Mexico" -> R.drawable.mexico
                                else -> R.drawable.france
                            }
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = country,
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                text = country,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Barre de recherche
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search ingredients or countries") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}


@Composable
fun CategoryGrid(
    items: List<Pair<Int, String>>,
    onItemClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { (iconRes, label) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onItemClick(label) }
                    .padding(8.dp)
            ) {
                val icon: Painter = painterResource(id = iconRes)
                Image(
                    painter = icon,
                    contentDescription = label,
                    modifier = Modifier.size(64.dp)
                )
                Text(text = label, fontSize = 14.sp)
            }
        }
    }
}