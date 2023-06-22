package com.example.atleticolineupapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.util.drag.DragContainer
import com.example.atleticolineupapp.util.drag.DragData
import com.example.atleticolineupapp.util.drag.DragTargetInfo
import com.example.atleticolineupapp.util.drag.LocalDragTargetInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
private fun DraggableTextLowLevel() {

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .background(Color.Blue)
            .size(50.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    )
}

@Composable
fun DragTargetX(
    modifier: Modifier,
    dragData: DragData,
    content: @Composable (() -> Unit)
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    Box(modifier = modifier
        //get layout size
        .onGloballyPositioned {
            currentPosition = it.localToWindow(Offset.Zero)
        }
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDragStart = {
                    currentState.dragData = dragData
                    currentState.isDragging = true
                    currentState.dragPosition = currentPosition + it
                    //動かすitemの実体
                    currentState.draggableComposable = content
                }, onDrag = { change, dragAmount ->
                    //どこかで消費されたら
                    change.consume()
                    currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
                }, onDragEnd = {
                    currentState.isDragging = false
                    currentState.dragOffset = Offset.Zero
                }, onDragCancel = {
                    currentState.dragOffset = Offset.Zero
                    currentState.isDragging = false
                })
        }) {
        content()
    }
}

@Preview
@Composable
fun SampleDrag() {
    Box(modifier = Modifier.fillMaxSize()) {
        DragContainer(modifier = Modifier.fillMaxSize()) {
            DraggableTextLowLevel()
        }
    }
}