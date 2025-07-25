package com.whitebeach.presentation.playerSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.whitebeach.atleticolineupapp.dragAndDrop.MimeType
import com.whitebeach.data.model.player.ResponseX
import com.whitebeach.presentation.component.dragDrop.DragData
import com.whitebeach.presentation.component.dragDrop.DragTarget

@Composable
fun PlayerSheetCheck(
    modifier: Modifier = Modifier,
    playersUiState: PlayersUiState
) {
    when (playersUiState) {
        is PlayersUiState.Loading -> {
            Text(text = "Loading")
        }
        is PlayersUiState.Success -> {
            PlayerSheet(
                modifier = modifier,
                playersList = playersUiState.players
            )
        }
        is PlayersUiState.Error -> {
            Text(text = "Error")
        }
    }
}


@Composable
fun PlayerSheet(
    modifier: Modifier = Modifier,
    playersList: List<ResponseX>
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = modifier.height(280.dp),
        contentPadding = PaddingValues(horizontal = 5.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(
            items = playersList,
            key = { it.player.id }
        ) { item ->
            PlayerCard(
                player = item,
            )
            //onClick = { onClick(item) }
        }
    }
}

@Composable
fun PlayerCard(
    player: ResponseX,
//    onClick: () -> Unit
) {
    val imageUrl = player.player.photo
    val painter = rememberAsyncImagePainter(model = imageUrl)
    val dragData = DragData(type = MimeType.IMAGE_JPEG, data = painter)

    DragTarget(
        dragData = dragData
    ) {
        Card(
            // onClick = onClick,
            modifier = Modifier
                .padding(5.dp)
                .height(120.dp)
                .width(90.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(18.dp)
                ),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Box {
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillHeight
                )
                Text(
                    text = player.player.name,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                        .background(color = Color.Black.copy(alpha = 0.5f)),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
