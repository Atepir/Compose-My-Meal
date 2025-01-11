package com.unistra.m2info.composemymeal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.unistra.m2info.composemymeal.layout.SheetStack
import com.unistra.m2info.composemymeal.screens.CountriesSheet
import com.unistra.m2info.composemymeal.screens.IngredientsSheet

@Composable
fun BrowseSheet(
    sheetStack: SheetStack,
    ingredientViewModel: IngredientsViewModel = remember { IngredientsViewModel() },
    countryViewModel: CountriesViewModel = remember { CountriesViewModel() }
) {
    val popularIngredients = listOf("Tomato", "Plain Chocolate", "Broccoli", "Beef", "Orange", "Banana")
    val popularCountries = listOf("French", "Italian", "Spanish", "Japanese", "Indian", "Mexican")
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
        ) {
            item {
                Text(
                    text = "Browse",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }

            if (filteredIngredients.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Ingredients",
                        onClick = { sheetStack.push { IngredientsSheet(sheetStack, allIngredients.first()) } }
                    )
                }
                items(filteredIngredients.chunked(3)) { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        row.forEach { ingredient ->
                            Box(
                                modifier = Modifier
                                    .weight(1f, fill = true)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                IngredientItem(
                                    ingredient = ingredient,
                                    onClick = { sheetStack.push { IngredientsSheet(sheetStack, ingredient) } }
                                )
                            }
                        }
                        repeat(3 - row.size) {
                            Spacer(modifier = Modifier.weight(1f, fill = true))
                        }
                    }
                }
            }

            if (filteredCountries.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Countries",
                        onClick = { sheetStack.push { CountriesSheet(sheetStack, allCountries.first()) } }
                    )
                }
                items(filteredCountries.chunked(3)) { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        row.forEach { country ->
                            Box(
                                modifier = Modifier
                                    .weight(1f, fill = true)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CountryItem(
                                    country = country,
                                    onClick = { sheetStack.push { CountriesSheet(sheetStack, country) } }
                                )
                            }
                        }
                        repeat(3 - row.size) {
                            Spacer(modifier = Modifier.weight(1f, fill = true))
                        }
                    }
                }
            }
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search ingredients or countries") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun SectionHeader(title: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(6.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable { onClick() }
        )
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "More", Modifier.size(30.dp))
    }
}

@Composable
fun IngredientItem(ingredient: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        val imageUrl = "https://www.themealdb.com/images/ingredients/${ingredient}-Small.png"
        AsyncImage(
            model = imageUrl,
            contentDescription = ingredient,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = ingredient,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun CountryItem(country: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        val iconRes = countryIconMap[country] ?: R.drawable.france
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

val countryIconMap = mapOf(
    "French" to R.drawable.france,
    "Italian" to R.drawable.italy,
    "Spanish" to R.drawable.spain,
    "Japanese" to R.drawable.japan,
    "Indian" to R.drawable.india,
    "Mexican" to R.drawable.mexico,
    "American" to R.drawable.unitedstates,
    "British" to R.drawable.unitedkingdom,
    "Canadian" to R.drawable.canada,
    "Chinese" to R.drawable.china,
    "Croatian" to R.drawable.croatia,
    "Dutch" to R.drawable.germany,
    "Egyptian" to R.drawable.egypt,
    "Filipino" to R.drawable.philippines,
    "Greek" to R.drawable.greece,
    "Irish" to R.drawable.ireland,
    "Jamaican" to R.drawable.jamaica,
    "Kenyan" to R.drawable.kenya,
    "Malaysian" to R.drawable.malaysia,
    "Moroccan" to R.drawable.morocco,
    "Polish" to R.drawable.poland,
    "Portuguese" to R.drawable.portugal,
    "Russian" to R.drawable.russia,
    "Thai" to R.drawable.thailand,
    "Tunisian" to R.drawable.tunisia,
    "Turkish" to R.drawable.turkey,
    "Ukrainian" to R.drawable.ukraine,
    "Vietnamese" to R.drawable.vietnam
)