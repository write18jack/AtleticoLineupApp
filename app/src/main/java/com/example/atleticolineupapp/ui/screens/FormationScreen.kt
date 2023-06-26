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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.layoutId
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.dragAndDrop.MimeType
import com.example.atleticolineupapp.ui.formation.ManageFormation
import com.example.atleticolineupapp.ui.formation.positionList
import com.example.atleticolineupapp.util.drop.DropContainer
import com.example.atleticolineupapp.util.drop.DropPaneContent

@Composable
fun DisplayFormation(
    modifier: Modifier,
    manageFormation: ManageFormation,
    vm: PositionStateViewModel = viewModel(),
//    stateHolder: StateHolder = rememberStateHolder()
) {
    val stateList = vm.positionStateList
    val positionLists = remember { mutableStateListOf<String>() }
    positionLists.addAll(positionList)
    ConstraintLayout(
        constraintSet = manageFormation.formationConstraints(),
        modifier = modifier
    ) {
        for (i in 0..10) {
            DropContainer(
                modifier = Modifier.layoutId(positionLists[i]),
                onDrag = { isBounds, isDragging ->
                    stateList[i].isItemInBounds = isBounds
                    stateList[i].isDroppingItem = isDragging
                }
            ) { dragData ->
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
                        //Dropしてない時にdragData内の中身を調べている。
                        dragData?.let {
                            if (!stateList[i].isDroppingItem) {
                                if (dragData.type == MimeType.IMAGE_JPEG) {
                                    stateList[i].dragImage = dragData.data as Painter
                                }
                            }
                        }
                        DropPaneContent(
                            modifier = Modifier.align(Alignment.Center),
                            dragImage = stateList[i].dragImage
                        )
                    }
                }
            }
        }
    }
}