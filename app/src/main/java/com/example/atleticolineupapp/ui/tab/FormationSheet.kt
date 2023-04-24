package com.example.atleticolineupapp.ui.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atleticolineupapp.ui.formation.ManageFormation

//@Composable
//fun FormationSheet(
//    modifier: Modifier = Modifier,
//    allScreens: List<FormationDestination>,
//    onTabSelected: (FormationDestination) -> Unit,
//    currentScreen: FormationDestination
//) {
//    LazyRow(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(bottom = 2.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        items(items = allScreens) { formation ->
//            FormationCards(
//                image = formation.formationImage,
//                onSelected = { onTabSelected(formation) },
//                selected = currentScreen == formation
//            )
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FormationCards(
//    image: Int,
//    onSelected: () -> Unit,
//    selected: Boolean
//) {
//    Card(
//        onClick = onSelected,
//        modifier = Modifier
//            .padding(3.dp),
//        shape = RoundedCornerShape(8.dp),
//        border = BorderStroke(4.dp, Color.LightGray)
//    ) {
//        Image(
//            painter = painterResource(id = image),
//            contentDescription = null,
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier
//                .height(300.dp)
//                .width(200.dp)
//        )
//    }
//}

@Composable
fun FormationSheet(
    modifier: Modifier = Modifier,
    onCLickTask: (manageFormation: ManageFormation)->Unit
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(5.dp)
    ){
        items(formationItemList){
            FormationCard(
                formationItem = it,
                onClick = { onCLickTask(it.manageFormation) }
            )
        }
    }
}

@Composable
fun FormationCard(
    formationItem: FormationItem,
    onClick: ()->Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(5.dp))
            .clickable(
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = formationItem.formation,fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TabPreview(){
}