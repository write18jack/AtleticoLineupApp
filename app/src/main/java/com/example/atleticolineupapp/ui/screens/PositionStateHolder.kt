package com.example.atleticolineupapp.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

data class PositionState(
    var dragImage: Painter? = null,
    var isItemInBounds: Boolean = false,
    var isDroppingItem: Boolean = false
)

val PositionStates: List<PositionState>  = listOf(
    PositionState(),
    PositionState(),
    PositionState(),
    PositionState(),
    PositionState(),
    PositionState(),
    PositionState(),
    PositionState(),
    PositionState(),
    PositionState(),
    PositionState()
)

@Stable
class PositionStateViewModel: ViewModel(){

    val positionStateList by mutableStateOf(List(11){
        PositionState(
            dragImage = PositionStates[it].dragImage,
            isItemInBounds = PositionStates[it].isItemInBounds,
            isDroppingItem = PositionStates[it].isDroppingItem
        )
    })
}

class StateHolder(
    private val positionStateViewModel: PositionStateViewModel
){
    // viewModel変数はget @Composableかメソッド内で呼び出すことができる。
}

@Composable
fun rememberStateHolder(
    positionStateViewModel: PositionStateViewModel = viewModel()
) = remember {
    StateHolder(
        positionStateViewModel = positionStateViewModel
    )
}