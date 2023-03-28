package com.example.atleticolineupapp

import androidx.compose.runtime.*

// state & logic
@Stable
class ControlTabState {
    var expandedFormation: Boolean by mutableStateOf(false)
    private set
    var expandedSub: Boolean by mutableStateOf(false)
    private set

    fun clickedExpandedFormation(){
        if (expandedSub){
            closeSub()
        }
        expandedFormation = !expandedFormation
    }
    fun clickExpandedSub(){
        if (expandedFormation){
            closeFormation()
        }
        expandedSub = !expandedSub
    }

    private fun closeSub(){
        expandedSub = false
    }

    private fun closeFormation(){
        expandedFormation = false
    }
}