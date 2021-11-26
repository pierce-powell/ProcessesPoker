package com.example.yeehawholdem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//TODO Need to keep track of whether or not the user is online and logged in.
//TODO implement sign out functionality
//TODO Issue warning about not being saved when offline or not logged in
//TODO Remove "AS GUEST" from online !logged in favor of issuing a prompt


//Global variables
val BUTTON_HEIGHT = 50.dp
const val BUTTON_WIDTH = .8f
val SPACER_HEIGHT = 20.dp

@Composable
fun MainMenuScreen(
    navController : NavController,
) {

    var isLoggedIn by remember { mutableStateOf(false) }
    val auth: FirebaseAuth = Firebase.auth

    if (auth.currentUser != null)
        isLoggedIn = true

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {

        //Column to hold all the goods
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight())
        {

            //Login button
            if (!isLoggedIn)
            Button(onClick = {
                navController.navigate(route = Screen.Login.route)
            },
                modifier = Modifier
                    .fillMaxWidth(BUTTON_WIDTH)
                    .height(BUTTON_HEIGHT))
            {
                Text(text = "LOGIN", fontSize = MaterialTheme.typography.h5.fontSize)
            }
            else
            {
                Button(onClick = {
                    //TODO implement sign out functionality
                        auth.signOut()
                        isLoggedIn = false
                },
                    modifier = Modifier
                        .fillMaxWidth(BUTTON_WIDTH)
                        .height(BUTTON_HEIGHT))
                {
                    Text(text = "SIGN OUT", fontSize = MaterialTheme.typography.h5.fontSize)
                }
            }

            Spacer(modifier = Modifier.padding(SPACER_HEIGHT))

            //Leaderboard button
            Button(onClick = {
                navController.navigate(route = Screen.LeaderBoard.route)
            },
                modifier = Modifier
                    .fillMaxWidth(BUTTON_WIDTH)
                    .height(BUTTON_HEIGHT))
            {
                Text(text = "LEADERBOARD", fontSize = MaterialTheme.typography.h5.fontSize)
            }

            Spacer(modifier = Modifier.padding(SPACER_HEIGHT))

            //User can only play online if they have an internet connection
                Button(
                    onClick = {
                        navController.navigate(route = Screen.Play.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(BUTTON_WIDTH)
                        .height(BUTTON_HEIGHT)
                )
                {
                    Text(text = "PLAY", fontSize = MaterialTheme.typography.h5.fontSize)
                }
            }

        }
    

    }



@Composable
@Preview
fun HomeScreenPreview()
{
    MainMenuScreen(
        navController = rememberNavController(),
    )
}