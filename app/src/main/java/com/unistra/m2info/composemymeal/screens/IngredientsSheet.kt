package com.unistra.m2info.composemymeal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.unistra.m2info.composemymeal.R
import com.unistra.m2info.composemymeal.layout.CustomRow
import com.unistra.m2info.composemymeal.layout.SheetStack


@Composable
fun IngredientsSheet(sheetStack: SheetStack) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val ingredients = listOf("Tomato", "Carrot", "Broccoli", "apple", "orange", "banana")
        var selectedTabIndex by remember { mutableStateOf(0) }
        CustomRow(
            items = ingredients,
            selectedIndex = selectedTabIndex,
            onTabSelected = { index -> selectedTabIndex = index }
        )

        Text(
            text = "View Ingredient description, allergies, etc. >",
            fontSize = 14.sp,
            color = Color.Blue,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {}
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(6) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(Color.LightGray)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tomato),
                            contentDescription = "Special recipe",
                            modifier = Modifier.size(80.dp)
                        )
                        Text(text = "Special recipe", fontSize = 14.sp)
                    }
                }
            }
        }

        OutlinedTextField(
            value = "",
            onValueChange = { },
            placeholder = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}
