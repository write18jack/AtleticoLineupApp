package com.whitebeach.presentation.playerSheet

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.data.remote.PlayerApi
import com.whitebeach.data.model.player.ResponseX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PlayersUiState {
    data class Success(val players: MutableList<ResponseX>) : PlayersUiState
    object Error : PlayersUiState
    object Loading : PlayersUiState
}

sealed class ResultUiState<out T> {
    //object Idle : ResultUiState<Nothing>()
    object Loading : ResultUiState<Nothing>()
    data class Success<T>(val data: T) : ResultUiState<T>()
    data class Error(val error: String) : ResultUiState<Nothing>()
}

@Stable
class RapidApiViewModel : ViewModel() {
//    var playersUiState: PlayersUiState by mutableStateOf(PlayersUiState.Loading)
//        private set

    private val _playersUiState =
        MutableStateFlow<List<ResponseX>>(emptyList())
    val playersUiState: StateFlow<List<ResponseX>> = _playersUiState.asStateFlow()

    init {
        getPlayersInfo()
    }

    private fun getPlayersInfo() {
        //_playersUiState.value = ResultUiState.Loading
        viewModelScope.launch {
            try {
                val response = PlayerApi.retrofitService.getPlayers().body()!!.response.toMutableList()
                response += PlayerApi.retrofitService.getPlayers2().body()!!.response
                response += PlayerApi.retrofitService.getPlayers3().body()!!.response
                if (response.isNotEmpty()) {
                    _playersUiState.value = response
                }
            } catch (e: IOException) {
                _playersUiState.value = emptyList()
            } catch (e: HttpException) {
                _playersUiState.value = emptyList()
            }
        }
    }
}