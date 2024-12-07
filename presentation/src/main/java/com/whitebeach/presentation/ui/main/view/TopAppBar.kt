package com.whitebeach.presentation.ui.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBarItems(
    modifier: Modifier,
    clickShare: () -> Unit
) {
    Row(
        modifier = modifier.background(Color.Red),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier.weight(0.5f)
        )
        Box(
            modifier
                .weight(1f)
                .size(50.dp)
                .padding(3.dp)
                .paint(painter = painterResource(id = com.whitebeach.presentation.R.drawable.atletico_logo))
        )
        IconButton(
            modifier = modifier
                .weight(0.5f),
            onClick = clickShare
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}
