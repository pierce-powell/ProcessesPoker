package com.example.yeehawholdem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun PlayScreen(navController: NavController, internetConnection: Boolean?)
{

    //database calls for the dynamic button
    var isLoggedIn by remember { mutableStateOf(false) }
    lateinit var auth: FirebaseAuth
    auth = Firebase.auth

    //if it returns null, the user is not signed in and can't join a lobby
    if (auth.currentUser != null)
        isLoggedIn = true


Box(
modifier = Modifier.fillMaxSize(),
contentAlignment = Alignment.Center
)
{

    //A box to put our pretty picture in
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    )
    {
        /*  Image(
              painter = painterResource(id = R.drawable.pain),
              contentDescription = "Login Image"
          )*/
    }

    //Column to hold all the goods
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight())
    {

        //Play Offline
        Button(
            onClick = {
                navController.navigate(route = Screen.GameBoardOffline.route)
            },
            modifier = Modifier
                .fillMaxWidth(BUTTON_WIDTH)
                .height(BUTTON_HEIGHT)
        )
        {
            // game.startGame()
            Text(text = "PLAY OFFLINE", fontSize = MaterialTheme.typography.h5.fontSize)
        }





        Spacer(modifier = Modifier.padding(SPACER_HEIGHT))

        if (isLoggedIn) {

            //User can only play online if they have an internet connection

            Button(
                onClick = {
                    navController.navigate(route = Screen.JoinLobby.route)
                },
                modifier = Modifier
                    .fillMaxWidth(BUTTON_WIDTH)
                    .height(BUTTON_HEIGHT)
            )
            {
                Text(text = "JOIN LOBBY", fontSize = MaterialTheme.typography.h5.fontSize)
            }
        }

        else
        {
            Button(
                onClick = {
                    navController.navigate(route = Screen.Login.route)
                },
                modifier = Modifier
                    .fillMaxWidth(BUTTON_WIDTH)
                    .height(BUTTON_HEIGHT)
            )
            {
                Text(text = "LOGIN TO JOIN", fontSize = MaterialTheme.typography.h5.fontSize)
            }
        }

        Spacer(modifier = Modifier.padding(SPACER_HEIGHT))

        Button(
            onClick = {
                navController.navigate(route = Screen.MainMenu.route)
            },
            modifier = Modifier
                .fillMaxWidth(BUTTON_WIDTH)
                .height(BUTTON_HEIGHT)
        )
        {
            // game.startGame()
            Text(text = "BACK", fontSize = MaterialTheme.typography.h5.fontSize)
        }


    }



        }
}

