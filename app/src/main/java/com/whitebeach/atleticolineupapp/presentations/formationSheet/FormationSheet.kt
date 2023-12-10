package com.whitebeach.atleticolineupapp.presentations.formationSheet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whitebeach.atleticolineupapp.presentations.formation.ManageFormation

@Composable
fun FormationSheet(
    modifier: Modifier = Modifier,
    list: List<FormationItem>,
    onCLickTask: (manageFormation: ManageFormation)->Unit
){
    SideEffect {
        Log.d("composeLog", "FormationTab composition!")
    }
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(5.dp)
    ){
        items(
            items = list,
            key = { item -> item.id }
        ){formationItem ->
            FormationCard(
                formationItem = formationItem,
                onClick = { onCLickTask(formationItem.manageFormation) }
            )
        }
    }
}

@Composable
fun FormationCard(
    formationItem: FormationItem,
    onClick: ()->Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(5.dp))
            .clickable(
                onClick = onClick
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(5.dp)
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = formationItem.formation,fontSize = 28.sp
        )
    }
}