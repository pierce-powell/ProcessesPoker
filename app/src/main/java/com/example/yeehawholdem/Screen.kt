package com.example.yeehawholdem

sealed class Screen(val route : String)
{
    object MainMenu: Screen(route = "MainMenu_screen")
    object Login: Screen(route = "Login_Screen")
    object CreateAccount: Screen(route = "CreateAccount_Screen")
    object LeaderBoard: Screen(route = "LeaderBoard_Screen")
    object GameBoardOffline: Screen(route = "GameBoardOffline_Screen")
    object GameBoard: Screen(route = "GameBoard_Screen")
    object PlayOnline: Screen(route = "PlayOnline_Screen")
    object CreateLobby: Screen(route = "CreateLobby_Screen")
    object JoinLobby: Screen(route = "JoinLobby_Screen")
    object Play: Screen(route = "Play_Screen")


}
