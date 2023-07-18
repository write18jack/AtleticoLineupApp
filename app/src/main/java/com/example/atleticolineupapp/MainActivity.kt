package com.example.atleticolineupapp

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.atleticolineupapp.ui.screens.MainScreen
import com.example.atleticolineupapp.ui.theme.AtleticoLineupAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, true)
//        window.statusBarColor = android.graphics.Color.TRANSPARENT
//        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        setContent {
            AtleticoLineupAppTheme {
                MainScreen()
            }
        }
    }
}
@HiltAndroidApp
class ComposeApplication: Application()