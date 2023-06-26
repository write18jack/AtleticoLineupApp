package com.example.atleticolineupapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.R
import com.example.atleticolineupapp.ui.tab.FormationTabViewModel
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController

@Composable
fun CaptureSample(vm2: FormationTabViewModel = viewModel()){

    val constraintSetItem by vm2.constraintSetItem.collectAsState()
    //ScreenShot
    val captureController = rememberCaptureController()

    // This will hold captured bitmap
    // So that we can demo it
    var ticketBitmap: ImageBitmap? by remember { mutableStateOf(null) }

    // When Ticket's Bitmap image is captured, show preview in dialog
    ticketBitmap?.let { bitmap ->
        Dialog(onDismissRequest = { }) {
            Column(
                modifier = Modifier
                    .background(LightGray)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Preview of Ticket image \uD83D\uDC47")
                Spacer(Modifier.size(16.dp))
                Image(
                    bitmap = bitmap,
                    contentDescription = "Preview of ticket",
                )
                Spacer(Modifier.size(4.dp))
                Button(onClick = { ticketBitmap = null }) {
                    Text("Close Preview")
                }
            }
        }
    }

   Scaffold(
       topBar = {
           TopAppBar() {
               androidx.compose.material3.IconButton(onClick = {captureController.capture()}) {
                   androidx.compose.material3.Icon(
                       imageVector = Icons.Filled.Share,
                       contentDescription = "",
                       tint = Color.White
                   )
               }
           }
                },
       bottomBar = {
           BottomAppBar() {
               
           }
       }
   ){ it ->
       Box(
           Modifier
               .padding(it)
               .fillMaxSize()){
           Capturable(
               controller = captureController,
               modifier = Modifier.align(alignment = Alignment.Center),
               onCaptured = { bitmap, error -> ticketBitmap = bitmap }
           ) {
               DisplayFormation(
                   modifier = Modifier.paint(
                       painter = painterResource(id = R.drawable.pitch),
                       contentScale = ContentScale.FillBounds
                   ),
                   manageFormation = constraintSetItem
               )
           }

           // Captures ticket bitmap on click
           Button(
               onClick = { captureController.capture() },
               modifier = Modifier.align(alignment = Alignment.BottomCenter)
           ) {
               Text("PUSH")
           }
       }
    }
}

@Preview
@Composable
fun PreCap(){
    //CaptureSample()
}