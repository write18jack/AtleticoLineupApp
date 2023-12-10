package com.whitebeach.atleticolineupapp.presentations.playerSheet

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.atleticolineupapp.data.PlayerItem
import com.whitebeach.atleticolineupapp.data.PlayerList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Stable
class PlayersTabViewModel : ViewModel() {

    //keyをsetするためにViewModel内でもう一度リストを定義している
    var players by mutableStateOf(List(PlayerList.size) {
        PlayerItem(
            name = PlayerList[it].name,
            image = PlayerList[it].image,
            key = it
        )
    })

    private val _playerItemFlow = MutableStateFlow(PlayerItem(name = "", image = 0))
    val playerItemFlow: StateFlow<PlayerItem> = _playerItemFlow
    fun onChangePlayerItem(newValue: PlayerItem){
        viewModelScope.launch {
            _playerItemFlow.value = newValue
        }
    }
}