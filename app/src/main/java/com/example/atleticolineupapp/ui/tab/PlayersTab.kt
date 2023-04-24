package com.example.atleticolineupapp.ui.tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atleticolineupapp.util.drag.DragData
import com.example.atleticolineupapp.util.drag.DragTarget
import com.example.atleticolineupapp.dragAndDrop.MimeType
import com.example.atleticolineupapp.util.PlayerItem
import com.example.atleticolineupapp.util.PlayerList

@Composable
fun PlayerSlotSheet(
    modifier: Modifier
) {
    LazyHorizontalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(310.dp),
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(items = PlayerList) { player ->
            Box{
                Card(
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .padding(5.dp)
                        .size(100.dp, 140.dp)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(18.dp)
                        )
                ){
                    Icon(Icons.Filled.Person, contentDescription = "", tint = Color.Red)
                }
                PlayerCard(player)
            }
        }
    }
}

@Composable
fun PlayerCard(player: PlayerItem) {
    val dragImage = painterResource(id = player.image)//Cast to Painter at here.
    val dragData = DragData(type = MimeType.IMAGE_JPEG, data = dragImage)
    DragTarget(modifier = Modifier.size(110.dp, 140.dp), dragData = dragData) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .padding(5.dp)
                .size(100.dp, 140.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(18.dp)
                )
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

@Preview(showBackground = true)
@Composable
fun Nn(){
    PlayerSlotSheet(modifier = Modifier)
}