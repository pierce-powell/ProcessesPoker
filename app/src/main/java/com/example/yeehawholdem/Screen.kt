package com.example.yeehawholdem

sealed class Screen(val route : String)
{
    object MainMenu: Screen(route = "MainMenu_screen")
    object Login: Screen(route = "Login_Screen")
    object CreateAccount: Screen(route = "CreateAccount_Screen")
    object LeaderBoard: Screen(route = "LeaderBoard_Screen")
}
