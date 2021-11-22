package com.example.yeehawholdem

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yeehawholdem.GameBoardGoods.GameBoardOfflineScreen
//import com.example.yeehawholdem.GameBoardGoods.GameBoardScreen
import com.example.yeehawholdem.LeaderBoardGoods.LeaderBoardScreen
import com.example.yeehawholdem.LogicGoods.Game
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
        composable(route = Screen.Play.route)
        {
            PlayScreen(navController = navController)
        }
        composable(route = Screen.CreateLobby.route)
        {
            CreateLobbyScreen(navController = navController)
        }
        composable(route = Screen.JoinLobby.route)
        {
            Joinlobby(navController = navController)
        }
        composable(route = Screen.GameBoardOnline.route)
        {
            GameBoardOnline(navController = navController)
        }

    }
}