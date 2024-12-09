package com.unistra.m2info.composemymeal.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unistra.m2info.composemymeal.BrowseSheet

@Composable
fun BottomNavigation(sheetStack: SheetStack) {
    // Buttons at the bottom
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        FloatingActionButton(
            onClick = { sheetStack.push { BrowseSheet(sheetStack) } },
            containerColor = Color.DarkGray,
            contentColor = Color.White,
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ){
                Text(text = "Browse", fontSize = 24.sp, modifier = Modifier.padding(end = 4.dp))
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", Modifier.size(30.dp))
            }

        }
        FloatingActionButton(
            onClick = { sheetStack.push { BrowseSheet(sheetStack) } },
            containerColor = Color.DarkGray,
            contentColor = Color.White,
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ){
                Text(text = "Surprise Me", fontSize = 24.sp, modifier = Modifier.padding(end = 4.dp))
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Surprise", Modifier.size(30.dp))
            }

        }
    }
}