package com.whitebeach.presentation.component.dragDrop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp

@Composable
fun DropTarget(
    modifier: Modifier,
    onDrag: (inBounds: Boolean, isDragging: Boolean) -> Unit,
    content: @Composable BoxScope.(data: DragData?) -> Unit
) {
    val dragState = LocalDragTargetInfo.current
    val dragPosition = dragState.dragPosition
    val dragOffset = dragState.dragOffset
    var inBounds by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                it.boundsInWindow().let { rect ->
                    // containsでboolean判定してる
                    inBounds = rect.contains(dragPosition + dragOffset)
                }
            }
    ) {
        val dragData = if (inBounds && !dragState.isDragging) dragState.dragData else null
        onDrag(inBounds, dragState.isDragging)
        content(dragData)
    }
}

@Composable
fun DropPaneContent(
    modifier: Modifier,
    dragImage: Painter?,
    //isItemInBounds: Boolean
) {
    if (dragImage != null) {
        Image(
            painter = dragImage,
            contentDescription = null,
            modifier =  Modifier,//modifier.clip(shape = ShapeDefaults.Large),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillHeight
        )
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = null,
                modifier = modifier
                    .align(Alignment.Center)
                    .size(60.dp),
                tint = Color.LightGray
            )
        }
    }
}