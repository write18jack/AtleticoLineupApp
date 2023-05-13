package com.example.atleticolineupapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
@Preview(showBackground = true)
@Composable
fun PreviewMethodXX() {
    ReorderGrid()
}

@Composable
fun ReorderGrid() {
    Column {
        //PlayerSlotSheet()

    }
}

//@Composable
//private fun HorizontalGrid(
//    vm: ReorderListViewModel,
//    modifier: Modifier = Modifier,
//) {
//    val dragImage = painterResource(id = player.image)//Cast to Painter at here.
//    val dragData = DragData(type = MimeType.IMAGE_JPEG, data = dragImage)
//    val state = rememberReorderableLazyGridState(onMove = vm::moveCat)
//    LazyHorizontalGrid(
//        rows = GridCells.Fixed(2),
//        state = state.gridState,
//        contentPadding = PaddingValues(horizontal = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(4.dp),
//        horizontalArrangement = Arrangement.spacedBy(4.dp),
//        modifier = modifier
//            .reorderable(state)
//            .height(200.dp)
//            .detectReorderAfterLongPress(state)
//    ) {
//        items(vm.cats, { it.key }) { item ->
//            DragTarget(modifier = Modifier.size(100.dp, 150.dp), dragData = dragData) {
//                ReorderableItem(state, item.key) { isDragging ->
//                    val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier
//                            .shadow(elevation.value)
//                            .aspectRatio(1f)
//                            .background(MaterialTheme.colors.secondary)
//                    ) {
//                        Text(item.title)
//                    }
//                }
//            }
//        }
//    }
//}
//
//class ReorderListViewModel : ViewModel() {
//    var cats by mutableStateOf(List(500) { ItemData("Cat $it", "id$it") })
//    var dogs by mutableStateOf(List(500) {
//        if (it.mod(10) == 0) ItemData("Locked", "id$it", true) else ItemData("Dog $it", "id$it")
//    })
//
//    fun moveCat(from: ItemPosition, to: ItemPosition) {
//        cats = cats.toMutableList().apply {
//            add(to.index, removeAt(from.index))
//        }
//    }
//
//    fun moveDog(from: ItemPosition, to: ItemPosition) {
//        dogs = dogs.toMutableList().apply {
//            add(to.index, removeAt(from.index))
//        }
//    }
//
//    fun isDogDragEnabled(draggedOver: ItemPosition, dragging: ItemPosition) = dogs.getOrNull(draggedOver.index)?.isLocked != true
//}
//
//
//@Composable
//private fun VerticalGrid(
//    vm: ReorderListViewModel,
//    modifier: Modifier = Modifier,
//) {
//    val state = rememberReorderableLazyGridState(onMove = vm::moveDog, canDragOver = vm::isDogDragEnabled)
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(4),
//        state = state.gridState,
//        contentPadding = PaddingValues(horizontal = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(4.dp),
//        horizontalArrangement = Arrangement.spacedBy(4.dp),
//        modifier = modifier.reorderable(state)
//    ) {
//        items(vm.dogs, { it.key }) { item ->
//            if (item.isLocked) {
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .background(MaterialTheme.colors.surface)
//                ) {
//                    Text(item.title)
//                }
//            } else {
//                ReorderableItem(state, item.key) { isDragging ->
//                    val elevation = animateDpAsState(if (isDragging) 8.dp else 0.dp)
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier
//                            .detectReorderAfterLongPress(state)
//                            .shadow(elevation.value)
//                            .aspectRatio(1f)
//                            .background(MaterialTheme.colors.primary)
//                    ) {
//                        Text(item.title)
//                    }
//                }
//            }
//        }
//    }
//}