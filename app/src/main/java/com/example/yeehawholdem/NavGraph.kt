package com.example.yeehawholdem

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yeehawholdem.Login.LoginScreen

@Composable
fun setUpNavHost(navController: NavHostController)
{
    NavHost(navController = navController, startDestination = Screen.MainMenu.route)
    {
        composable(route = Screen.MainMenu.route)
        {
            MainMenuScreen(navController = navController)
        }
        composable(route = Screen.Login.route)
        {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.CreateAccount.route)
        {
            CreateAccount(navController = navController)
        }
    }
}