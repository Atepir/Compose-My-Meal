package com.unistra.m2info.composemymeal.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Sheet(
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)
    val coroutineScope = rememberCoroutineScope()

    // Listen for changes to the sheet state
    LaunchedEffect(sheetState.isVisible) {
        if (!sheetState.isVisible) {
            onDismissRequest() // Trigger dismiss when the sheet is hidden
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            // Content of the sheet
            content()
        }
    ) {
        // Optionally, main screen content goes here
    }
}
