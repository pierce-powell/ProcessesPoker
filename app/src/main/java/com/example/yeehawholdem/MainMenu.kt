package com.example.yeehawholdem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

//TODO Need to keep track of whether or not the user is online and logged in.
//TODO implement sign out functionality
//TODO Issue warning about not being saved when offline or not logged in
//TODO Determine whether to hide buttons that they can't do or allow them to remain and issue prompts
//TODO Remove "AS GUEST" from online !logged in favor of issuing a prompt?


@Composable
fun MainMenuScreen(
    navController : NavController
) {
    //Used to determine which buttons to show
    var isOnline by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    )
    {

        //A box to put our pretty picture in
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.TopCenter
        )
        {
            Image(
                painter = painterResource(id = R.drawable.pain),
                contentDescription = "Login Image"
            )
        }

        //Column to hold all the goods
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight(.6f))
        {

            //Login button
            if (!isLoggedIn)
            Button(onClick = {
                navController.navigate(route = Screen.Login.route)
            },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(50.dp))
            {
                Text(text = "LOGIN", fontSize = MaterialTheme.typography.h5.fontSize)
            }
            else
            {
                Button(onClick = {
                    //TODO implement sign out functionality
                },
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(50.dp))
                {
                    Text(text = "SIGN OUT", fontSize = MaterialTheme.typography.h5.fontSize)
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))

            //Leaderboard button
            Button(onClick = {
                navController.navigate(route = Screen.Login.route)
            },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(50.dp))
            {
                Text(text = "LEADERBOARD", fontSize = MaterialTheme.typography.h5.fontSize)
            }

            Spacer(modifier = Modifier.padding(20.dp))

            //User can only play online if they have an internet connection
            if(isOnline && isLoggedIn) {
                Button(
                    onClick = {
                        navController.navigate(route = Screen.Login.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(50.dp)
                )
                {
                    Text(text = "PLAY ONLINE", fontSize = MaterialTheme.typography.h5.fontSize)
                }
            }

            //Play online as guest button for those who have internet and no account
            else if (isOnline && !isLoggedIn)
            {
                Button(
                    onClick = {
                        //TODO issue warning prompt that their progress will not be saved
                        navController.navigate(route = Screen.Login.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(50.dp)
                )
                {
                    Text(text = "PLAY ONLINE AS GUEST", fontSize = MaterialTheme.typography.h5.fontSize)
                }
            }

            // No internet connection means that they aren't logged in
            else if(!isOnline)
            {
                Button(
                    onClick = {
                        //TODO issue warning prompt that their progress will not be saved
                        navController.navigate(route = Screen.Login.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(50.dp)
                )
                {
                    Text(text = "PLAY OFFLINE", fontSize = MaterialTheme.typography.h5.fontSize)
                }
            }
        }



    }
}


@Composable
@Preview
fun HomeScreenPreview()
{
    MainMenuScreen(
        navController = rememberNavController()
    )
}