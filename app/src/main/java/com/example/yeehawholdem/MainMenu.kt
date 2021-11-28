package com.example.yeehawholdem

import android.app.PendingIntent.getActivity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
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
fun MainMenuScreen(navController: NavController, internetConnection: Boolean)
{
    var showTutorial by remember { mutableStateOf(false) }
    var showHands by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }
    var noInternetWarning by remember { mutableStateOf(false)}


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
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()))
        {
            //Tutorial button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            )
            {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .background(MaterialTheme.colors.background)
                        .padding(1.dp),
                    contentAlignment = Alignment.Center,
                )
                {
                    Button(
                        onClick = {
                            showTutorial = true
                        }, modifier = Modifier
                            .fillMaxWidth(.15f)
                            .height(BUTTON_HEIGHT))
                    {
                        Image(
                            modifier = Modifier.graphicsLayer(
                                scaleX = 2f,
                                scaleY = 2f,
                            ),
                            painter = painterResource(id = R.drawable.information),
                            contentDescription = "Tutorial pop up button"
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
            }

            //YeeHaw Hold 'Em Logo
            val scale = remember { mutableStateOf(2f)}

            //determine which drawable to use
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colors.background)
                    .width((LocalConfiguration.current.screenWidthDp.dp))
                    .height((LocalConfiguration.current.screenWidthDp.dp) / 2)
                    .padding(1.dp)
                    .clip(RectangleShape),
                contentAlignment = Alignment.Center,
            )
            {
                // The Pretty Picture
                    Image(modifier = Modifier.height(LocalConfiguration.current.screenWidthDp.dp / 2)
                        .width(LocalConfiguration.current.screenWidthDp.dp),
                        painter = painterResource(id = R.drawable.yeehawlogo),
                        contentDescription = "Logo"
                    )
            }
            Spacer(modifier = Modifier.padding(10.dp))

            //Login button
            if (!isLoggedIn)
            Button(onClick = {
                if(!internetConnection)
                    noInternetWarning = true
                else
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
    // pop up message to give a small tutorial about the app
    if (showTutorial) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "Tutorial")
            },
            text = {
                Text(text = "Click LOGIN to play online with friends\n" +
                        "Click LEADERBOARD to see the top players\n" +
                        "Click PLAY to get started!\n\n" +
                        "Poker Rules: Texas Hold 'Em\n" +
                        " - You will be dealt 2 cards that only you can see\n" +
                        " - The River is 5 cards that will be shared with all players and will be revealed as the game goes on\n" +
                        " - When first dealt your cards there will be a round of betting where every player picks one of 2 options\n" +
                        " 1. Bet: Pick an amount to wager on the cards you have, all bets are added to the pot.\n" +
                        " 2. Fold: Forfeit your cards and drop out of this game\n" +
                        " - First 3 cards of The River will be revealed and another round of betting occurs\n" +
                        " - 4th card is revealed and another round of betting occurs\n" +
                        " - 5th card is revealed and the last round of betting occurs\n" +
                        " - Last, a winner is picked based on who has the best hand\n" +
                        " - All points wagered in the pot are awarded to the winner and a new game starts\n\n\n\n\n\n",
                modifier = Modifier.verticalScroll(rememberScrollState()))

            },
            confirmButton = {
                Button(onClick = {
                    showTutorial = false
                    showHands = true
                })
                {
                    Text(text = "Cool")
                }
            }
        )
    }
    // pop up message to show the best hands
    if (showHands) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "List of Poker Hands(Higher hand wins)")
            },
            text = {
                Text(" - Royal Flush: A, K, Q, J, 10 all same suit\n" +
                        " - Straight Flush: 5 consecutive #'s all same suit\n" +
                        " - 4 of a kind: All 4 copies of 1 card\n" +
                        " - Full House: 3 of a kind plus a pair\n" +
                        " - Straight: 5 consecutive #'s\n" +
                        " - Flush: All cards same suit\n" +
                        " - 3 of a kind: 3 copies of 1 card\n" +
                        " - 2 pairs: 2 copies of 1 card plus 2 copies of another card\n" +
                        " - Pair: 2 copies of 1 card\n" +
                        "In the event of a tie the pot is split between the winners")
            },
            confirmButton = {
                Button(onClick = {
                    showHands = false
                })
                {
                    Text(text = "Cool")
                }
            }
        )
    }

    if (noInternetWarning) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "ATTENTION")
            },
            text = {
                Text("This functionality requires a stable internet connection!" +
                        " Please establish an internet connection and restart the app! ")
            },
            confirmButton = {
                Button(onClick = {
                    noInternetWarning = false
                })
                {
                    Text(text = "Gotcha")
                }
            }
        )
    }
}







@Composable
@Preview
fun HomeScreenPreview()
{

}