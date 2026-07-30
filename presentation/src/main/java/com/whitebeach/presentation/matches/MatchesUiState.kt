package com.whitebeach.presentation.matches

sealed interface MatchesUiState {

    data object Loading : MatchesUiState

    data class Success(
        val matches: List<MatchUiModel>,
    ) : MatchesUiState

    data class Error(
        val message: String,
    ) : MatchesUiState
}