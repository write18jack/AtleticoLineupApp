package com.whitebeach.atleticolineupapp.presentations.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.whitebeach.atleticolineupapp.R
import com.whitebeach.atleticolineupapp.presentations.main.view.BottomBar
import com.whitebeach.atleticolineupapp.presentations.formation.DisplayFormation
import com.whitebeach.atleticolineupapp.presentations.main.view.TopAppBarItems
import com.whitebeach.atleticolineupapp.presentations.formationSheet.FormationSheet
import com.whitebeach.atleticolineupapp.presentations.playerSheet.PlayerSheet
import com.whitebeach.atleticolineupapp.presentations.formationSheet.formationItemList
import com.whitebeach.atleticolineupapp.presentations.formationSheet.rememberFormation
import com.whitebeach.atleticolineupapp.app.theme.AppTheme
import com.whitebeach.atleticolineupapp.presentations.main.view.BitmapDialog
import com.whitebeach.atleticolineupapp.app.component.dragDrop.DropTarget
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    fireStoreViewModel: FireStoreViewModel = viewModel(),
    positionStateViewModel: PositionStateViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var bottomSheetContent: (@Composable () -> Unit)? by remember { mutableStateOf(null) }
    val formationState = rememberFormation()
    //bottomSheet expanded state
    var isDroppingItem by remember { mutableStateOf(true) }
    var isItemInBounds by remember { mutableStateOf(true) }
    val captureController = rememberCaptureController()
    var formationBitmap: ImageBitmap? by remember { mutableStateOf(null) }

    if (!isItemInBounds) {
        LaunchedEffect(Unit) {
            sheetState.hide()
        }
    }

    LaunchedEffect(formationState) {
        sheetState.hide()
    }

    formationBitmap?.let { imageBitmap ->
        BitmapDialog(
            closeDialog = { formationBitmap = null },
            imageBitmap = imageBitmap.asAndroidBitmap(),
            context = LocalContext.current,
            coroutineScope = scope
        )
    }

    ModalBottomSheetLayout(
        sheetContent = {
            DropTarget(
                modifier = Modifier,
                onDrag = { isBounds, isDragging ->
                    isItemInBounds = isBounds
                    isDroppingItem = isDragging
                }
            ) {
                bottomSheetContent?.let { it() }
            }
        },
        modifier = modifier,
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent
    ) {
        Scaffold(
            topBar = {
                TopAppBarItems(
                    modifier = Modifier,
                    clickShare = {
                        captureController.capture()
                        //context.startActivity(shareIntent)
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color.Transparent
                ) {
                    BottomBar(
                        openPlayerSheet = {
                            scope.launch {
                                sheetState.show()
                            }
                            bottomSheetContent = {
                                PlayerSheet(list = fireStoreViewModel.state)
                            }
                        },
                        openFormationSheet = {
                            scope.launch {
                                sheetState.show()
                            }
                            bottomSheetContent = {
                                FormationSheet(
                                    list = formationItemList,
                                    onCLickTask = {
                                        formationState.changeFormation(it)
                                    }
                                )
                            }
                        }
                    )
                }
            }
        ) {
            Capturable(
                controller = captureController,
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.pitch),
                        contentScale = ContentScale.FillBounds
                    )
                    .fillMaxSize()
                    .padding(it),
                onCaptured = { imageBitmap, _ ->
                    formationBitmap = imageBitmap
                }
            ) {
                DisplayFormation(
                    modifier = Modifier.fillMaxSize(),
                    manageFormation = formationState.manageFormation,
                    stateList = positionStateViewModel.positionStateList
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    AppTheme {
        MainScreen()
    }
}
//@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun MainScreenX(
//    //vm: PlayersTabViewModel = viewModel(),
//    //vm2: RapidapiViewModel = viewModel(),
//    vm: FireStoreViewModel = hiltViewModel(),
//    vm3: PositionStateViewModel = viewModel(),
//    //vm: FireStoreViewModel = viewModel()
//) {
//    //val configuration = LocalConfiguration.current
//   // val heightInDp = configuration.screenHeightDp.dp
//
//    val tasks = vm.tasks.collectAsState(initial = emptyList())
//
//    //val playersList: List<ResponseX> by vm2.storePlayersList.collectAsState()
//
//    //state&scope of bottomSheet
//    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
//
//    // CallBack関数でCoroutineを使用する場合
//    val scope = rememberCoroutineScope()
//
//    //bottomSheet expanded state
//    var isDroppingItem by remember { mutableStateOf(true) }
//    var isItemInBounds by remember { mutableStateOf(true) }
//
//    //val lazyGridState = rememberLazyGridState()
//
//    var bottomSheetContent: (@Composable () -> Unit)? by remember {
//        mutableStateOf(null)
//    }
//    //val playerItemFlow by vm.playerItemFlow.collectAsState()
//
//    val formationSample = rememberFormation()
//
//    val stateList = vm3.positionStateList
//    //ScreenShot
//    val captureController = rememberCaptureController()
//
//    // This will hold captured bitmap
//    // So that we can demo it
//    var formationBitmap: ImageBitmap? by remember { mutableStateOf(null) }
//
//    //PlayerInfoDialog
//    var showDialog by remember { mutableStateOf(false) }
//
//    if (!sheetState.isVisible) {
//        LaunchedEffect(Unit) {
//            sheetState.hide()
//        }
//    }
//    //itemのDragでPlayerTabを出るとclose
//    if (!isItemInBounds) {
//        LaunchedEffect(Unit) {
//            sheetState.hide()
//        }
//    }
//    //formationTabタップ時にclose
//    LaunchedEffect(key1 = formationSample, block = {
//        sheetState.hide()
//    })
//
//    // When Ticket's Bitmap image is captured, show preview in dialog
//    formationBitmap?.let { imageBitmap ->
//        BitmapDialog(
//            closeDialog = { formationBitmap = null },
//            imageBitmap = imageBitmap.asAndroidBitmap(),
//            context = LocalContext.current,
//            coroutineScope = scope
//        )
//    }
//
////    if (showDialog) {
////        PlayerDialog(
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(heightInDp / 3 * 2),
////            playerInfo = playerItemFlow
////        ) {
////            showDialog = false
////        }
////    }
//
//    DragContainer(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        ModalBottomSheetLayout(
//            modifier = Modifier,
//            sheetState = sheetState,
//            sheetContent = {
//                DropTarget(
//                    modifier = Modifier,
//                    onDrag = { isBounds, isDragging ->
//                        isItemInBounds = isBounds
//                        isDroppingItem = isDragging
//                    }
//                ) {
//                    bottomSheetContent?.let { it() }
//                }
//            },
//            sheetBackgroundColor = Color.Transparent
//        ) {
//            Scaffold(
//                topBar = {
//                    BoxWithConstraints {
//                        val contentHeight = maxHeight
//                        androidx.compose.material.TopAppBar(
//                            modifier = Modifier.border(width = 3.dp, color = Color.Blue),
//                            backgroundColor = Color.Red,
//                            elevation = 8.dp
//                        ) {
//                            TopAppBarItems(
//                                modifier = Modifier,
//                                clickShare = {
//                                    captureController.capture()
//                                }
//                            )
//                        }
//                    }
//                },
//                bottomBar = {
//                    Column {
////                        AdaptiveBanner(modifier = Modifier)
//                        BottomBar(
//                            modifier = Modifier.background(Color.Blue),
//                            openPlayerSheet = {
//                                bottomSheetContent = {
//                                    PlayerSheet(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        list = tasks.value
////                                        onClick = { playerItem ->
////                                            showDialog = true
////                                            vm.onChangePlayerItem(playerItem)
////                                        }
//                                    )
//                                }
//                                scope.launch {
//                                    sheetState.show()
//                                }
//                            },
//                            openFormationSheet = {
//                                bottomSheetContent = {
//                                    FormationSheet(
//                                        list = formationItemList,
//                                        onCLickTask = { manageFormation ->
//                                            formationSample.changeFormation(manageFormation)
//                                        }
//                                    )
//                                }
//                                scope.launch {
//                                    sheetState.show()
//                                }
//                            }
//                        )
//                    }
//                }
//            ) { paddingValues ->
//                Capturable(
//                    controller = captureController,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues),
//                    onCaptured = { imageBitmap, _ ->
//                        formationBitmap = imageBitmap
//                    }
//                ) {
//                    DisplayFormation(
//                        modifier = Modifier
//                            .paint(
//                                painter = painterResource(id = R.drawable.pitch),
//                                contentScale = ContentScale.FillBounds
//                            )
//                            .fillMaxSize(),
//                        manageFormation = formationSample.manageFormation,
//                        stateList = stateList
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun ManagePlayersUiState(
//    playersUiState: PlayersUiState,
//) {
//    val emptyList = emptyList<ResponseX>().toMutableList()
//    when (playersUiState) {
//        is PlayersUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
//        is PlayersUiState.Success -> {
//            playersUiState.players.forEach {
//                if (it.player.name in displayPlayer) {
//                    emptyList.add(it)
//                }
//            }
////            PlayerSheet(
////                response =  emptyList
////            )
//        }
//
//        is PlayersUiState.Error -> ErrorScreen(retryAction = { /*TODO*/ })
//    }
//}
//
//@Composable
//fun LoadingScreen(modifier: Modifier = Modifier) {
//    Image(
//        modifier = modifier,
//        painter = painterResource(R.drawable.loading_img),
//        contentDescription = stringResource(R.string.loading)
//    )
//}
//
//@Composable
//fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
//        )
//        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
//        Button(onClick = retryAction) {
//            Text(stringResource(R.string.retry))
//        }
//    }
//}