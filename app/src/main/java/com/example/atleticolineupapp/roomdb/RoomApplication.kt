package com.example.atleticolineupapp.roomdb

import android.app.Application

class RoomApplication:Application(){
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}