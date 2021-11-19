package com.example.yeehawholdem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.yeehawholdem.LogicGoods.Game

@Composable
fun PlayScreen(navController : NavController)
{

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
        /*  Image(
              painter = painterResource(id = R.drawable.pain),
              contentDescription = "Login Image"
          )*/
    }

    //Column to hold all the goods
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight(.6f))
    {

            //Play Offline
            Button(onClick = {
                navController.navigate(route = Screen.GameBoardOffline.route)
            },
                modifier = Modifier
                    .fillMaxWidth(BUTTON_WIDTH)
                    .height(BUTTON_HEIGHT))
            {
                // game.startGame()
                Text(text = "PLAY OFFLINE", fontSize = MaterialTheme.typography.h5.fontSize)
            }


        Spacer(modifier = Modifier.padding(SPACER_HEIGHT))

        //Leaderboard button
        Button(onClick = {
            navController.navigate(route = Screen.CreateLobby.route)
        },
            modifier = Modifier
                .fillMaxWidth(BUTTON_WIDTH)
                .height(BUTTON_HEIGHT))
        {
            Text(text = "CREATE LOBBY", fontSize = MaterialTheme.typography.h5.fontSize)
        }

        Spacer(modifier = Modifier.padding(SPACER_HEIGHT))

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


        }
}

