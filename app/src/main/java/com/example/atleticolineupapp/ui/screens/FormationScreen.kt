package com.example.atleticolineupapp.ui.screens

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.layoutId
import com.example.atleticolineupapp.dragAndDrop.MimeType
import com.example.atleticolineupapp.ui.formation.ManageFormation
import com.example.atleticolineupapp.ui.formation.positionList
import com.example.atleticolineupapp.util.drop.DropContainer
import com.example.atleticolineupapp.util.drop.DropPaneContent

@Composable
fun DisplayFormation(
    modifier: Modifier,
    manageFormation: ManageFormation,
    stateList: List<PositionState>,
//    stateHolder: StateHolder = rememberStateHolder()
) {
    val positionLists = remember { mutableStateListOf<String>() }
    positionLists.addAll(positionList)

    var isDroppingTarget by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val itemHeight = screenHeight / 10
    val itemWidth = screenWidth / 5

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
            ) {dragData ->
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
                    itemWidth = itemWidth,
                    itemHeight = itemHeight
                )
            }
        }
    }
}

@Composable
fun PositionCard(
    dragImage: Painter?,
    itemWidth: Dp,
    itemHeight: Dp
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .padding(5.dp)
            .background(color = Color.Transparent)
            .size(width = itemWidth, height = itemHeight)
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