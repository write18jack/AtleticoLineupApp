package com.whitebeach.presentation.main

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.whitebeach.presentation.LoadingDialog
import com.whitebeach.presentation.R
import com.whitebeach.presentation.component.AdaptiveBanner
import com.whitebeach.presentation.component.dragDrop.DropTarget
import com.whitebeach.presentation.formation.DisplayFormation
import com.whitebeach.presentation.formationSheet.FormationSheet
import com.whitebeach.presentation.formationSheet.formationItemList
import com.whitebeach.presentation.formationSheet.rememberFormation
import com.whitebeach.presentation.main.view.BottomBar
import com.whitebeach.presentation.playerSheet.PlayerSheetCheck
import com.whitebeach.presentation.playerSheet.RapidApiViewModel
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class, ExperimentalComposeApi::class
)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    positionStateViewModel: PositionStateViewModel = viewModel(),
    rapidApiViewModel: RapidApiViewModel = viewModel(),
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var bottomSheetContent: (@Composable () -> Unit)? by remember { mutableStateOf(null) }
    val formationState = rememberFormation()
    var isDroppingItem by remember { mutableStateOf(true) }
    var isItemInBounds by remember { mutableStateOf(true) }
    val captureController = rememberCaptureController()
    var formationBitmap: ImageBitmap? by remember { mutableStateOf(null) }
    var loadingDialogState by remember { mutableStateOf(false) }

    val playersUiState = rapidApiViewModel.playersUiState

    if (!isItemInBounds) {
        LaunchedEffect(Unit) {
            sheetState.hide()
        }
    }

    LaunchedEffect(formationState) {
        sheetState.hide()
    }

    if (loadingDialogState) {
        LoadingDialog(
            onDismissRequest = { loadingDialogState = false }
        )
    }

    LaunchedEffect(formationBitmap) {
        formationBitmap?.let { imageBitmap ->
            val imagesFolder = File(context.cacheDir, "images")
            var uri: Uri? = null
            try {
                imagesFolder.mkdirs()
                val file = File(imagesFolder, "shared_image.png")
                val stream = FileOutputStream(file)
                imageBitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 90, stream)
                stream.flush()
                stream.close()
                uri = FileProvider.getUriForFile(
                    context,
                    "com.atletico.file-provider",
                    file
                )
            } catch (e: IOException) {
                Log.d("Error", "IOException while trying to write file for sharing: " + e.message)
            }
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                type = "image/png"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            loadingDialogState = false
            startActivity(context, shareIntent, null)
        }
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
                CenterAlignedTopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.atletico_logo),
                            contentDescription = "",
                            modifier = Modifier.size(50.dp),
                        )
                    },
                    modifier = Modifier,
                    navigationIcon = {

                    },
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    formationBitmap = captureController.captureAsync().await()
                                    loadingDialogState = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = ""
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Red,
                        navigationIconContentColor = Color.Red,
                        actionIconContentColor = Color.White
                    )
                )
            },
            bottomBar = {
                Column {
                    AdaptiveBanner(modifier = Modifier)
                    BottomAppBar(
                        containerColor = Color.Red
                    ) {
                        BottomBar(
                            openPlayerSheet = {
                                scope.launch {
                                    sheetState.show()
                                    bottomSheetContent = {
                                        PlayerSheetCheck(
                                            modifier = Modifier,
                                            playersUiState = playersUiState
                                        )
                                    }
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
            }
        ) {
            DisplayFormation(
                modifier = Modifier
                    .capturable(captureController)
                    .padding(it)
                    .paint(
                        painter = painterResource(id = R.drawable.pitch),
                        contentScale = ContentScale.FillBounds
                    )
                    .fillMaxSize(),
                manageFormation = formationState.manageFormation,
                stateList = positionStateViewModel.positionStateList
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {

}
