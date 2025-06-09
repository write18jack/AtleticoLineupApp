package com.whitebeach.atleticolineupapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.whitebeach.presentation.component.dragDrop.DragContainer
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme
import com.whitebeach.presentation.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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