package com.unistra.m2info.composemymeal.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Sheet(
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            // Close button at the top of the sheet
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        sheetState.hide() // Close the sheet
                        onDismissRequest()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Close Sheet")
            }

            // Dynamically render the provided content
            content()
        }
    ) {
        // Trigger sheet from parent UI
        Button(
            onClick = {
                coroutineScope.launch {
                    sheetState.show() // Open the sheet
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Open Bottom Sheet")
        }
    }
}
