package com.whitebeach.atleticolineupapp.presentations.playerSheet

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.whitebeach.atleticolineupapp.data.model.remote.PlayerInfo
import com.whitebeach.atleticolineupapp.dragAndDrop.MimeType
import com.whitebeach.atleticolineupapp.app.component.dragDrop.DragData
import com.whitebeach.atleticolineupapp.app.component.dragDrop.DragTarget
import com.whitebeach.atleticolineupapp.data.model.remote.player.Player
import com.whitebeach.atleticolineupapp.data.model.remote.player.ResponseX

@Composable
fun PlayerSheet(
    modifier: Modifier = Modifier,
    //playersUiState: PlayersUiState,
    //getPlayersInfo: () -> Unit = {},
    //list: List<PlayerInfo>
    //onClick: (playerItem: PlayerItem) -> Unit
    //rapidApiViewModel: RapidApiViewModel = viewModel(),
) {
   // val playersUiState = rapidApiViewModel.playersUiState.collectAsState()
    //val playerList = rapidApiViewModel.playersUiState.collectAsState()

    PlayerSheetComponent(playerList = playerList.value)
}

@Composable
fun PlayerSheetComponent(
    playerList: List<ResponseX>
){
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier.height(280.dp),
        contentPadding = PaddingValues(horizontal = 5.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(
            items = playerList,
            key = { it.player.id }
        ) { item ->
            PlayerCard(
                player = item.player,
                //onClick = { onClick(item) }
            )
        }
    }
}

@Composable
fun PlayerCard(
    player: Player,
//    onClick: () -> Unit
) {
    val imageUrl = player.photo
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
                    text = player.name,
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

@Preview
@Composable
fun PlayerSheetPreview(){
    val list = listOf(
        PlayerInfo()
    )
}