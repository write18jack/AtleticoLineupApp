package com.whitebeach.presentation.players.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.domain.model.Player
import com.whitebeach.domain.usecase.ObservePlayersUseCase
import com.whitebeach.domain.usecase.RefreshPlayersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    observePlayersUseCase: ObservePlayersUseCase,
    private val refreshPlayersUseCase: RefreshPlayersUseCase,
) : ViewModel() {

    // flow一本で値を監視する。load()呼ばない。
    val uiState = observePlayersUseCase()
        .map<List<Player>, PlayersUiState> { players ->
            PlayersUiState.Success(
                players = players.toUiModels(),
            )
        }
        .onStart {
            emit(PlayersUiState.Loading)
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

    fun refreshPlayers() {
        viewModelScope.launch {
            runCatching {
                refreshPlayersUseCase()
            }
        }
    }
}