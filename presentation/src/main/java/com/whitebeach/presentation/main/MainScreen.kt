package com.whitebeach.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.whitebeach.presentation.component.AtleticoTopAppBar
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = MainDestination.fromRoute(
        route = backStackEntry?.destination?.route,
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            AtleticoTopAppBar(
                title = currentDestination.title,
            )
        },
        bottomBar = {
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
                },
            )
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