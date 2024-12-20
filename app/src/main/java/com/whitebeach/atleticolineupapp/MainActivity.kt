package com.whitebeach.atleticolineupapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.android.gms.ads.MobileAds
import com.whitebeach.presentation.ui.dragDrop.DragContainer
import com.whitebeach.presentation.ui.theme.AtleticoLineupAppTheme
import com.whitebeach.presentation.ui.main.MainScreen

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
        MobileAds.initialize(this){}
    }
}