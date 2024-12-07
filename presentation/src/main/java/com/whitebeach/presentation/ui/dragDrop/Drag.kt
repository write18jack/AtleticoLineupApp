package com.whitebeach.presentation.ui.dragDrop

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    var dragData by mutableStateOf<DragData?>(null)
    var itemDropped: Boolean by mutableStateOf(false)
    var absolutePositionX by mutableFloatStateOf(0f)
    var absolutePositionY by mutableFloatStateOf(0f)
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
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            content()
            //contentの上で表示指せるDraggingItemに関して
            if (state.isDragging) {

                var targetSize by remember { mutableStateOf(IntSize.Zero) }

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val offset = (state.dragPosition + state.dragOffset)
                            alpha = if (targetSize == IntSize.Zero) 0f else .9f
                            translationX = offset.x.minus(targetSize.width / 2)
                            translationY = offset.y.minus(targetSize.height / 2)
                        }
                        //content()のsizeを取得する
                        .onGloballyPositioned {
                            targetSize = it.size
//                            it.let { cordinates ->
//                                state.absolutePositionX = cordinates.positionInRoot().x
//                                state.absolutePositionY = cordinates.positionInRoot().y
//                            }
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
    dragData: DragData? = null,
    content: @Composable () -> Unit
) {

    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    Box(
        modifier = Modifier
            //get layout size
            .onGloballyPositioned {
                //その指定スコープ内でのoffsetゼロを設定
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
                        //PointerInputChangeイベントが他のコンポーネントで消費されたことを示す
                        change.consume()
                        // used to prevent drop target from multiple re-renders
                        currentState.itemDropped = false
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