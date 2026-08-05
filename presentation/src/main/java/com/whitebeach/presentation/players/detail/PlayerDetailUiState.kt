package com.whitebeach.presentation.players.detail

import com.whitebeach.presentation.players.model.PlayerUiModel

sealed interface PlayerDetailUiState {

    data object Loading : PlayerDetailUiState

    data class Success(
        val player: PlayerUiModel,
    ) : PlayerDetailUiState

    data class Error(
        val message: String,
    ) : PlayerDetailUiState
}