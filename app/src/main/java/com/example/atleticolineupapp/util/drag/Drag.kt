package com.example.atleticolineupapp.util.drag

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atleticolineupapp.R
import com.example.atleticolineupapp.dragAndDrop.MimeType
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    var dragData by mutableStateOf<DragData?>(null)
}

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun DragContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val state = remember { DragTargetInfo() }
    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(modifier = modifier.fillMaxSize())
        {
            content()
            // DraggingItem
            if (state.isDragging) {
                var targetSize by remember {
                    mutableStateOf(IntSize.Zero)
                }
                Box(modifier = Modifier
                    .graphicsLayer {
                        val offset = (state.dragPosition + state.dragOffset)
                        alpha = if (targetSize == IntSize.Zero) 0f else .9f
                        translationX = offset.x.minus(targetSize.width / 2)
                        translationY = offset.y.minus(targetSize.height / 2)
                    }
                    .onGloballyPositioned {
                        targetSize = it.size
                    }
                ) {
                    state.draggableComposable?.invoke()
                }
            }
        }
    }
}

@Composable
fun DragTarget(
    modifier: Modifier,
    dragData: DragData,
    content: @Composable (() -> Unit)
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    Box(
        modifier = modifier
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
                        //Log.d("Drag", "cordinate: ${dragAmount.x}, ${dragAmount.y}")
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
fun Gesture() {
    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown()
                }
                coroutineScope {
                    while (true) {
                        // Detect a tap event and obtain its position.
                        awaitPointerEventScope {
                            val position = awaitFirstDown().position

                            launch {
                                // Animate to the tap position.
                                offset.animateTo(position)
                            }
                        }
                    }
                }
            }
    ) {
        Box(modifier = Modifier.offset { offset.value.toIntOffset() }) {
            Text(text = "VV", fontSize = 20.sp)
        }
    }
}
private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

@Composable
private fun DraggableTextLowLevel(dragData: DragData) {
    Box(modifier = Modifier.fillMaxSize()) {
        var currentPosition by remember { mutableStateOf(Offset.Zero) }
        val currentState = LocalDragTargetInfo.current
        Box(
            Modifier
                .onGloballyPositioned {
                    currentPosition = it.localToWindow(Offset.Zero)
                }
                .size(50.dp)
                .paint(painterResource(id = R.drawable.griezmann))
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = {
                            currentState.dragData = dragData
                        },
                        onDrag = { change: PointerInputChange, dragAmount: Offset ->
                            change.consume()
                            currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
                        },
                        onDragEnd = {
                            currentState.dragOffset = Offset.Zero
                        },
                        onDragCancel = {
                            currentState.isDragging = false
                        }
                    )
                }
        )
    }
}

@Composable
private fun DraggableTextLowLevel2() {
    Box(modifier = Modifier.fillMaxSize()) {
//        var offsetX by remember { mutableStateOf(400f) }
//        var offsetY by remember { mutableStateOf(200f) }
        var currentPosition by remember { mutableStateOf(Offset.Zero) }
        var dragOffset by remember{ mutableStateOf(Offset.Zero) }

        Box(
            Modifier
                .onGloballyPositioned {
                    currentPosition = it.localToWindow(Offset.Zero)
                }
//                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDrag = { change: PointerInputChange, dragAmount: Offset ->
                            change.consume()
//                            offsetX += dragAmount.x
//                            offsetY += dragAmount.y
                            dragOffset += Offset(dragAmount.x, dragAmount.y)
                        }
                    )
                }
        ){
            Image(
                painter = painterResource(R.drawable.griezmann),
                contentDescription = "Griezmann"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun gg() {
    val dragImage = R.drawable.griezmann
    val dragData = DragData(type = MimeType.IMAGE_JPEG, data = dragImage)
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        DraggableTextLowLevel2()
    }
}