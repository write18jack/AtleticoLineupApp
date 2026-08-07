package com.whitebeach.presentation.players.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.domain.usecase.GetPlayerByIdUseCase
import com.whitebeach.presentation.navigation.PlayerDetailDestination
import com.whitebeach.presentation.players.list.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlayerByIdUseCase: GetPlayerByIdUseCase,
) : ViewModel() {

    private val playerId: Int = checkNotNull(
        savedStateHandle[
            PlayerDetailDestination.PLAYER_ID_ARGUMENT
        ],
    )

    private val _uiState =
        MutableStateFlow<PlayerDetailUiState>(
            PlayerDetailUiState.Loading,
        )

    val uiState: StateFlow<PlayerDetailUiState> =
        _uiState.asStateFlow()

    init {
        loadPlayer()
    }

    fun loadPlayer() {
        viewModelScope.launch {
            _uiState.value = PlayerDetailUiState.Loading

            _uiState.value = try {
                val player = getPlayerByIdUseCase(
                    playerId = playerId,
                )

                PlayerDetailUiState.Success(
                    player = player.toUiModel(),
                )
            } catch (exception: Exception) {
                PlayerDetailUiState.Error(
                    message = exception.message
                        ?: "Failed to load player.",
                )
            }
        }
    }
}