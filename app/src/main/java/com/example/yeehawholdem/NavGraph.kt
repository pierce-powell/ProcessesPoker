package com.example.yeehawholdem

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yeehawholdem.GameBoardGoods.GameBoardOfflineScreen
//import com.example.yeehawholdem.GameBoardGoods.GameBoardScreen
import com.example.yeehawholdem.LeaderBoardGoods.LeaderBoardScreen
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
        composable(route = Screen.LeaderBoard.route)
        {
            LeaderBoardScreen(navController = navController)
        }
        composable(route = Screen.GameBoardOffline.route)
        {
            //TODO Play OFFLINE screen
            GameBoardOfflineScreen(navController = navController)
        }
    }
}