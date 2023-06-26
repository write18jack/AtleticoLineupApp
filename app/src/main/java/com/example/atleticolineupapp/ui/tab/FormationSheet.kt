package com.example.atleticolineupapp.ui.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.ui.formation.ManageFormation

@Composable
fun FormationTab(
    modifier: Modifier = Modifier,
    list: List<FormationItem>,
    onCLickTask: (manageFormation: ManageFormation)->Unit
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(5.dp)
    ){
        items(
            items = list,
            key = { item -> item.id }
        ){FormationItem ->
            FormationCard(
                formationItem = FormationItem,
                onClick = { onCLickTask(FormationItem.manageFormation) }
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
            text = formationItem.formation,fontSize = 18.sp
        )
    }
}