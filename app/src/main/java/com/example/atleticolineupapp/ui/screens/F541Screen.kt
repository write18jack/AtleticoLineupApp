package com.example.atleticolineupapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.ui.formation.F442
import com.example.atleticolineupapp.ui.tab.FormationSheet
import com.example.atleticolineupapp.ui.tab.FormationViewModel
import com.example.atleticolineupapp.ui.tab.PlayerSlotSheet
import com.example.atleticolineupapp.ui.tab.SheetViewModel
import com.example.atleticolineupapp.util.BottomBar
import com.example.atleticolineupapp.util.drop.DropContainer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BottomSample(vm2: FormationViewModel = viewModel()) {
    val sheetState = rememberSheetState(

    )
    val scope = rememberCoroutineScope()
    var bottomSheetContent: (@Composable () -> Unit)? by remember {
        mutableStateOf(null)
    }
    var isDroppingItem by remember { mutableStateOf(true) }
    var isItemInBounds by remember { mutableStateOf(true) }

    val constraintSetItem by vm2.constraintSetItem.collectAsState()

    //formationTabタップ時にclose
    LaunchedEffect(key1 = constraintSetItem, block = {
        sheetState.hide()
    })

    Scaffold(
        topBar = {},
        bottomBar = {
            BottomBar(
                openPlayerSheet = {
                    bottomSheetContent = {
                        PlayerSlotSheet(modifier = Modifier)
                    }
                    scope.launch {
                        sheetState.show()
                    }
                }, openFormationSheet = {
                    bottomSheetContent = {
                        FormationSheet(onCLickTask = {
                            vm2.constraintSetItem
                        })
                    }
//                    vm.onOpenSheet()
                    scope.launch {
                        sheetState.show()
                    }
                })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            DisplayFormation(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                manageFormation = constraintSetItem
            )

            DropContainer(
                modifier = Modifier,
                onDrag = { isBounds, isDragging ->
                    isItemInBounds = isBounds
                    isDroppingItem = isDragging
                }
            ) {
                if (isDroppingItem && !isItemInBounds) {
                    if (sheetState.isVisible) {
                        //itemのDragでPlayerTabを出るとclose
                    if (isDroppingItem && !isItemInBounds) {
                        LaunchedEffect(Unit) {
                            sheetState.hide()
                        }
                    }
                        //formationTabタップ時にclose
                    LaunchedEffect(key1 = constraintSetItem, block = {
                        sheetState.hide()
                    })
                        ModalBottomSheet(
                            sheetState = sheetState,
                            onDismissRequest = {
                                scope.launch {
                                    sheetState.hide()
                                }
                            },
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent
                        ) {
                            Box(
                                modifier = Modifier.defaultMinSize(minHeight = 1.dp)
                            ) {
                                bottomSheetContent?.let { it() }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun Sample() {
    Box(modifier = Modifier.fillMaxSize()) {
        BottomSample()
    }
}