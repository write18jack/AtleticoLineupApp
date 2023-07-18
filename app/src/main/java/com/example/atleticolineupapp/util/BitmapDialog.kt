package com.example.atleticolineupapp.util

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

@Composable
fun BitmapDialog(
    closeDialog: () -> Unit,
    imageBitmap: Bitmap,
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()

    Dialog(onDismissRequest = { }) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Preview of image \uD83D\uDC47")
            Spacer(Modifier.size(13.dp))
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = "Preview of image",
            )
            Spacer(Modifier.size(4.dp))
            Row(horizontalArrangement = Arrangement.Center) {

                IconButton(onClick = closeDialog) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            shareNoteImage(imageBitmap, context)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

suspend fun shareNoteImage(bitmap: Bitmap, context: Context) {
    val uri = saveImage(bitmap, context)
    if (uri != null) {
        shareImageUri(context, uri)
    } else {
        Toast.makeText(
            context,
            "uri is null",
            Toast.LENGTH_SHORT
        ).show()
    }
}