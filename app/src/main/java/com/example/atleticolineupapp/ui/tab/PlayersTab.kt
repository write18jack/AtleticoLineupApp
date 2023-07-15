package com.example.atleticolineupapp.ui.tab

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atleticolineupapp.dragAndDrop.MimeType
import com.example.atleticolineupapp.util.PlayerItem
import com.example.atleticolineupapp.util.drag.DragContainer
import com.example.atleticolineupapp.util.drag.DragData
import com.example.atleticolineupapp.util.drag.DragTarget
import com.example.atleticolineupapp.util.drop.DropContainer
import com.example.atleticolineupapp.util.drop.PlayerTabDropContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PlayerSlotSheet(
    modifier: Modifier = Modifier,
    lazyGridState: LazyGridState,
    players: List<PlayerItem>,
    onHeightInfo: (Int) -> Unit
) {

    SideEffect {
        Log.d("composeLog", "PlayerSlotSheet composition!")
    }

    BoxWithConstraints {
        val copyPlayersTabHeight = with(LocalDensity.current) { constraints.maxHeight}/3
        val localScreenHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() }/3
        onHeightInfo(copyPlayersTabHeight)
        //lazyGridState.layoutInfo
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            state = lazyGridState,
            contentPadding = PaddingValues(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(localScreenHeight)

        ) {
            items(players, { it.key }) { item ->
                //SlotCard()
                PlayerCard(
                    player = item,
                )
            }
        }
    }
}

@Composable
fun PlayerCard(player: PlayerItem) { //checkDrag: Boolean
    val dragImage = painterResource(id = player.image)//Cast to Painter at here.
    val dragData = DragData(type = MimeType.IMAGE_JPEG, data = dragImage)
    //val elevation = animateDpAsState(if (checkDrag) 50.dp else 0.dp)

    SideEffect {
        Log.d("composeLog", "PlayerCard composition!")
    }

    var isDroppingItem by remember { mutableStateOf(true) }
    var isItemInBounds by remember { mutableStateOf(true) }
    PlayerTabDropContainer(
        modifier = Modifier,
        onDrag = { isBounds, isDragging ->
            isItemInBounds = isBounds
            isDroppingItem = isDragging
        }
    ) {
        DragTarget(modifier = Modifier, dragData = dragData) {
            Card(
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .padding(5.dp)
                    .size(100.dp, 150.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(18.dp)
                    )
                // .shadow(elevation.value)
            ) {
                Box {
                    Image(
                        modifier = Modifier,
                        painter = painterResource(id = player.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = player.name,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp)
                            .background(color = Color.Black.copy(alpha = 0.5f)),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ScrollTopCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {}
}

private const val SCROLL_DX = 25f //scroll speed & direction
private const val SCROLL_DX2 = -25f

suspend fun ScrollableState.autoStartScroll(
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 80, easing = LinearEasing)
) {
    var previousValue = 0f
    scroll(MutatePriority.UserInput) {
        animate(
            initialValue = 0f,
            targetValue = SCROLL_DX2,
            animationSpec = animationSpec
        ) { currentValue, _ ->
            previousValue += scrollBy(currentValue - previousValue)
        }
    }
}

suspend fun ScrollableState.autoEndScroll(
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 80, easing = LinearEasing)
) {
    var previousValue = 0f
    scroll(MutatePriority.UserInput) {
        animate(
            initialValue = 0f,
            targetValue = SCROLL_DX,
            animationSpec = animationSpec
        ) { currentValue, _ ->
            previousValue += scrollBy(currentValue - previousValue)//- previousValue
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    DragContainer(modifier = Modifier.fillMaxSize()) {
        PlayerSlotSheet(
            lazyGridState = rememberLazyGridState(),
            players = PlayersTabViewModel().players,
            onHeightInfo = {}
        )
    }
}