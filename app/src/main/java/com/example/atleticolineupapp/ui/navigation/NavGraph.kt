package com.example.atleticolineupapp.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.atleticolineupapp.ui.screens.HomeScreen

//@Composable
//fun NavGraph(
//    navController: NavHostController,
//    modifier: Modifier = Modifier
//){
//    NavHost(
//        navController = navController,
//        startDestination = switchFormation(),
//        modifier = modifier
//    ){
//        addHomeScreen(navController, this)
//        addF442Screen(navController, this)
//        addF3142Screen(navController, this)
//        addF532Screen(navController, this)
//        addF541Screen(navController, this)
//    }
//}
//
//private fun addHomeScreen(
//    navController: NavHostController,
//    navGraphBuilder: NavGraphBuilder
//){
//    navGraphBuilder.composable(route = F442.route){
//        HomeScreen(
//            navigateToFormation = {
//                navController.navigate(switchFormation())
//            }
//        )
//    }
//}
//
//fun switchFormation():String{
//    return F442.route
//}
//
//@OptIn(ExperimentalMaterialApi::class)
//private fun addF442Screen(
//    navController: NavHostController,
//    navGraphBuilder: NavGraphBuilder
//){
//    navGraphBuilder.composable(route = F442.route){
//
//    }
//}
//
//private fun addF3142Screen(
//    navController: NavHostController,
//    navGraphBuilder: NavGraphBuilder
//){
//    navGraphBuilder.composable(route = F3142.route){
//
//    }
//}
//
//private fun addF532Screen(
//    navController: NavHostController,
//    navGraphBuilder: NavGraphBuilder
//){
//    navGraphBuilder.composable(route = F532.route){
//
//    }
//}
//
//private fun addF541Screen(
//    navController: NavHostController,
//    navGraphBuilder: NavGraphBuilder
//){
//    navGraphBuilder.composable(route = F541.route){
//
//    }
//}
//
//fun NavHostController.navigateSingleTopTo(route: String) =
//    this.navigate(route){
//        popUpTo(
//            this@navigateSingleTopTo.graph.findStartDestination().id
//        ){
//            saveState = true
//        }
//        launchSingleTop = true
//        restoreState = true
//    }