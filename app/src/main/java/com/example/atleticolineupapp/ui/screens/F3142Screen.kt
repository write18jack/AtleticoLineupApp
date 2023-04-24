package com.example.atleticolineupapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.ui.formation.F442
import com.example.atleticolineupapp.ui.tab.FormationSheet
import com.example.atleticolineupapp.ui.tab.PlayerSlotSheet
import com.example.atleticolineupapp.ui.tab.SheetViewModel
import com.example.atleticolineupapp.util.BottomBar
import com.example.atleticolineupapp.util.drop.DropContainer
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewMethod1() {
    TestScreenX()
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TestScreenX() {
//    val state = vm.sheetHandler.handle()
    var bottomSheetContent: (@Composable () -> Unit)? by remember {
        mutableStateOf(null)
    }
    val sheetState = rememberSheetState()
    val sheetState2 = rememberSheetState()
    val scope = rememberCoroutineScope()

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
                            modifier = Modifier
                        )
                    }
                    scope.launch {
                        sheetState.show()
                    }
                },
                openFormationSheet = {
                    bottomSheetContent = {
                        FormationSheet(
                            onCLickTask = {

                            }
                        )
                    }

//                        scope.launch {
//                            sheetState.show()
//                        }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                    }
                }
            ) {
                bottomSheetContent?.let { it() }
            }
        }
//            DisplayFormation(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                manageFormation = F442()
//            )
    }

}
