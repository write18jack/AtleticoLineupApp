package com.whitebeach.atleticolineupapp.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whitebeach.atleticolineupapp.R

@Composable
fun TopAppBarX(
    modifier: Modifier,
    openCaptureDialog: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Red),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.size(50.dp)) {
            
        }
        Box(
            Modifier
                .size(50.dp)
                .paint(painter = painterResource(id = R.drawable.atletico_logo))){
        }
        IconButton(onClick = openCaptureDialog ) {
            Icon(imageVector = Icons.Filled.Share, contentDescription = "", tint = Color.White)
        }
    }
}

@Preview
@Composable
fun TopBarPreview(){
    TopAppBarX(modifier = Modifier, openCaptureDialog = {})
}