package com.whitebeach.presentation.matches.list

import com.whitebeach.presentation.matches.model.MatchUiModel

sealed interface MatchesUiState {

    data object Loading : MatchesUiState

    data class Success(
        val matches: List<MatchUiModel>,
        val isRefreshing: Boolean = false,
        val refreshError: String? = null,
    ) : MatchesUiState

    data class Error(val message: String) : MatchesUiState
}