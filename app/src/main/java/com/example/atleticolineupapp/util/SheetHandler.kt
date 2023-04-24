package com.example.atleticolineupapp.util

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetState.Companion.Saver
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

data class SheetHandlerX @OptIn(ExperimentalMaterial3Api::class) constructor(
    val initialValue: SheetValue,
    val skipCollapsed: Boolean,
    val confirmValueChange: (SheetValue) -> Boolean = {true}
){
    @OptIn(ExperimentalMaterial3Api::class)
    fun state(block: suspend SheetState.() -> Unit){
        stateScope.value = block
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private val stateScope = mutableStateOf<(suspend SheetState.()->Unit)?>(null)
    @OptIn(ExperimentalMaterial3Api::class)
    private lateinit var _state: SheetState

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun handle(animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec):SheetState{
        _state = rememberSaveable(
            initialValue, animationSpec, skipCollapsed, confirmValueChange,
            saver = SheetState.Saver(
//                animationSpec = animationSpec,
                skipHalfExpanded = skipCollapsed,
                confirmValueChange = confirmValueChange
            )
        ) {
            SheetState(
                initialValue = initialValue,
//                animationSpec = animationSpec,
                skipCollapsed = skipCollapsed,
                confirmValueChange = confirmValueChange
            )
        }
        LaunchedEffect(key1 = stateScope.value){
            stateScope.value?.let {
                it(_state)
                stateScope.value = null
            }
        }
        return _state
    }
}

data class SheetHandler @OptIn(ExperimentalMaterialApi::class) constructor(
    val initialValue: ModalBottomSheetValue,
    val skipHalfExpanded: Boolean,
    val confirmStateChange: (ModalBottomSheetValue) -> Boolean = { true }
){
    @OptIn(ExperimentalMaterialApi::class)
    fun state(block: suspend ModalBottomSheetState.()->Unit){
        stateScope.value = block
    }

    @OptIn(ExperimentalMaterialApi::class)
    private val stateScope = mutableStateOf<(suspend ModalBottomSheetState.()->Unit)?>(null)
    @OptIn(ExperimentalMaterialApi::class)
    private lateinit var _state: ModalBottomSheetState

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun handle(animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec): ModalBottomSheetState {
        _state = rememberSaveable(
            initialValue, animationSpec, skipHalfExpanded, confirmStateChange,
            saver = Saver(
                animationSpec = animationSpec,
                confirmValueChange = confirmStateChange,
                skipHalfExpanded = skipHalfExpanded
            )
        ) {
            ModalBottomSheetState(
                initialValue = initialValue,
                animationSpec = animationSpec,
                isSkipHalfExpanded = skipHalfExpanded,
                confirmStateChange = confirmStateChange
            )
        }
        LaunchedEffect(key1 = stateScope.value){
            stateScope.value?.let {
                it(_state)
                stateScope.value = null
            }
        }
        return _state
    }
}