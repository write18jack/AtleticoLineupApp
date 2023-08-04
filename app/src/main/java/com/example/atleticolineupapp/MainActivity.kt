package com.example.atleticolineupapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.atleticolineupapp.ui.screens.MainScreen
import com.example.atleticolineupapp.ui.theme.AtleticoLineupAppTheme
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

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
                MainScreen()
            }
        }
        // initialize the Mobile Ads SDK
        MobileAds.initialize(this){}
    }
}

@HiltAndroidApp
class ComposeApplication: Application()