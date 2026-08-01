package com.whitebeach.presentation.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.domain.repository.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val repository: PlayersRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<PlayersUiState>(PlayersUiState.Loading)

    val uiState: StateFlow<PlayersUiState> =
        _uiState.asStateFlow()

    init {
        loadPlayers()
    }

    fun loadPlayers() {
        viewModelScope.launch {
            _uiState.value = PlayersUiState.Loading

            _uiState.value = try {
                val players = repository.getPlayers()

                PlayersUiState.Success(
                    players = players.toUiModels(),
                )
            } catch (exception: Exception) {
                PlayersUiState.Error(
                    message = exception.message
                        ?: "Failed to load players.",
                )
            }
        }
    }
}