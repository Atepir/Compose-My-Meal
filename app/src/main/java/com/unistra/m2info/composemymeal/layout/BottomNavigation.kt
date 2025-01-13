package com.unistra.m2info.composemymeal.layout

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.unistra.m2info.composemymeal.BrowseSheet
import com.unistra.m2info.composemymeal.components.BrowseMainSheet
import com.unistra.m2info.composemymeal.ui.theme.UbuntuFontFamily

@Composable
fun BottomNavigation(sheetStack: SheetStack, navController: NavController) { // Passer NavController
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        FloatingActionButton(
            onClick = {
                sheetStack.push { BrowseMainSheet(sheetStack) }
            },
            containerColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            contentColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
            modifier = Modifier.fillMaxWidth(0.45f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp).fillMaxWidth()
            ) {
                Text(text = "Explore", fontFamily = UbuntuFontFamily, fontSize = 16.sp, modifier = Modifier.padding(end = 4.dp))
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", Modifier.size(24.dp))
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("suggestion") }, // Naviguer vers "suggestion"
            containerColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            contentColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp).fillMaxWidth()
            ) {
                Text(text = "Surprise Me", fontFamily = UbuntuFontFamily, fontSize = 16.sp, modifier = Modifier.padding(end = 4.dp))
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Surprise", Modifier.size(24.dp))
            }
        }
    }
}
