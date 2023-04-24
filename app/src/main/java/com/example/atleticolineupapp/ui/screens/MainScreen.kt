package com.example.atleticolineupapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.ui.formation.ManageFormation
import com.example.atleticolineupapp.ui.tab.*
import com.example.atleticolineupapp.util.BottomBar
import com.example.atleticolineupapp.util.drag.DragContainer
import com.example.atleticolineupapp.util.drop.DropContainer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: SheetViewModelX = viewModel(),
    vm2: FormationViewModel = viewModel()
) {
    val sheetState = rememberSheetState()
    val scope = rememberCoroutineScope()

    var bottomSheetContent: (@Composable () -> Unit)? by remember {
        mutableStateOf(null)
    }

    val state = vm.sheetHandler.handle()
    val constraintSetItem by vm2.constraintSetItem.collectAsState()

    var isDroppingItem by remember { mutableStateOf(true) }
    var isItemInBounds by remember { mutableStateOf(true) }

    BackHandler(sheetState.isVisible) {
        scope.launch {
            sheetState.hide()
        }
    }

//    ModalBottomSheetLayout(
//        sheetState = sheetState,
//        sheetBackgroundColor = Color.Transparent,
//        sheetContent = {
//            DropContainer(
//                modifier = Modifier,
//                onDrag = { isBounds, isDragging ->
//                    isItemInBounds = isBounds
//                    isDroppingItem = isDragging
//                }
//            ) {
//                //itemのDragでPlayerTabを出るとclose
//                if (isDroppingItem && !isItemInBounds) {
//                    LaunchedEffect(Unit) {
//                        vm.onHideSheet()
//                    }
//                }
//                //formationTabタップ時にclose
//                LaunchedEffect(key1 = constraintSetItem, block = {
//                    vm.onHideSheet()
//                })
//                Box(
//                    modifier = Modifier.defaultMinSize(minHeight = 1.dp)
//                ) {
//                    bottomSheetContent?.let { it() }
//                }
//            }
//        }
//    )

    DragContainer(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Atletico de Madrid") },
                    colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent)
                )
            },
            bottomBar = {
                BottomBar(
                    openPlayerSheet = {
                        bottomSheetContent = {
                            PlayerSlotSheet(modifier = Modifier)
                        }
//                            sheetState.show()
                        vm.onOpenSheet()
                    },
                    openFormationSheet = {
                        bottomSheetContent = {
                            FormationSheet(
                                onCLickTask = { vm2.constraintSetItem }
                            )
                        }
//                            sheetState.show()
                        vm.onOpenSheet()
                    }
                )
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
                    //itemのDragでPlayerTabを出るとclose
                    if (isDroppingItem && !isItemInBounds) {
                        LaunchedEffect(Unit) {
                            //sheetState.hide()
                            vm.onHideSheet()
                        }
                    }
                    //formationTabタップ時にclose
                    LaunchedEffect(key1 = constraintSetItem, block = {
//                            sheetState.hide()
                        vm.onHideSheet()
                    })
                    if (sheetState.isVisible) {
                        ModalBottomSheet(
                            sheetState = state,
                            onDismissRequest = {
                                scope.launch {
//                                    sheetState.hide()
                                    vm.onHideSheet()
                                }
                            },
                            containerColor = Color.Transparent,
                            //contentColor = Color.Transparent
                        ) {
                            Box(
                                modifier = Modifier.defaultMinSize(minHeight = 1.dp)
                            ) {
                                bottomSheetContent?.let { it() }
                            }
//                            bottomSheetContent?.let { it() }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMethod() {
    MainScreen()
}