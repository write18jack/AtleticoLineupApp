package com.whitebeach.atleticolineupapp.presentations.main

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.data.model.PlayerInfo
import com.whitebeach.data.remote.firestore.playerFireStoreDataSource
import kotlinx.coroutines.launch

class FireStoreViewModel : ViewModel() {

    var playerDataList = mutableStateListOf<PlayerInfo>()
//    private val _playerDataList = MutableStateFlow<List<PlayerInfo?>>(emptyList())
//    val playerDataList: StateFlow<List<PlayerInfo?>> = _playerDataList.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            playerDataList = playerFireStoreDataSource()
        }
    }
}

