package com.whitebeach.presentation.players

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor() : ViewModel() {

    private val _uiState =
        MutableStateFlow<PlayersUiState>(PlayersUiState.Loading)

    val uiState: StateFlow<PlayersUiState> =
        _uiState.asStateFlow()

    init {
        loadPlayers()
    }

    fun loadPlayers() {
        _uiState.value = PlayersUiState.Loading

        _uiState.value = PlayersUiState.Success(
            players = dummyPlayers,
        )
    }
}