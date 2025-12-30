package com.github.thesambex.dododonkey.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.thesambex.dododonkey.ui.home.HomeScreen

@Composable
fun MainNavigation(navHostController: NavHostController = rememberNavController()) {

    NavHost(navController = navHostController, startDestination = Route.Home) {
        composable<Route.Home> { HomeScreen(navHostController) }
    }

}