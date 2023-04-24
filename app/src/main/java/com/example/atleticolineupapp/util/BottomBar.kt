package com.example.atleticolineupapp.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.ui.tab.SheetViewModel
import com.example.atleticolineupapp.util.drop.DropContainer

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    openPlayerSheet: () -> Unit,
    openFormationSheet: () -> Unit,
    vm: SheetViewModel = viewModel()
) {
    var isDroppingItem by remember { mutableStateOf(false) }
    var isItemInBounds by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        DropContainer(
            modifier = Modifier,
            onDrag = { isBounds, isDragging ->
                isItemInBounds = isBounds
                isDroppingItem = isDragging
            }
        ) {
            if (isDroppingItem && isItemInBounds) {
                LaunchedEffect(Unit) {
                    vm.onOpenSheet()
                }
            }
            IconButton(
                onClick = openPlayerSheet
            ) {
                Icon(Icons.Filled.Person, contentDescription = "", tint = Color.Red)
            }
        }
        IconButton(
            onClick = openFormationSheet
        ) {
            Icon(Icons.Filled.List, contentDescription = "", tint = Color.Red)
        }
    }
}