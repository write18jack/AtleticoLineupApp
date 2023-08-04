package com.example.atleticolineupapp.ui.tab

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.atleticolineupapp.util.PlayerItem
import com.example.atleticolineupapp.util.PlayerList

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