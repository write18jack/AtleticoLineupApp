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
import org.burnoutcrew.reorderable.NoDragCancelledAnimation
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: PlayersTabViewModel = viewModel(),
    vm2: FormationViewModel = viewModel()
) {
    //state&scope of bottomSheet
    val sheetState = rememberSheetState()
    val scope = rememberCoroutineScope()
    //bottomSheet expanded state
    var isDroppingItem by remember { mutableStateOf(true) }
    var isItemInBounds by remember { mutableStateOf(true) }

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
                            PlayerSlotSheet(players = vm.players)
                        }
                        scope.launch {
                            sheetState.show()
                        }
                    },
                    openFormationSheet = {
                        bottomSheetContent = {
                            FormationSheet(
                                onCLickTask = { vm2.constraintSetItem }
                            )
                        }
                        scope.launch {
                            sheetState.show()
                        }
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
                    if (sheetState.isVisible) {
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
                            bottomSheetContent?.let { it() }
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