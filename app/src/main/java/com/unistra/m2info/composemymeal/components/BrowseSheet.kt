package com.unistra.m2info.composemymeal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.unistra.m2info.composemymeal.layout.SheetStack
import com.unistra.m2info.composemymeal.components.CountriesSheet
import com.unistra.m2info.composemymeal.components.IngredientsSheet
import com.unistra.m2info.composemymeal.components.SearchInputField
import com.unistra.m2info.composemymeal.utils.countryIconMap
import com.unistra.m2info.composemymeal.viewmodels.CountriesViewModel
import com.unistra.m2info.composemymeal.viewmodels.IngredientsViewModel

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
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 48.dp)
        ) {
            LazyColumn(
                modifier = Modifier
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


        }

        Box(
            modifier = Modifier.align(Alignment.BottomCenter).background(Color.Transparent)
        ) {
            SearchInputField(searchText, { searchText = it })
        }
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
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally),
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
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

