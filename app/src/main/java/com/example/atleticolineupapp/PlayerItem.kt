package com.example.atleticolineupapp

import androidx.annotation.DrawableRes

data class PlayerItem(val name: String, @DrawableRes val image: Int)

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