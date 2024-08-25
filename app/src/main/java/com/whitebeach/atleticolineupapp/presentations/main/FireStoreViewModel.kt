package com.whitebeach.atleticolineupapp.presentations.main

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.atleticolineupapp.data.model.remote.PlayerInfo
import com.whitebeach.atleticolineupapp.domain.usecase.getPlayerInFireStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections.emptyList

class FireStoreViewModel : ViewModel() {

    var state = mutableStateListOf<PlayerInfo>()
    private val _playerDataList = MutableStateFlow<List<PlayerInfo>>(emptyList())
    val playerDataList: StateFlow<List<PlayerInfo>> = _playerDataList.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _playerDataList.value = getPlayerInFireStore()
        }
    }
}

