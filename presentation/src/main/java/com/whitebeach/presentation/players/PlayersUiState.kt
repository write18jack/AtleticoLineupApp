package com.whitebeach.presentation.players

sealed interface PlayersUiState {

    data object Loading : PlayersUiState

    data class Success(
        val players: List<PlayerUiModel>,
    ) : PlayersUiState

    data class Error(
        val message: String,
    ) : PlayersUiState
}