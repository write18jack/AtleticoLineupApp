package com.whitebeach.presentation.playerSheet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.data.model.player.ResponseX
import com.whitebeach.data.repository.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RapidApiViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
)  : ViewModel() {
    var playersUiState: PlayersUiState by mutableStateOf(PlayersUiState.Loading)
        private set

    init {
        getPlayersInfo()
    }

    private fun getPlayersInfo() {
        viewModelScope.launch {
            playersUiState = PlayersUiState.Loading
            playersUiState = try {
                PlayersUiState.Success(playersRepository.getPlayers().body()!!.response)
            } catch (e: Exception) {
                PlayersUiState.Error(e.message ?: "unknown error")
            }
        }
    }
}

sealed interface PlayersUiState {
    object Loading : PlayersUiState
    data class Success(val players: List<ResponseX>) : PlayersUiState
    data class Error(val message: String) : PlayersUiState
}