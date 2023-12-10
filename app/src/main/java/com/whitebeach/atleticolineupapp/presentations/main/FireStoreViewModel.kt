package com.whitebeach.atleticolineupapp.presentations.main

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitebeach.atleticolineupapp.data.model.remote.PlayerInfo
import com.whitebeach.atleticolineupapp.domain.usecase.getPlayerInFireStore
import kotlinx.coroutines.launch

class FireStoreViewModel : ViewModel() {

    var state = mutableStateListOf<PlayerInfo>()
    init {
        getData()
    }
    private fun getData() {
        viewModelScope.launch {
            state = getPlayerInFireStore()
        }
    }
}

