package com.whitebeach.atleticolineupapp.presentations.playerSheet

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.atleticolineupapp.data.model.remote.player.ResponseX
import com.whitebeach.atleticolineupapp.data.remote.di.PlayerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface PlayersUiState {
    data class Success(val players: List<ResponseX>) : PlayersUiState
    object Error : PlayersUiState
    object Loading : PlayersUiState
}

@Stable
class RapidapiViewModel: ViewModel() {
    var playersUiState: PlayersUiState by mutableStateOf(PlayersUiState.Loading)
        private set

    private val _storePlayersList = MutableStateFlow<List<ResponseX>>(emptyList())
    val storePlayersList: StateFlow<List<ResponseX>> = _storePlayersList.asStateFlow()

    init {
        getPlayersInfoX()
    }

    fun getPlayersInfoX() {
        viewModelScope.launch {
            try {
                _storePlayersList.value += PlayerApi.retrofitService.getPlayers().body()!!.response
                _storePlayersList.value += PlayerApi.retrofitService.getPlayers2().body()!!.response
                _storePlayersList.value += PlayerApi.retrofitService.getPlayers3().body()!!.response

            }catch (e: IOException){
                PlayersUiState.Error
            }
        }
    }

//    fun getPlayersInfo() {
//        viewModelScope.launch {
//            playersUiState = try {
//                _storePlayersList.value += PlayerApi.retrofitService.getPlayers().body()!!.response
//                _storePlayersList.value += PlayerApi.retrofitService.getPlayers2().body()!!.response
//                _storePlayersList.value += PlayerApi.retrofitService.getPlayers3().body()!!.response
//                PlayersUiState.Success(_storePlayersList.value)
//            }catch (e: IOException){
//                PlayersUiState.Error
//            }
//        }
//    }
}