package com.whitebeach.presentation.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.domain.usecase.GetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val matchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<MatchesUiState>(MatchesUiState.Loading)

    val uiState: StateFlow<MatchesUiState> =
        _uiState.asStateFlow()

    init {
        loadMatches()
    }

    fun loadMatches() {
        viewModelScope.launch {
            _uiState.value = MatchesUiState.Loading

            _uiState.value = try {
                val matches = matchesUseCase()

                MatchesUiState.Success(
                    matches = matches.toUiModels(),
                )
            } catch (exception: Exception) {
                MatchesUiState.Error(
                    message = exception.message
                        ?: "Failed to load matches.",
                )
            }
        }
    }
}