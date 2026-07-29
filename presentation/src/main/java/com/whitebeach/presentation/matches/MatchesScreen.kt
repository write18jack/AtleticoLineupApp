package com.whitebeach.presentation.matches

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whitebeach.presentation.theme.AtleticoLineupAppTheme

@Composable
fun MatchesScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Matches Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchesScreenPreview() {
    AtleticoLineupAppTheme {
        MatchesScreen()
    }
}