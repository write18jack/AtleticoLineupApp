package com.example.atleticolineupapp.presentations.navigation

import androidx.annotation.DrawableRes
import com.example.atleticolineupapp.R

interface FormationDestination{
    val route: String
    @get: DrawableRes val formationImage: Int
}

object F442: FormationDestination {
    override val route = "f442"
    override val formationImage: Int
        get() = R.drawable.f442
}

object F3142: FormationDestination {
    override val route = "f3142"
    override val formationImage: Int
        get() = R.drawable.f3142
}

object F532: FormationDestination {
    override val route = "f532"
    override val formationImage: Int
        get() = R.drawable.f532
}

object F541: FormationDestination {
    override val route = "f541"
    override val formationImage: Int
        get() = R.drawable.f541
}

val formationScreens = listOf(F442, F3142, F541, F532)