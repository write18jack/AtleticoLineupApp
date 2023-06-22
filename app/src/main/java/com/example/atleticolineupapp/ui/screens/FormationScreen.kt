package com.example.atleticolineupapp.ui.screens

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.layoutId
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.dragAndDrop.MimeType
import com.example.atleticolineupapp.ui.formation.F4141
import com.example.atleticolineupapp.ui.formation.F442
import com.example.atleticolineupapp.ui.formation.ManageFormation
import com.example.atleticolineupapp.ui.formation.positionList
import com.example.atleticolineupapp.ui.tab.FormationTabViewModel
import com.example.atleticolineupapp.ui.theme.Purple200
import com.example.atleticolineupapp.ui.theme.Teal200
import com.example.atleticolineupapp.util.drop.DropContainer
import com.example.atleticolineupapp.util.drop.DropPaneContent

@Composable
fun DisplayFormation(
    modifier: Modifier,
    manageFormation: ManageFormation,
    vm: PositionStateViewModel = viewModel(),
    stateHolder: StateHolder = rememberStateHolder()
) {
    val stateList = vm.positionStateList

    ConstraintLayout(
        constraintSet = manageFormation.formationConstraints(),
        modifier = modifier
    ) {
        for (i in 0..10) {
            DropContainer(
                modifier = Modifier.layoutId(positionList[i]),
                onDrag = { isBounds, isDragging ->
                    stateList[i].isDroppingItem = isDragging
                    stateList[i].isItemInBounds = isBounds
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
                    // .shadow(elevation.value)
                ) {
                    Box {
                        //Dropしてない時にdragData内の中身を調べる
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

@Preview
@Composable
fun FormationPreview(vm2: FormationTabViewModel = viewModel()) {
    val constraintSetItemX by vm2.constraintSetItem.collectAsState()
    DisplayFormation(
        modifier = Modifier.fillMaxSize(),
        manageFormation = constraintSetItemX
    )
}