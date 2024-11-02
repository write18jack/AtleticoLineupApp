package com.whitebeach.atleticolineupapp.presentations.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.whitebeach.atleticolineupapp.app.theme.AtleticoLineupAppTheme
import com.whitebeach.atleticolineupapp.app.component.dragDrop.DragContainer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //Calling SplashScreen
        installSplashScreen()

        super.onCreate(savedInstanceState)
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            AtleticoLineupAppTheme {
                DragContainer {
                    MainScreen()
                }
            }
        }
        // initialize the Mobile Ads SDK
        //MobileAds.initialize(this){}
    }
}