package com.example.atleticolineupapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.atleticolineupapp.presentations.dragAndDrop.DragContainer
import com.example.atleticolineupapp.presentations.dragAndDrop.DragTargetInfo
import com.example.atleticolineupapp.presentations.dragAndDrop.DropContainer
import com.example.atleticolineupapp.presentations.navigation.F442
import com.example.atleticolineupapp.presentations.navigation.NavGraph
import com.example.atleticolineupapp.presentations.navigation.formationScreens
import com.example.atleticolineupapp.presentations.navigation.navigateSingleTopTo
import com.example.atleticolineupapp.presentations.tab.FormationSheet
import com.example.atleticolineupapp.presentations.tab.PlayerSheet
import com.example.atleticolineupapp.presentations.theme.AtleticoLineupAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        setContent {
            AtleticoLineupAppTheme {
                LineupApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LineupApp(vm: SheetViewModel = viewModel()) {

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = formationScreens.find { it.route == currentDestination?.route } ?: F442
    val state = vm.sheetHandler.handle()
    var bottomSheetContent: (@Composable () -> Unit)? by remember {
        mutableStateOf(null)
    }
    var isDroppingItem by remember { mutableStateOf(true) }
    var isItemInBounds by remember { mutableStateOf(true) }

    BackHandler(state.isVisible) {
        vm.onHideSheet()
    }

    DragContainer(
        modifier = Modifier.fillMaxSize()
    ) {
        ModalBottomSheetLayout(
            sheetState = state,
            sheetBackgroundColor = Color.Transparent,
            sheetContent = {
                DropContainer(
                    modifier = Modifier,
                    onDrag = { isBounds, isDragging ->
                        isItemInBounds = isBounds
                        isDroppingItem = isDragging
                    }
                ) {
                    if (isDroppingItem && !isItemInBounds) {
                        LaunchedEffect(Unit) {
                            Log.d("State", "$isItemInBounds")
                            vm.onHideSheet()
                        }
                    }
                    Box(
                        modifier = Modifier.defaultMinSize(minHeight = 1.dp)
                    ) {
                        Log.d("CONFIRM", "$it sheet!")
                        bottomSheetContent?.let { it() }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Atletico de Madrid") },
                        colors = TopAppBarDefaults.topAppBarColors(Color.Red)
                    )
                },
                bottomBar = {
                    BottomBar(
                        modifier = Modifier.background(Color.Black),
                        openPlayerSheet = {
                            bottomSheetContent = {
                                PlayerSheet(modifier = Modifier)
                            }
                            vm.onOpenSheet()
                        },
                        openFormationSheet = {
                            bottomSheetContent = {
                                FormationSheet(
                                    modifier = Modifier,
                                    allScreens = formationScreens,
                                    onTabSelected = { newScreen ->
                                        navController.navigateSingleTopTo(newScreen.route)
                                    },
                                    currentScreen = currentScreen
                                )
                            }
                            vm.onOpenSheet()
                        }
                    )
                }
            ) { paddingValues ->
                NavGraph(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}

class SheetViewModel : ViewModel() {
    @OptIn(ExperimentalMaterialApi::class)
    fun onOpenSheet() {
        sheetHandler.state {
            animateTo(
                ModalBottomSheetValue.Expanded
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun onHideSheet() {
        sheetHandler.state {
            hide()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    val sheetHandler = SheetHandler(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = {
            true
        }
    )
}

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

@Preview(showBackground = true)
@Composable
fun PreviewMethod() {
    AtleticoLineupAppTheme {
        LineupApp()
    }
}