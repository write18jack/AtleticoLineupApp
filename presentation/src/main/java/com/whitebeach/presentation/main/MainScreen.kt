package com.whitebeach.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.whitebeach.presentation.main.component.AtleticoTopAppBar
import com.whitebeach.presentation.main.component.MainBottomBar
import com.whitebeach.presentation.navigation.AtleticoNavHost
import com.whitebeach.presentation.navigation.MainDestination
import com.whitebeach.presentation.navigation.PlayerDetailDestination
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry?.destination?.route

    val currentDestination =
        MainDestination.entries.firstOrNull { destination ->
            destination.route == currentRoute
        }

    val isPlayerDetail =
        currentRoute == PlayerDetailDestination.route

    val title = when {
        currentDestination != null -> currentDestination.title
        isPlayerDetail -> "Player Details"
        else -> "Atlético Madrid"
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AtleticoTopAppBar(
                title = title,
                onBackClick = if (isPlayerDetail) {
                    {
                        navController.popBackStack()
                    }
                } else {
                    null
                },
            )
        },
        bottomBar = {
            if (currentDestination != null) {
                MainBottomBar(
                    currentDestination = currentDestination,
                    onDestinationSelected = { destination ->
                        navController.navigate(destination.route) {
                            popUpTo(MainDestination.PLAYERS.route) {
                                // タブを切り替え続けたとき、同じ画面がバックスタックへ大量に積まれるのを防ぐ
                                saveState = true
                            }

                            // 現在Players画面なのに、もう一度Playersを押した場合の重複作成を防ぐ
                            launchSingleTop = true
                            // 別タブから戻ったとき、保存されている画面状態の復元を試みる
                            restoreState = true
                        }
                    }
                )
            }
        },
    ) { innerPadding ->
        AtleticoNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    AtleticoLineupAppTheme {
        MainScreen()
    }
}