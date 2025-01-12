package com.whitebeach.presentation.formationSheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.whitebeach.presentation.formation.F3142
import com.whitebeach.presentation.formation.F4141
import com.whitebeach.presentation.formation.F433
import com.whitebeach.presentation.formation.F442
import com.whitebeach.presentation.formation.F532
import com.whitebeach.presentation.formation.ManageFormation

data class FormationItem(
    val id: Int,
    val formation: String,
    var manageFormation: ManageFormation,
)

val formationItemList = listOf(
    FormationItem(id = 0, "4-4-2", F442()),
    FormationItem(id = 1, "4-3-3", F433()),
    FormationItem(id = 2, "4-1-4-1", F4141()),
    FormationItem(id = 3, "5-3-2", F532()),
    FormationItem(id = 4, "3-1-4-2", F3142())
)

data class FormationFactoryFunction(
    val manageFormation: ManageFormation,
    val changeFormation: (newValue: ManageFormation) -> Unit
)

@Composable
fun rememberFormation(): FormationFactoryFunction {
    var formationItem by remember { mutableStateOf<ManageFormation>(F532()) }
    return remember(formationItem) {
        FormationFactoryFunction(
            manageFormation = formationItem,
            changeFormation = { newValue ->
                formationItem = newValue
            }
        )
    }
}