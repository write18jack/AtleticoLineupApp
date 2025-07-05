package com.whitebeach.presentation

import android.app.Application
import androidx.test.runner.AndroidJUnitRunner
import com.google.android.gms.ads.MobileAds

class AdMobTestRunner : AndroidJUnitRunner() {
    override fun callApplicationOnCreate(app: Application) {
        // テストアプリ起動時にAdMobを初期化
        MobileAds.initialize(app) {}
        super.callApplicationOnCreate(app)
    }
}