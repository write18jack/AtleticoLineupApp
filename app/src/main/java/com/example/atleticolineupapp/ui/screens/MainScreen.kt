package com.example.atleticolineupapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import org.burnoutcrew.reorderable.NoDragCancelledAnimation
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: PlayersTabViewModel = viewModel(),
    vm2: FormationViewModel = viewModel()
) {
    //state&scope of bottomSheet
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    //bottomSheet expanded state
    var isDroppingItem by remember { mutableStateOf(true) }
    var isItemInBounds by remember { mutableStateOf(true) }

    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    val reorderState = rememberReorderableLazyGridState(
        dragCancelledAnimation = NoDragCancelledAnimation(),
        onMove = vm::movePlayer
    )

    var bottomSheetContent: (@Composable () -> Unit)? by remember {
        mutableStateOf(null)
    }
    val constraintSetItem by vm2.constraintSetItem.collectAsState()

    //when click backButton
    BackHandler(sheetState.isVisible) {
        scope.launch {
            sheetState.hide()
        }
    }
    //itemのDragでPlayerTabを出るとclose
    if (!isItemInBounds) {
        LaunchedEffect(Unit) {
            sheetState.hide()
        }
    }
    //formationTabタップ時にclose
    LaunchedEffect(key1 = constraintSetItem, block = {
        sheetState.hide()
    })

    DragContainer(
        modifier = Modifier.fillMaxSize()
    ) {
        ModalBottomSheetLayout(
            modifier = Modifier,
            sheetState = sheetState,
            sheetContent = {
                DropContainer(
                    modifier = Modifier,
                    onDrag = { isBounds, isDragging ->
                        isItemInBounds = isBounds
                        isDroppingItem = isDragging
                    }
                ) {
                    bottomSheetContent?.let { it() }
                }
            },
            sheetBackgroundColor = Color.Transparent,
            sheetContentColor = Color.Transparent
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
                                PlayerSlotSheet(
                                    lazyGridState = lazyGridState,
                                    coroutineScope = coroutineScope,
                                    players = vm.players
                                )
                            }
                            scope.launch {
                                sheetState.show()
                            }
                        },
                        openFormationSheet = {
                            bottomSheetContent = {
                                FormationSheet(
                                    list = vm2.constraintSetList,
                                    onCLickTask = { ManageFormation ->
                                        vm2.onChangeConstraintItem(ManageFormation)
                                    }
                                )
                            }
                            scope.launch {
                                sheetState.show()
                            }
                        },
                        modalBottomSheetState = sheetState,
                        isDroppingItem = isDroppingItem,
                        isItemInBounds = isItemInBounds
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