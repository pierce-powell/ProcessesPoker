package com.example.yeehawholdem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainMenuScreen(
    navController : NavController
) {
Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
){


        //Make a box with text that allows are users to click it and go to the
        // LOGIN
        Box(modifier = Modifier
            .background(color = MaterialTheme.colors.background))
        {
            Text(
                modifier = Modifier.clickable { navController.navigate(route = Screen.Login.route)
                },
                text = "LOGIN",
                color = MaterialTheme.colors.primary,
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
        )
    }

        //Make a box with text that allows are users to click it and go to the
        // LEADERBOARD
        Box(modifier = Modifier
            .background(color = MaterialTheme.colors.background))
        {
            Text(
                modifier = Modifier.clickable { navController.navigate(route = Screen.Login.route)
                },
                text = "LEADERBOARD",
                color = MaterialTheme.colors.primary,
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

        //Make a box with text that allows are users to click it and go to the
        // GAME SCREEN
        Box(modifier = Modifier
            .background(color = MaterialTheme.colors.background))
        {
            Text(
                modifier = Modifier.clickable { navController.navigate(route = Screen.Login.route)
                },
                text = "PLAY",
                color = MaterialTheme.colors.primary,
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
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