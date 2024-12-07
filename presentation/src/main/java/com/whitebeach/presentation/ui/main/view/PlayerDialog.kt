package com.whitebeach.presentation.ui.main.view

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whitebeach.data.model.PlayerInfo

@Composable
fun PlayerDialog(
    modifier: Modifier,
    playerInfo: PlayerInfo,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(18.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = playerInfo.image.toInt()),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .padding(top = 10.dp, start = 5.dp)
                        .clickable { onDismissRequest() },
                    tint = colorResource(R.color.white),
                )
            }
        }
    }
}
