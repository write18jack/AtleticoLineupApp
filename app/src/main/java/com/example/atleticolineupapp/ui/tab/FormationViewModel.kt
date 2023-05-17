package com.example.atleticolineupapp.ui.tab

import androidx.compose.runtime.toMutableStateList
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atleticolineupapp.ui.formation.F4141
import com.example.atleticolineupapp.ui.formation.F442
import com.example.atleticolineupapp.ui.formation.F532
import com.example.atleticolineupapp.ui.formation.ManageFormation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class FormationItem(
    val id: Int,
    val formation: String,
    var manageFormation: ManageFormation,
)

val formationItemList = listOf(
    FormationItem(id = 0, "4-4-2", F442()),
    FormationItem(id = 1,"4-3-3", F442()),
    FormationItem(id = 2,"4-1-4-1", F4141()),
    FormationItem(id = 3,"5-3-2", F532()),
    FormationItem(id = 4,"3-1-4-2", F442())
)

class FormationViewModel : ViewModel() {
    private val _constraintSetList = formationItemList.toMutableStateList()
    val constraintSetList: List<FormationItem>
        get() = _constraintSetList

    private val _constraintSetItem = MutableStateFlow<ManageFormation>(F532())
    val constraintSetItem: StateFlow<ManageFormation> = _constraintSetItem

//    fun selectedFormation(item: ConstraintSet) {
//        constraintSetList.find { it.constraintSet == item }?.let { task ->
//            task.constraintSet = item
//        }
//    }
    fun onChangeConstraintItem(newValue: ManageFormation){
        viewModelScope.launch {
            _constraintSetItem.value = newValue
        }
    }
}