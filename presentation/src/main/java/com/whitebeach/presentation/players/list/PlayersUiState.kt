package com.whitebeach.presentation.players.list

import com.whitebeach.presentation.players.model.PlayerUiModel

sealed interface PlayersUiState {

    data object Loading : PlayersUiState

    data class Success(
        val players: List<PlayerUiModel>,
        val isRefreshing: Boolean = false,
        val refreshError: String? = null,
    ) : PlayersUiState

    data class Error(
        val message: String,
    ) : PlayersUiState
}