package com.whitebeach.atleticolineupapp.ui.tab

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.whitebeach.atleticolineupapp.util.PlayerItem
import com.whitebeach.atleticolineupapp.util.PlayerList

@Stable
class PlayersTabViewModel : ViewModel() {
    var players by mutableStateOf(List(PlayerList.size) {
        PlayerItem(
            name = PlayerList[it].name,
            image = PlayerList[it].image,
            key = it
        )
    })
}