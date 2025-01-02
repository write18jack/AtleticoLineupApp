package com.whitebeach.presentation.main.view

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whitebeach.presentation.R

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
                .paint(painter = painterResource(id = R.drawable.atletico_logo))
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

@Preview
@Composable
fun TopBatPreview() {

    Scaffold(
        Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        topBar = {
            TopAppBarItems(
                Modifier.fillMaxWidth(),
                clickShare = {}
            )
        },
        bottomBar = {}
    ) {
        Box(modifier = Modifier.padding(it))
    }

}