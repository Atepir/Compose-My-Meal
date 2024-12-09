package com.unistra.m2info.composemymeal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unistra.m2info.composemymeal.layout.SheetStack
import com.unistra.m2info.composemymeal.screens.CountriesSheet
import com.unistra.m2info.composemymeal.screens.IngredientsSheet

@Composable
fun BrowseSheet(sheetStack: SheetStack) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Browse",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Ingredients",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .padding(bottom = 8.dp)
                .clickable { sheetStack.push({ IngredientsSheet(sheetStack) }) }
        )

        CategoryGrid(
            items = listOf(
                R.drawable.tomato to "Tomato",
                R.drawable.carrot to "Carrot",
                R.drawable.banana to "Banana",
                R.drawable.apple to "Apple",
                R.drawable.broccoli to "Broccoli",
                R.drawable.orange to "Orange"
            )
        ) {
            println("Clicked on ingredient: $it")
        }

        Text(
            text = "Countries",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(bottom = 8.dp)
                .clickable { sheetStack.push({ CountriesSheet(sheetStack) }) }
        )
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
