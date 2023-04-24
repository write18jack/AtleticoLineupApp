package com.example.atleticolineupapp.ui.tab

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.atleticolineupapp.util.PlayerItem
import com.example.atleticolineupapp.util.PlayerList

class PlayersTabViewModel : ViewModel(){

    private val _slotList = PlayerList.toMutableStateList()
    val slotList: SnapshotStateList<PlayerItem>
        get() = _slotList

    var slots by mutableStateOf(List(26){ SlotData(key = "$it") })

    fun movePlayer(from: ItemPosition, to: ItemPosition){
        slots = slots.toMutableStateList().apply {
            add(to.index, removeAt(from.index))
        }
    }
}