package com.whitebeach.presentation.ui.main

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel

data class PositionState(
    val id: Int? = null,
    var dragImage: Painter? = null,
    var isItemInBounds: Boolean = false,
    var isDroppingItem: Boolean = false
)

val PositionStates: List<PositionState>  = List(11){
    PositionState()
}

@Stable
class PositionStateViewModel: ViewModel(){
    val positionStateList by mutableStateOf(List(11){
        PositionState(
            id = it,
            dragImage = PositionStates[it].dragImage,
            isItemInBounds = PositionStates[it].isItemInBounds,
            isDroppingItem = PositionStates[it].isDroppingItem
        )
    })
}