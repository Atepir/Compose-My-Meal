package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.unistra.m2info.composemymeal.BrowseSheet
import com.unistra.m2info.composemymeal.layout.CustomRow
import com.unistra.m2info.composemymeal.layout.SheetStack

@Composable
fun BrowseMainSheet(
    sheetStack: SheetStack,
) {
    val browseNavigationItems = listOf("Browse", "Search")
    var selectedTabIndex by remember { mutableStateOf(0) }

    fun onTabSelected(index: Int) {
        selectedTabIndex = index
    }

    var handled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .imePadding()
            .pointerInput(Unit) {
                detectHorizontalDragGestures (
                    onDragEnd = { handled = false }, // Reset the flag when the drag ends
                    onHorizontalDrag = { _, dragAmount ->
                        if (!handled) {
                            when {
                                dragAmount < -30 && selectedTabIndex < browseNavigationItems.size - 1 -> {
                                    onTabSelected(selectedTabIndex + 1)
                                    handled = true
                                }
                                dragAmount > 30 && selectedTabIndex > 0 -> {
                                    onTabSelected(selectedTabIndex - 1)
                                    handled = true
                                }
                            }
                        }
                    })
            }
    ) {
        CustomRow(
            items = browseNavigationItems,
            selectedIndex = selectedTabIndex,
            onTabSelected = {it -> onTabSelected(it)}
        )

        if (selectedTabIndex == 0) {
            BrowseSheet(sheetStack)
        } else {
            SearchAllSheet(sheetStack)
        }
    }
}