package com.example.atleticolineupapp.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.R
import com.example.atleticolineupapp.ui.tab.*
import com.example.atleticolineupapp.util.BottomBar
import com.example.atleticolineupapp.util.drag.DragContainer
import com.example.atleticolineupapp.util.drop.DropContainer
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Close
import com.example.atleticolineupapp.util.captureBitmap
import com.example.atleticolineupapp.util.saveBitmap
import com.example.atleticolineupapp.util.saveImage
import com.example.atleticolineupapp.util.shareImage
import com.example.atleticolineupapp.util.shareImageUri
import dev.shreyaspatil.capturable.Capturable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: PlayersTabViewModel = viewModel(),
    vm2: FormationTabViewModel = viewModel()
) {
    //state&scope of bottomSheet
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    //bottomSheet expanded state
    var isDroppingItem by remember { mutableStateOf(true) }
    var isItemInBounds by remember { mutableStateOf(true) }

    val lazyGridState = rememberLazyGridState()

    var bottomSheetContent: (@Composable () -> Unit)? by remember {
        mutableStateOf(null)
    }
    val constraintSetItem by vm2.constraintSetItem.collectAsState()
    //ScreenShot
    val captureController = rememberCaptureController()

    // This will hold captured bitmap
    // So that we can demo it
    var formationBitmap: ImageBitmap? by remember { mutableStateOf(null) }

    val context = LocalContext.current

//    var snapShot: () -> Bitmap? = { null }

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

    // When Ticket's Bitmap image is captured, show preview in dialog
    formationBitmap?.let { ImageBitmap ->
        BitmapDialog(
            closeDialog = { formationBitmap = null },
            imageBitmap = ImageBitmap.asAndroidBitmap(),
            context = context
        )
    }

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
                        modifier = Modifier.border(width = 3.dp, color = Color.Blue),
                        backgroundColor = Color.Red,
                        elevation = 8.dp
                    ) {
                        Box(
                            Modifier
                                .weight(0.5f)
                                .background(color = Color.Green)
                        ) {

                        }
                        Box(
                            Modifier
                                .weight(1f)
                                .size(50.dp)
                                .paint(painter = painterResource(id = R.drawable.atletico_logo))
                        ) {

                        }
                        IconButton(
                            modifier = Modifier.weight(0.5f),
                            onClick = { captureController.capture() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    }
                },
                bottomBar = {
                    BottomBar(
                        openPlayerSheet = {
                            bottomSheetContent = {
                                PlayerSlotSheet(
                                    lazyGridState = lazyGridState,
                                    coroutineScope = scope,
                                    players = vm.players
                                )
                            }
                            scope.launch {
                                sheetState.show()
                            }
                        },
                        openFormationSheet = {
                            bottomSheetContent = {
                                FormationTab(
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
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    Capturable(
                        controller = captureController,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(alignment = Alignment.Center),
                        onCaptured = { imageBitmap, _ -> formationBitmap = imageBitmap }
                    ) {
                        DisplayFormation(
                            modifier = Modifier.paint(
                                painter = painterResource(id = R.drawable.pitch),
                                contentScale = ContentScale.FillBounds
                            ),
                            manageFormation = constraintSetItem
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BitmapDialog(
    closeDialog: () -> Unit,
    imageBitmap: Bitmap,
    context: Context
) {
//    val uri = saveBitmap(context, imageBitmap)

    val coroutineScope = rememberCoroutineScope()


//    val sendIntent = Intent().apply {
//        action = Intent.ACTION_SEND
//        putExtra(
//            Intent.EXTRA_STREAM,  // name: 格納するデータの名称
//            "text send" // value: 格納するデータ
//        )
//        type = "image/*" // image/x-ms-bmp
//    }
//    val shareIntent = Intent.createChooser(sendIntent, null)

    Dialog(onDismissRequest = { }) {
        Column(
            modifier = Modifier
                .background(LightGray)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Preview of image \uD83D\uDC47")
            Spacer(Modifier.size(13.dp))
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = "Preview of image",
            )
            Spacer(Modifier.size(4.dp))
            Row(horizontalArrangement = Arrangement.Center) {

                IconButton(onClick = closeDialog) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = {
//                        MainScope().launch {
//                            val bitmap = captureBitmap.invoke()
//                            val uri = saveImage(bitmap, context)
//                            if (uri != null) {
//                                shareImageUri(context, uri)
//                            } else {
//                                Toast.makeText(
//                                    context,
//                                    "uri is null",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
                    },
                    Modifier.paint(painterResource(id = R.drawable.download_icon))
                ) {
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            shareNoteImage(imageBitmap, context)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }


        }
    }
}
suspend fun shareNoteImage(bitmap: Bitmap, context: Context) {
    val uri = saveImage(bitmap, context)
    if (uri != null) {
        shareImageUri(context, uri)
    } else {
        Toast.makeText(
            context,
            "uri is null",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMe() {
    MainScreen()
}