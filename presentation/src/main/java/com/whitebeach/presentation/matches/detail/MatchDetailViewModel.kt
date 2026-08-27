package com.whitebeach.presentation.matches.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.domain.usecase.GetMatchByIdUseCase
import com.whitebeach.presentation.matches.list.toUiModel
import com.whitebeach.presentation.navigation.MatchDetailDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMatchByIdUseCase: GetMatchByIdUseCase,
) : ViewModel() {

    private val matchId: Int = checkNotNull(
        savedStateHandle[MatchDetailDestination.MATCH_ID_ARGUMENT],
    )

    private val _uiState =
        MutableStateFlow<MatchDetailUiState>(
            MatchDetailUiState.Loading,
        )

    val uiState: StateFlow<MatchDetailUiState> =
        _uiState.asStateFlow()

    init {
        loadMatch()
    }

    fun loadMatch() {
        viewModelScope.launch {
            _uiState.value = MatchDetailUiState.Loading

            _uiState.value = try {
                val match = getMatchByIdUseCase(
                    matchId = matchId
                )

                MatchDetailUiState.Success(
                    match = match.toUiModel(),
                )
            } catch (exception: Exception) {
                MatchDetailUiState.Error(
                    message = exception.message
                        ?: "Failed to load match.",
                )
            }
        }
    }
}