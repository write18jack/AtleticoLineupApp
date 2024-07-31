package com.whitebeach.atleticolineupapp.presentations.formation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.layoutId
import com.whitebeach.atleticolineupapp.dragAndDrop.MimeType
import com.whitebeach.atleticolineupapp.presentations.main.PositionState
import com.whitebeach.atleticolineupapp.app.component.dragDrop.LocalDragTargetInfo
import com.whitebeach.atleticolineupapp.app.component.dragDrop.DropPaneContent
import com.whitebeach.atleticolineupapp.app.component.dragDrop.DropTarget

@Composable
fun DisplayFormation(
    modifier: Modifier,
    manageFormation: ManageFormation,
    stateList: List<PositionState>
//    stateHolder: StateHolder = rememberStateHolder()
) {
    val positionLists = rememberUpdatedState(newValue = positionList)

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val itemHeight = screenHeight / 8
    val itemWidth = screenWidth / 5

    ConstraintLayout(
        constraintSet = manageFormation.formationConstraints(),
        modifier = modifier
    ) {
        for (i in 0..10) {
            DropTarget(
                modifier = Modifier.layoutId(positionLists.value[i]),
                onDrag = { isBounds, isDragging ->
                    stateList[i].isDroppingItem = isDragging
                    stateList[i].isItemInBounds = isBounds
                },
            ) { dragData ->
                if (!LocalDragTargetInfo.current.itemDropped) {
                    //Dropしてない時にdragData内の中身を調べている。
                    //これが無いとそもそもdropしても表示されない
                    dragData?.let {
                        LocalDragTargetInfo.current.itemDropped = true
                        //LocalDragTargetInfo.current.dragData = null

                        if (!stateList[i].isDroppingItem) {
                            if (it.type == MimeType.IMAGE_JPEG) {
                                stateList[i].dragImage = it.data as Painter
                            }
                        }
                    }
                    PositionCard(
                        dragImage = stateList[i].dragImage,
                        itemWidth = itemWidth,
                        itemHeight = itemHeight
                    )
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