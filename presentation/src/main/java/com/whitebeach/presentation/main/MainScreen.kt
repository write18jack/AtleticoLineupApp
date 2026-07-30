package com.whitebeach.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whitebeach.presentation.component.AtleticoTopAppBar
import com.whitebeach.presentation.matches.MatchesScreen
import com.whitebeach.presentation.players.PlayersScreen
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    var currentDestination by rememberSaveable {
        mutableStateOf(MainDestination.PLAYERS)
    }

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
                    currentDestination = destination
                },
            )
        },
    ) { innerPadding ->
        when (currentDestination) {
            MainDestination.PLAYERS -> {
                PlayersScreen(
                    modifier = Modifier.padding(innerPadding),
                )
            }

            MainDestination.MATCHES -> {
                MatchesScreen(
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}

@Composable
private fun MainBottomBar(
    currentDestination: MainDestination,
    onDestinationSelected: (MainDestination) -> Unit,
) {
    NavigationBar {
        MainDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination,
                onClick = {
                    onDestinationSelected(destination)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label,
                    )
                },
                label = {
                    Text(text = destination.label)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    AtleticoLineupAppTheme {
        MainScreen()
    }
}