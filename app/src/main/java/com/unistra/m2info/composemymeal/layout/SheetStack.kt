package com.unistra.m2info.composemymeal.layout
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

/**
 * A stack for managing multiple bottom sheets.
 */
class SheetStack {
    private val stack = mutableStateListOf<@Composable () -> Unit>()

    // Check if the stack is not empty
    val isEmpty: Boolean get() = stack.isEmpty()

    // Add a new sheet to the stack
    fun push(sheet: @Composable () -> Unit) {
        stack.add(sheet)
    }

    // Remove the top sheet from the stack
    fun pop() {
        if (stack.isNotEmpty()) stack.removeAt(stack.lastIndex)
    }

    // Get the top-most sheet content
    @Composable
    fun peek(): (@Composable () -> Unit)? = stack.lastOrNull()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetStackManager(sheetStack: SheetStack) {
    val coroutineScope = rememberCoroutineScope()

    // If there's a sheet in the stack, show it
    sheetStack.peek()?.let { content ->
        Sheet(
            content = content,
            onDismissRequest = {
                coroutineScope.launch {
                    sheetStack.pop() // Remove the sheet from the stack
                }
            }
        )
    }
}
