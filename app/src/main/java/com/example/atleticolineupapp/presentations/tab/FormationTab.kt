package com.example.atleticolineupapp.presentations.tab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.atleticolineupapp.presentations.navigation.FormationDestination

@Composable
fun FormationSheet(
    modifier: Modifier = Modifier,
    allScreens: List<FormationDestination>,
    onTabSelected: (FormationDestination) -> Unit,
    currentScreen: FormationDestination

) {
    LazyRow(
        modifier = modifier.fillMaxWidth().padding(bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items(items = allScreens) { formation ->
            FormationCard(
                image = formation.formationImage,
                onSelected = { onTabSelected(formation) },
                selected = currentScreen == formation
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormationCard(
    image: Int,
    onSelected: () -> Unit,
    selected: Boolean
) {
    Card(
        onClick = onSelected,
        modifier = Modifier
            .padding(3.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(4.dp, Color.LightGray)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(300.dp)
                .width(200.dp)
        )
    }
}