package com.whitebeach.presentation.matches.detail

import com.whitebeach.presentation.matches.model.MatchUiModel

sealed interface MatchDetailUiState {

    data object Loading : MatchDetailUiState

    data class Success(
        val match: MatchUiModel,
    ) : MatchDetailUiState

    data class Error(
        val message: String,
    ) : MatchDetailUiState
}