package com.whitebeach.atleticolineupapp.presentations.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whitebeach.atleticolineupapp.app.theme.MidNightBlue

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    openPlayerSheet: () -> Unit,
    openFormationSheet: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 7.dp, end = 7.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MidNightBlue),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(
                onClick = openPlayerSheet
            ) {
                Icon(Icons.Default.Person, contentDescription = "", tint = Color.White)
            }

            IconButton(
                onClick = openFormationSheet
            ) {
                Icon(Icons.Default.List, contentDescription = "", tint = Color.White)
            }
        }
    }
}