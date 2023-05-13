package com.example.atleticolineupapp.ui.tab

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.atleticolineupapp.util.PlayerItem
import com.example.atleticolineupapp.util.PlayerList
import org.burnoutcrew.reorderable.ItemPosition

@Stable
class PlayersTabViewModel : ViewModel() {
    var players by mutableStateOf(List(13) {
        PlayerItem(
            name = PlayerList[it].name,
            image = PlayerList[it].image,
            key = it
        )
    })

    fun movePlayer(from: ItemPosition, to: ItemPosition) {
        players = players.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }
}