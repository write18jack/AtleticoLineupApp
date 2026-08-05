package com.whitebeach.presentation.matches

import com.whitebeach.presentation.matches.model.MatchUiModel

sealed interface MatchesUiState {

    data object Loading : MatchesUiState

    data class Success(
        val matches: List<MatchUiModel>,
    ) : MatchesUiState

    data class Error(
        val message: String,
    ) : MatchesUiState
}