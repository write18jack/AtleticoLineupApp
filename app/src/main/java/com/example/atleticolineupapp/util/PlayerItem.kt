package com.example.atleticolineupapp.util

import androidx.annotation.DrawableRes
import com.example.atleticolineupapp.R

data class PlayerItem(val name: String, @DrawableRes val image: Int, val key: Int = -1)

val PlayerList = listOf(
    PlayerItem("GRIEZMANN", R.drawable.griezmann),
    PlayerItem("Saul", R.drawable.saul),
    PlayerItem("Griezmann", R.drawable.griezmann),
    PlayerItem("Saul", R.drawable.saul),
    PlayerItem("Griezmann", R.drawable.griezmann),
    PlayerItem("Saul", R.drawable.saul),
    PlayerItem("Griezmann", R.drawable.griezmann),
    PlayerItem("Saul", R.drawable.saul),
    PlayerItem("Saul", R.drawable.saul),
    PlayerItem("Saul", R.drawable.saul),
    PlayerItem("Griezmann", R.drawable.griezmann),
    PlayerItem("Saul", R.drawable.saul),
    PlayerItem("Saul", R.drawable.saul),
    )