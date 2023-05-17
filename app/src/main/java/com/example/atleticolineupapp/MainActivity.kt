package com.example.atleticolineupapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.atleticolineupapp.ui.screens.MainScreen
import com.example.atleticolineupapp.ui.theme.AtleticoLineupAppTheme
import com.example.atleticolineupapp.util.drag.DragContainer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        setContent {
            AtleticoLineupAppTheme {
                MainScreen()
            }
        }
    }
}