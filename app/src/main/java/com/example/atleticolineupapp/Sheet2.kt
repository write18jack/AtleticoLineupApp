package com.example.atleticolineupapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.solver.state.ConstraintReference
import com.example.atleticolineupapp.dragAndDrop.MimeType
import com.example.atleticolineupapp.presentations.dragAndDrop.DropContainer
import com.example.atleticolineupapp.presentations.theme.Purple700
import com.example.atleticolineupapp.presentations.theme.Teal200
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun F442ScreenX(
    modifier: Modifier = Modifier
) {
    //var dragText by remember { mutableStateOf<String?>(null) }
    var dragImage by remember { mutableStateOf<Painter?>(null) }
    //val updateDragText: (String?) -> Unit = { newValue -> dragText = newValue }
    //val updateDragImage: (Painter?) -> Unit = { newValue -> dragImage = newValue }
    var isDroppingItem by remember { mutableStateOf(false) }
    var isItemInBounds by remember { mutableStateOf(false) }

    var dragImage2 by remember { mutableStateOf<Painter?>(null) }
    var isDroppingItem2 by remember { mutableStateOf(false) }
    var isItemInBounds2 by remember { mutableStateOf(false) }

    var dragImage3 by remember { mutableStateOf<Painter?>(null) }
    var isDroppingItem3 by remember { mutableStateOf(false) }
    var isItemInBounds3 by remember { mutableStateOf(false) }

//    Box(
//        modifier = modifier.fillMaxSize()
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.pitch),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize()
//        )

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (lst, rst, lm, lcm, rcm, rm, lsb, lcb, rcb, rsb, gk) = createRefs()

        DropContainer(
            modifier = modifier
                .constrainAs(lst) {
                    top.linkTo(parent.top, margin = 0.dp)
                    bottom.linkTo(lcm.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(rst.start, margin = 0.dp)
                },
            onDrag = { isBounds, isDragging ->
                isDroppingItem = isDragging
                isItemInBounds = isBounds
            }
        ) { dragData ->
            Box(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .border(width = 1.dp, color = androidx.compose.ui.graphics.Color.Red)
            ) {
                dragData?.let {
                    if (!isDroppingItem) {
                        if (dragData.type == MimeType.IMAGE_JPEG) {
                            dragImage = dragData.data as Painter
                        }
                    }
                }
                DropPaneContent(
                    dragImage, modifier
                        .align(Alignment.Center)
                )
            }
        }

        DropContainer(
            modifier = modifier
                .constrainAs(rst) {
                    top.linkTo(parent.top, margin = 0.dp)
                    bottom.linkTo(rcm.top, margin = 0.dp)
                    start.linkTo(lst.end, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                },
            onDrag = { isBounds, isDragging ->
                isDroppingItem2 = isDragging
                isItemInBounds2 = isBounds
            }
        ) { dragData ->
            val boxColor =
                if (isDroppingItem2 && isItemInBounds2) Teal200
                else if (isDroppingItem2) Purple700
                else androidx.compose.ui.graphics.Color.White
            Box(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .background(color = boxColor)
                    .border(width = 1.dp, color = androidx.compose.ui.graphics.Color.Red)
            ) {
                dragData?.let {
                    if (!isDroppingItem2) {
                        if (dragData.type == MimeType.IMAGE_JPEG) {
                            dragImage2 = dragData.data as Painter
                        }
                    }
                }
                DropPaneContent(
                    dragImage2,
                    modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                )
            }
        }

        DropContainer(
            modifier = modifier
                .constrainAs(lm) {
                    top.linkTo(lst.bottom, margin = 0.dp)
                    bottom.linkTo(lsb.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(lcm.start, margin = 0.dp)
                },
            onDrag = { isBounds, isDragging ->
                isDroppingItem = isDragging
                isItemInBounds = isBounds
            }
        ) { dragData ->

            Box(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .border(width = 1.dp, color = androidx.compose.ui.graphics.Color.Red)
            ) {
                dragData?.let {
                    if (!isDroppingItem) {
                        if (dragData.type == MimeType.IMAGE_JPEG) {
                            dragImage = dragData.data as Painter
                        }
                    }
                }
                DropPaneContent(
                    dragImage, modifier
                        .align(Alignment.Center)

                )
            }
        }

        DropContainer(
            modifier = modifier
                .constrainAs(lcm) {
                    top.linkTo(lst.bottom, margin = 0.dp)
                    bottom.linkTo(lcb.top, margin = 0.dp)
                    start.linkTo(lm.end, margin = 0.dp)
                    end.linkTo(rcm.start, margin = 0.dp)
                },
            onDrag = { isBounds, isDragging ->
                isDroppingItem = isDragging
                isItemInBounds = isBounds
            }
        ) { dragData ->

            Box(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .border(width = 1.dp, color = androidx.compose.ui.graphics.Color.Red)
            ) {
                dragData?.let {
                    if (!isDroppingItem) {
                        if (dragData.type == MimeType.IMAGE_JPEG) {
                            dragImage = dragData.data as Painter
                        }
                    }
                }
                DropPaneContent(
                    dragImage, modifier
                        .align(Alignment.Center)

                )
            }
        }

        DropContainer(
            modifier = modifier
                .constrainAs(rcm) {
                    top.linkTo(rst.bottom, margin = 0.dp)
                    bottom.linkTo(rcb.top, margin = 0.dp)
                    start.linkTo(lcm.end, margin = 0.dp)
                    end.linkTo(rm.start, margin = 0.dp)
                },
            onDrag = { isBounds, isDragging ->
                isDroppingItem = isDragging
                isItemInBounds = isBounds
            }
        ) { dragData ->

            Box(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .border(width = 1.dp, color = androidx.compose.ui.graphics.Color.Red)
            ) {
                dragData?.let {
                    if (!isDroppingItem) {
                        if (dragData.type == MimeType.IMAGE_JPEG) {
                            dragImage = dragData.data as Painter
                        }
                    }
                }
                DropPaneContent(
                    dragImage, modifier
                        .align(Alignment.Center)

                )
            }
        }

        DropContainer(
            modifier = modifier
                .constrainAs(rm) {
                    top.linkTo(rst.bottom, margin = 0.dp)
                    bottom.linkTo(rsb.top, margin = 0.dp)
                    start.linkTo(rcm.end, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                },
            onDrag = { isBounds, isDragging ->
                isDroppingItem = isDragging
                isItemInBounds = isBounds
            }
        ) { dragData ->

            Box(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .border(width = 1.dp, color = androidx.compose.ui.graphics.Color.Red)
            ) {
                dragData?.let {
                    if (!isDroppingItem) {
                        if (dragData.type == MimeType.IMAGE_JPEG) {
                            dragImage = dragData.data as Painter
                        }
                    }
                }
                DropPaneContent(
                    dragImage, modifier
                        .align(Alignment.Center)

                )
            }
        }

        Image(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier.constrainAs(lsb) {
                top.linkTo(lm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(lcb.start, margin = 0.dp)
            }
        )

        Image(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier.constrainAs(lcb) {
                top.linkTo(lcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(lsb.end, margin = 0.dp)
                end.linkTo(rcb.start, margin = 0.dp)
            }
        )

        Image(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier.constrainAs(rcb) {
                top.linkTo(rcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(lcb.end, margin = 0.dp)
                end.linkTo(rsb.start, margin = 0.dp)
            }
        )

        Image(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier.constrainAs(rsb) {
                top.linkTo(rm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(rcb.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
        )

        Image(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier.constrainAs(gk) {
                top.linkTo(lcb.bottom, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
                start.linkTo(lsb.end, margin = 0.dp)
                end.linkTo(rsb.start, margin = 0.dp)
            }
        )


//        DropContainer(
//            modifier = Modifier
//                .constrainAs(gk) {
//                    top.linkTo(lcb.bottom, margin = 0.dp)
//                    bottom.linkTo(parent.bottom, margin = 0.dp)
//                    start.linkTo(lsb.end, margin = 0.dp)
//                    end.linkTo(rsb.start, margin = 0.dp)
//                },
//            onDrag = { isBounds, isDragging ->
//                isDroppingItem2 = isDragging
//                isItemInBounds2 = isBounds
//            }
//        ) { dragData ->
//            val boxColor =
//                if (isDroppingItem2 && isItemInBounds2) Teal200
//                else if (isDroppingItem2) Purple700
//                else Color.White
//
//            Box(
//                modifier = Modifier
//                    .size(60.dp, 60.dp)
//                    .background(color = boxColor)
//                    .border(width = 1.dp, color = Color.Red)
//            ) {
//                dragData?.let {
//                    if (!isDroppingItem2) {
//                        if (dragData.type == MimeType.IMAGE_JPEG) {
//                            dragImage2 = dragData.data as Painter
//                        }
//                    }
//                }
//                DropPaneContent(
//                    dragImage2,
//                    modifier
//                        .align(Alignment.Center)
//                        .size(50.dp)
//                )
//            }
//        }

    }
}



@Composable
fun DropPaneContent(dragImage: Painter?, modifier: Modifier = Modifier) {
    if (dragImage != null) {
        Image(
            painter = dragImage,
            contentDescription = "player",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = modifier.size(40.dp),
        )
    }
}

@Composable
fun TestScreen1(){
    Box(modifier = Modifier.fillMaxSize()) {
        F442ScreenX()
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    TestScreen1()
}