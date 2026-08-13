package com.whitebeach.presentation.players.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.domain.usecase.ObservePlayersUseCase
import com.whitebeach.domain.usecase.RefreshPlayersUseCase
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
class PlayersViewModel @Inject constructor(
    observePlayersUseCase: ObservePlayersUseCase,
    private val refreshPlayersUseCase: RefreshPlayersUseCase,
) : ViewModel() {

    private val refreshState = MutableStateFlow<RefreshState>(RefreshState.Idle)

    // flow一本で値を監視する。load()呼ばない。
    val uiState: StateFlow<PlayersUiState> =
        combine(
            observePlayersUseCase(),
            refreshState,
        ) { players, refreshState ->
            when {
                players.isEmpty() && refreshState is RefreshState.Loading -> {
                    PlayersUiState.Loading
                }

                players.isEmpty() && refreshState is RefreshState.Error -> {
                    PlayersUiState.Error(message = "選手情報を取得できませんでした")
                }

                players.isNotEmpty() && refreshState is RefreshState.Error -> {
                    PlayersUiState.Success(
                        players = players.toUiModels(),
                        refreshError = "オフラインのデータを表示しています",
                    )
                }

                else -> {
                    PlayersUiState.Success(
                        players = players.toUiModels(),
                        isRefreshing = refreshState is RefreshState.Loading,
                        refreshError = null,
                    )
                }
            }
        }
            .catch { exception ->
                emit(
                    PlayersUiState.Error(
                        message = exception.message
                            ?: "Failed to load players.",
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5_000,
                ),
                initialValue = PlayersUiState.Loading
            )

    init {
        refreshPlayers()
    }

    fun refreshPlayers() {
        viewModelScope.launch {
            refreshState.value = RefreshState.Loading

            try {
                refreshPlayersUseCase()

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