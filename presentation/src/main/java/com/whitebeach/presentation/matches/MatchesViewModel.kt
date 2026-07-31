package com.whitebeach.presentation.matches

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor() : ViewModel() {

    private val _uiState =
        MutableStateFlow<MatchesUiState>(MatchesUiState.Loading)

    val uiState: StateFlow<MatchesUiState> =
        _uiState.asStateFlow()

    init {
        loadMatches()
    }

    fun loadMatches() {
        _uiState.value = MatchesUiState.Loading

        _uiState.value = MatchesUiState.Success(
            matches = dummyMatches,
        )
    }
}