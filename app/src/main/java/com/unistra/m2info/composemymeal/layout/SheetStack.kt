package com.unistra.m2info.composemymeal.layout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

/**
 * A stack for managing multiple bottom sheets.
 */
class SheetStack {
    private val stack = mutableStateListOf<@Composable () -> Unit>()

    // Check if the stack is empty
    val isEmpty: Boolean get() = stack.isEmpty()

    // Add a new sheet to the stack
    fun push(sheet: @Composable () -> Unit) {
        stack.add(sheet) // Ajoute une nouvelle feuille à la pile
    }

    // Remove the top sheet from the stack
    fun pop() {
        if (stack.isNotEmpty()) stack.removeAt(stack.lastIndex) // Supprime la dernière feuille
    }

    // Get the top-most sheet content
    @Composable
    fun peek(): (@Composable () -> Unit)? = stack.lastOrNull()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetStackManager(sheetStack: SheetStack, content: @Composable () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        // Contenu principal
        content()

        // Afficher la SheetStack si elle n'est pas vide
        sheetStack.peek()?.let { sheetContent ->
            Sheet(
                content = sheetContent,
                onDismissRequest = {
                    coroutineScope.launch {
                        // when dismissed, dismiss all
                        while (!sheetStack.isEmpty) {
                            sheetStack.pop()
                        }
                    }
                }
            )
        }
    }

    // Gérer le bouton "Retour" pour fermer la SheetStack
    BackHandler(enabled = !sheetStack.isEmpty) {
        coroutineScope.launch {
            sheetStack.pop()
        }
    }
}

