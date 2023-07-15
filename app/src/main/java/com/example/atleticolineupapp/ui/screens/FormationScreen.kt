package com.example.atleticolineupapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.layoutId
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.dragAndDrop.MimeType
import com.example.atleticolineupapp.ui.formation.ManageFormation
import com.example.atleticolineupapp.ui.formation.positionList
import com.example.atleticolineupapp.util.drag.DragData
import com.example.atleticolineupapp.util.drop.DropContainer
import com.example.atleticolineupapp.util.drop.DropContainerX
import com.example.atleticolineupapp.util.drop.DropPaneContent

@Composable
fun DisplayFormation(
    modifier: Modifier,
    manageFormation: ManageFormation,
//    vm: PositionStateViewModel = viewModel(),
    stateList: List<PositionState>,
    //tabStateX: Boolean
//    stateHolder: StateHolder = rememberStateHolder()
) {
    val positionLists = remember { mutableStateListOf<String>() }
    positionLists.addAll(positionList)

    var isDroppingTarget by remember { mutableStateOf(false) }

    SideEffect {
        Log.d("composeLog", "DisplayFormation composition!")
    }

    ConstraintLayout(
        constraintSet = manageFormation.formationConstraints(),
        modifier = modifier
    ) {
        for (i in 0..10) {
            DropContainer(
                modifier = Modifier.layoutId(positionLists[i]),
                onDrag = { isBounds, isDragging ->
                    if (!isBounds || !isDragging) {
                        stateList[i].isDroppingItem = false
                    }
                    isDroppingTarget = isDragging
                },
            ) { dragData ->
//                Log.d("List", "stateList: ${stateList[i]}")
                //Dropしてない時にdragData内の中身を調べている。
                //これが無いとそもそもdropしても表示されない
                dragData?.let {
                    if (dragData.type == MimeType.IMAGE_JPEG) {
                        stateList[i].isDroppingItem = isDroppingTarget
                        if (!isDroppingTarget) {
                            stateList[i].dragImage = dragData.data as Painter
                        }
                    }
                }
                PositionCard(
                    dragImage = stateList[i].dragImage,
                )
            }
        }
    }
}

@Composable
fun PositionCard(
    dragImage: Painter?,
) {

    SideEffect {
        Log.d("composeLog", "PositionCard composition!")
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .padding(5.dp)
            .background(color = Color.Transparent)
            .size(80.dp, 90.dp)
            .border(
                width = 3.dp,
                color = Color.Black,
                shape = RoundedCornerShape(18.dp)
            )
    ) {
        Box {
            DropPaneContent(
                modifier = Modifier.align(Alignment.Center),
                dragImage = dragImage,
            )
        }
    }
}