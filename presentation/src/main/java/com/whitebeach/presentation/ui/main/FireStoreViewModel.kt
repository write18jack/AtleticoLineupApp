package com.whitebeach.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.data.model.PlayerInfo
import com.whitebeach.data.remote.firestore.playerFireStoreDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FireStoreViewModel() : ViewModel() {

    var playerDataList: StateFlow<List<PlayerInfo>> = MutableStateFlow(emptyList())

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            playerDataList = playerFireStoreDataSource()
        }
    }
}
