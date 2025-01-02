package com.unistra.m2info.composemymeal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
fun BrowseSheet(sheetStack: SheetStack, viewModel: IngredientsViewModel = remember { IngredientsViewModel() }) {
    val ingredients = viewModel.ingredients.value

    LaunchedEffect(Unit) {
        viewModel.fetchIngredients()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Browse",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (ingredients.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(6.dp)
            ){
                Text(text = "Ingredients",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { sheetStack.push { IngredientsSheet(sheetStack, ingredients.first()) } }
                )
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "More", Modifier.size(30.dp))
            }
        } else {
            Text(
                text = "Loading ingredients...",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(8.dp)
            )
        }

        val ingredientIcons = listOf(
            R.drawable.tomato to "Tomato",
            R.drawable.carrot to "Carrots",
            R.drawable.broccoli to "Broccoli",
            R.drawable.apple to "Apple",
            R.drawable.orange to "Orange",
            R.drawable.banana to "Banana"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(ingredientIcons) { (iconRes, ingredient) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { sheetStack.push { IngredientsSheet(sheetStack, ingredient) } }
                        .padding(8.dp)
                ) {
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

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(6.dp)
        ){
            Text(text = "Countries",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                .clickable { sheetStack.push({ CountriesSheet(sheetStack)}) }
            )
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "More", Modifier.size(30.dp))
        }
        CategoryGrid(
            items = listOf(
                R.drawable.france to "France",
                R.drawable.italy to "Italy",
                R.drawable.spain to "Spain",
                R.drawable.japan to "Japan",
                R.drawable.india to "India",
                R.drawable.mexico to "Mexico"
            )
        ) {
            println("Clicked on country: $it")
        }
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
