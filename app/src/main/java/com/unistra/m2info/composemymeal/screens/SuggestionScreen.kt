package com.unistra.m2info.composemymeal.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.unistra.m2info.composemymeal.BrowseSheet
import com.unistra.m2info.composemymeal.layout.SheetStack
import androidx.compose.foundation.layout.* // For Column, Row, Box, and layout modifiers
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.* // For MaterialTheme, Text, Button, and other Material 3 components
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.unistra.m2info.composemymeal.layout.BottomNavigation


@Composable
fun SuggestionScreen(navController: NavController, sheetStack: SheetStack) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header text with primary color and h5 typography
        Text(
            text = "A very nice recipe!",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Box to represent the image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Image Placeholder",
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Ingredients section
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                repeat(3) {
                    Text(
                        text = "🍅 Tomato 1kg",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Description text
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        BottomNavigation(sheetStack)
    }
}
