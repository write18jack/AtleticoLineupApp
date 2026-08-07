package com.whitebeach.presentation.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.domain.model.Match
import com.whitebeach.domain.usecase.ObserveMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    observeMatchesUseCase: ObserveMatchesUseCase,
) : ViewModel() {

    val uiState = observeMatchesUseCase()
        .map<List<Match>, MatchesUiState> { matches ->
            MatchesUiState.Success(
                matches = matches.toUiModels(),
            )
        }
        .onStart {
            emit(MatchesUiState.Loading)
        }
        .catch { exception ->
            emit(
                MatchesUiState.Error(
                    message = exception.message
                        ?: "Failed to load matches.",
                ),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MatchesUiState.Loading,
        )
}