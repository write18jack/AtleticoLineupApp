package com.example.atleticolineupapp.util.drop

import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atleticolineupapp.ui.theme.Purple200
import com.example.atleticolineupapp.util.drag.DragData
import com.example.atleticolineupapp.util.drag.LocalDragTargetInfo

@Composable
fun DropContainer(
    modifier: Modifier,
    onDrag: (inBounds: Boolean, isDragging: Boolean) -> Unit,
    tabState: Boolean = false,
    content: @Composable (BoxScope.(data: DragData?) -> Unit)
) {
    val dragState = LocalDragTargetInfo.current
    val dragPosition = dragState.dragPosition
    val dragOffset = dragState.dragOffset
    var inBounds by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.onGloballyPositioned {
            it.boundsInWindow().let { rect ->
                // containsでboolean判定してる
                //val xx:Offset = if(tabState) Offset.Zero else dragPosition + dragOffset
                inBounds = rect.contains(dragPosition + dragOffset)
            }
        }
    ) {
        val dragData = if (inBounds) dragState.dragData else null
        onDrag(inBounds, dragState.isDragging)
        content(dragData)
    }
}

@Composable
fun DropContainerX(
    modifier: Modifier,
    onDrag: (inBounds: Boolean, isDragging: Boolean) -> Unit,
    tabState: Boolean = false,
    content: @Composable (BoxScope.(data: DragData?) -> Unit)
) {
    val dragState = LocalDragTargetInfo.current
    val dragPosition = dragState.dragPosition
    val dragOffset = dragState.dragOffset
    var inBounds by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
//        val dragData = if (inBounds) dragState.dragData else null
//        onDrag(inBounds, dragState.isDragging)
//        content(dragData)
    }
}

@Composable
fun PlayerTabDropContainer(
    modifier: Modifier,
    onDrag: (inBounds: Boolean, isDragging: Boolean) -> Unit,
    content: @Composable (BoxScope.(data: DragData?) -> Unit)
) {
    val dragState = LocalDragTargetInfo.current
    val dragPosition = dragState.dragPosition
    val dragOffset = dragState.dragOffset
    var inBounds by  remember { mutableStateOf(false) }

    Box(
        modifier = modifier.onGloballyPositioned {
            it.boundsInWindow().let { rect ->
                // containsでboolean判定してる
                //val xx:Offset = if(tabState) Offset.Zero else dragPosition + dragOffset
                inBounds = rect.contains(dragPosition + dragOffset)
            }
        }
    ) {
        val dragData = if (inBounds) dragState.dragData else null
        onDrag(inBounds, dragState.isDragging)
        content(dragData)
    }
}

@Composable
fun DropPaneContent(
    modifier: Modifier,
    dragImage: Painter?,
//    isItemInBounds: Boolean
) {
    if (dragImage != null) {
        Image(
            painter = dragImage,
            contentDescription = null,
            modifier = modifier.clip(shape = ShapeDefaults.Large),
            contentScale = ContentScale.Crop
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