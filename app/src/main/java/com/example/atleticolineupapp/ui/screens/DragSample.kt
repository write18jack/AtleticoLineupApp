package com.example.atleticolineupapp.ui.screens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atleticolineupapp.util.drag.DragContainer
import com.example.atleticolineupapp.util.drag.DragData
import com.example.atleticolineupapp.util.drag.LocalDragTargetInfo
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

@Composable
fun DragTest() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val state = rememberDraggableState(onDelta = { it ->
        Log.d(ContentValues.TAG, "$it")
    })

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = "x:${offsetX}\ny:${offsetY}",
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
                .background(color = Color.Red),
            fontSize = 20.sp
        )
    }
}

@Composable
fun Names() {
    var name by remember { mutableStateOf("user") }
    val names = remember { mutableStateListOf<String>() }

    Column {
        Row {
            BasicTextField(
                value = name,
                onValueChange = { name = it }
            )
            Button(onClick = { names.add(name) }) {
                Text("Add")
            }
        }
        Text("Added names:")
        Column {
            for (addedName in names) {
                Text(addedName)
            }
        }
    }
}

@Preview
@Composable
fun SampleDrag() {
    Box(modifier = Modifier.fillMaxSize()) {
        Names()
    }
}