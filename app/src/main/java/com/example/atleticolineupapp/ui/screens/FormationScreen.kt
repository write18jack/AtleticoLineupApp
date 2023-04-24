package com.example.atleticolineupapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atleticolineupapp.dragAndDrop.MimeType
import com.example.atleticolineupapp.ui.formation.F442
import com.example.atleticolineupapp.ui.formation.ManageFormation
import com.example.atleticolineupapp.ui.formation.positionList
import com.example.atleticolineupapp.util.drop.DropContainer
import com.example.atleticolineupapp.util.drop.DropPaneContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// mutablelistを使ってみる
//stateをviewmodelで管理する
@Stable
class DropStateViewModel : ViewModel() {
    private val _dragImage = MutableStateFlow<Painter?>(null)
    val dragImage: StateFlow<Painter?> = _dragImage
    fun onChangeDragImage(newValue: Painter?) {
        viewModelScope.launch {
            _dragImage.value = newValue
        }
    }

    private val _isDroppingItem = MutableStateFlow<Boolean>(false)
    val isDroppingItem: StateFlow<Boolean> = _isDroppingItem
    fun onChangeIsDroppingItem(newValue: Boolean) {
        viewModelScope.launch {
            _isDroppingItem.value = newValue
        }
    }

    private val _isItemInBounds = MutableStateFlow<Boolean>(false)
    val isItemInBounds: StateFlow<Boolean> = _isItemInBounds
    fun onChangeIsItemInBounds(newValue: Boolean) {
        viewModelScope.launch {
            _isItemInBounds.value = newValue
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayFormation(
    modifier: Modifier,
    manageFormation: ManageFormation
) {
    var dragImage by remember { mutableStateOf<Painter?>(null) }
    var isDroppingItem by remember { mutableStateOf(false) }
    var isItemInBounds by remember { mutableStateOf(false) }

    ConstraintLayout(
        constraintSet = manageFormation.formationConstraints(),
        modifier = modifier.fillMaxSize()
    ) {
        for (i in positionList) {
            DropContainer(
                modifier = Modifier.layoutId(i),
                onDrag = { isBounds, isDragging ->
                    isDroppingItem = isDragging
                    isItemInBounds = isBounds
                }
            ) {dragData ->
                Box(
                    modifier = Modifier
                        .size(60.dp, 60.dp)
                        .border(width = 1.dp, color = Color.Red)
                ) {
                    dragData?.let {
                        if (!isDroppingItem) {
                            if (dragData.type == MimeType.IMAGE_JPEG) {
                                dragImage = dragData.data as Painter
                            }
                        }
                    }
                    DropPaneContent(
                        modifier.align(Alignment.Center),
                        dragImage
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TestScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        DisplayFormation(
            modifier = Modifier.fillMaxSize(),
            manageFormation = F442()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSample() {
    TestScreen()
}
