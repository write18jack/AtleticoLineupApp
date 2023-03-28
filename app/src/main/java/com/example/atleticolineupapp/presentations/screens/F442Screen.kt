package com.example.atleticolineupapp.presentations.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.DropPaneContent
import com.example.atleticolineupapp.R
import com.example.atleticolineupapp.SheetViewModel
import com.example.atleticolineupapp.presentations.dragAndDrop.DropContainer
import com.example.atleticolineupapp.dragAndDrop.MimeType
import com.example.atleticolineupapp.presentations.theme.Purple700
import com.example.atleticolineupapp.presentations.theme.Teal200

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TestScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        F442Screen(modifier = Modifier.fillMaxSize())
    }
}

@ExperimentalMaterialApi
@Composable
fun F442Screen(
    modifier: Modifier = Modifier
) {
    var dragImage by remember { mutableStateOf<Painter?>(null) }
    var isDroppingItem by remember { mutableStateOf(false) }
    var isItemInBounds by remember { mutableStateOf(false) }

    val constraints = F532Constraints()
    ConstraintLayout(constraints, modifier = modifier.fillMaxSize()) {
        for (i in PositionList) {

            DropContainer(
                modifier = Modifier.layoutId(i),
                onDrag = { isBounds, isDragging ->
                    isDroppingItem = isDragging
                    isItemInBounds = isBounds
                }
            ) { dragData ->
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
                        dragImage, Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

val PositionList = mutableListOf("Lst", "St", "Rst", "Lm", "Lcm", "Cm", "Rcm", "Cdm", "Rm", "Lsb", "Lcb", "Cb", "Rcb", "Rsb", "Gk")

private fun F442Constraints(): ConstraintSet {
    return ConstraintSet {
        val lst = createRefFor(PositionList[0])
        val rst = createRefFor(PositionList[1])
        val lm = createRefFor(PositionList[3])
        val lcm = createRefFor(PositionList[5])
        val rcm = createRefFor(PositionList[7])
        val rm = createRefFor(PositionList[8])
        val lsb = createRefFor(PositionList[8])
        val lcb = createRefFor(PositionList[9])
        val rcb = createRefFor(PositionList[11])
        val rsb = createRefFor(PositionList[12])
        val gk = createRefFor(PositionList[13])

        constrain(lst) {
            top.linkTo(parent.top)
            bottom.linkTo(lcm.top)
            start.linkTo(parent.start)
            end.linkTo(rst.start)
        }
        constrain(rst) {
            top.linkTo(parent.top, margin = 0.dp)
            bottom.linkTo(rcm.top, margin = 0.dp)
            start.linkTo(lst.end, margin = 0.dp)
            end.linkTo(parent.end, margin = 0.dp)
        }
        constrain(lm) {
            top.linkTo(lst.bottom, margin = 0.dp)
            bottom.linkTo(lsb.top, margin = 0.dp)
            start.linkTo(parent.start, margin = 0.dp)
            end.linkTo(lcm.start, margin = 0.dp)
        }
        constrain(lcm) {
            top.linkTo(lst.bottom, margin = 0.dp)
            bottom.linkTo(lcb.top, margin = 0.dp)
            start.linkTo(lm.end, margin = 0.dp)
            end.linkTo(rcm.start, margin = 0.dp)
        }
        constrain(rcm) {
            top.linkTo(rst.bottom, margin = 0.dp)
            bottom.linkTo(rcb.top, margin = 0.dp)
            start.linkTo(lcm.end, margin = 0.dp)
            end.linkTo(rm.start, margin = 0.dp)
        }
        constrain(rm) {
            top.linkTo(rst.bottom, margin = 0.dp)
            bottom.linkTo(rsb.top, margin = 0.dp)
            start.linkTo(rcm.end, margin = 0.dp)
            end.linkTo(parent.end, margin = 0.dp)
        }
        constrain(lsb) {
            top.linkTo(lm.bottom, margin = 0.dp)
            bottom.linkTo(gk.top, margin = 0.dp)
            start.linkTo(parent.start, margin = 0.dp)
            end.linkTo(lcb.start, margin = 0.dp)
        }
        constrain(lcb) {
            top.linkTo(lcm.bottom, margin = 0.dp)
            bottom.linkTo(gk.top, margin = 0.dp)
            start.linkTo(lsb.end, margin = 0.dp)
            end.linkTo(rcb.start, margin = 0.dp)
        }
        constrain(rcb) {
            top.linkTo(rcm.bottom, margin = 0.dp)
            bottom.linkTo(gk.top, margin = 0.dp)
            start.linkTo(lcb.end, margin = 0.dp)
            end.linkTo(rsb.start, margin = 0.dp)
        }
        constrain(rsb) {
            top.linkTo(rm.bottom, margin = 0.dp)
            bottom.linkTo(gk.top, margin = 0.dp)
            start.linkTo(rcb.end, margin = 0.dp)
            end.linkTo(parent.end, margin = 0.dp)
        }
        constrain(gk) {
            top.linkTo(lcb.bottom, margin = 0.dp)
            bottom.linkTo(parent.bottom, margin = 0.dp)
            start.linkTo(lsb.end, margin = 0.dp)
            end.linkTo(rsb.start, margin = 0.dp)
        }
    }
}

private fun F532Constraints(): ConstraintSet {
    val copiedMutableList = PositionList.toMutableList()
    return ConstraintSet {
        val lst = createRefFor(PositionList[0])
        val rst = createRefFor(PositionList[2])
        val lcm = createRefFor(PositionList[4])
        val cm = createRefFor(PositionList[5])
        val rcm = createRefFor(PositionList[6])
        val lsb = createRefFor(PositionList[8])
        val lcb = createRefFor(PositionList[9])
        val cb = createRefFor(PositionList[10])
        val rcb = createRefFor(PositionList[11])
        val rsb = createRefFor(PositionList[12])
        val gk = createRefFor(PositionList[13])

        constrain(lst) {
            top.linkTo(parent.top)
            bottom.linkTo(lcm.top)
            start.linkTo(parent.start)
            end.linkTo(rst.start)
        }
        constrain(rst) {
            top.linkTo(parent.top, margin = 0.dp)
            bottom.linkTo(rcm.top, margin = 0.dp)
            start.linkTo(lst.end, margin = 0.dp)
            end.linkTo(parent.end, margin = 0.dp)
        }
        constrain(lcm) {
            top.linkTo(lst.bottom, margin = 0.dp)
            bottom.linkTo(lcb.top, margin = 0.dp)
            start.linkTo(lsb.end, margin = 0.dp)
            end.linkTo(rcm.start, margin = 0.dp)
        }
        constrain(cm) {
            top.linkTo(lst.bottom, margin = 0.dp)
            bottom.linkTo(cb.top, margin = 0.dp)
            start.linkTo(lcm.end, margin = 0.dp)
            end.linkTo(rcm.start, margin = 0.dp)
        }
        constrain(rcm) {
            top.linkTo(rst.bottom, margin = 0.dp)
            bottom.linkTo(rcb.top, margin = 0.dp)
            start.linkTo(lcm.end, margin = 0.dp)
            end.linkTo(rsb.start, margin = 0.dp)
        }
        constrain(lsb) {
            top.linkTo(lcm.bottom, margin = 0.dp)
            bottom.linkTo(gk.top, margin = 0.dp)
            start.linkTo(parent.start, margin = 0.dp)
            end.linkTo(lcb.start, margin = 0.dp)
        }
        constrain(lcb) {
            top.linkTo(lcm.bottom, margin = 0.dp)
            bottom.linkTo(gk.top, margin = 0.dp)
            start.linkTo(lsb.end, margin = 0.dp)
            end.linkTo(rcb.start, margin = 0.dp)
        }
        constrain(cb) {
            top.linkTo(cm.bottom, margin = 0.dp)
            bottom.linkTo(rsb.top, margin = 0.dp)
            start.linkTo(rcm.end, margin = 0.dp)
            end.linkTo(parent.end, margin = 0.dp)
        }
        constrain(rcb) {
            top.linkTo(rcm.bottom, margin = 0.dp)
            bottom.linkTo(gk.top, margin = 0.dp)
            start.linkTo(lcb.end, margin = 0.dp)
            end.linkTo(rsb.start, margin = 0.dp)
        }
        constrain(rsb) {
            top.linkTo(rcm.bottom, margin = 0.dp)
            bottom.linkTo(gk.top, margin = 0.dp)
            start.linkTo(rcb.end, margin = 0.dp)
            end.linkTo(parent.end, margin = 0.dp)
        }
        constrain(gk) {
            top.linkTo(lcb.bottom, margin = 0.dp)
            bottom.linkTo(parent.bottom, margin = 0.dp)
            start.linkTo(lsb.end, margin = 0.dp)
            end.linkTo(rsb.start, margin = 0.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSample(){
    TestScreen()
}
