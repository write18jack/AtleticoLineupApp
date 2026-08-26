package com.whitebeach.presentation.matches.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.domain.usecase.ObserveMatchesUseCase
import com.whitebeach.domain.usecase.RefreshMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    observeMatchesUseCase: ObserveMatchesUseCase,
    private val refreshMatchesUseCase: RefreshMatchesUseCase,
) : ViewModel() {

    private val refreshState = MutableStateFlow<RefreshState>(RefreshState.Idle)

    val uiState: StateFlow<MatchesUiState> =
        combine(
            observeMatchesUseCase(),
            refreshState,
        ) { matches, refreshState ->

            when {
                matches.isEmpty() && refreshState is RefreshState.Loading -> {
                    MatchesUiState.Loading
                }

                matches.isEmpty() && refreshState is RefreshState.Error -> {
                    MatchesUiState.Error(message = "試合情報を取得できませんでした")
                }

                matches.isNotEmpty() && refreshState is RefreshState.Error -> {
                    MatchesUiState.Success(
                        matches = matches.toUiModels(),
                        refreshError = "オフラインのデータを表示しています",
                    )
                }

                else -> {
                    MatchesUiState.Success(
                        matches = matches.toUiModels(),
                        isRefreshing = refreshState is RefreshState.Loading,
                        refreshError = null,
                    )
                }
            }
        }
            .catch { exception ->
                emit(
                    MatchesUiState.Error(
                        message = exception.message
                            ?: "試合情報を取得できませんでした",
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MatchesUiState.Loading,
            )

    init {
        refreshMatches()
    }

    fun refreshMatches() {
        viewModelScope.launch {
            refreshState.value = RefreshState.Loading

            try {
                refreshMatchesUseCase()
                refreshState.value = RefreshState.Idle
            } catch (exception: Exception) {
                refreshState.value = RefreshState.Error
            }
        }
    }

    private sealed interface RefreshState {
        data object Idle : RefreshState
        data object Loading : RefreshState
        data object Error : RefreshState
    }
}