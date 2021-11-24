package com.example.yeehawholdem

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeehawholdem.LogicGoods.Game
import com.example.yeehawholdem.LogicGoods.GameValues
import com.example.yeehawholdem.OnlineGameGoods.Communications
import com.example.yeehawholdem.OnlineGameGoods.QuitGameDataHandler
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

/*
Heyo peeps, heres some important notes to consider with how the lobby works, I tried to make it
easier on the logic end to manage a host.

At the start of a game, the lobby has a variable called IsGameInProgress(0 means false and 1 means True)
if the variable is 1, players will be fed into a waiting room. Therefore, upon starting the game,
you should pull all players out of the waiting room and add them to the ActiveUsers tag.

Furthermore, the host is saved under the LobbyName, and if a user becomes the host, under their unique ID
tag along with their username and balance, the lobby that they are the host of will be added.
This will make for an easy reference tag that the host can use to make updates this will be stored under
HostOfLobby


Personal Aside for what needs to happen when a user quits the game:
Check if the current user is a host, if they are transfer host status when the game ends
If the user is the host, don't update the host status until the IsGameInProgress is 0
When it is, remove them from the lobby (Active or Waitroom), reduce the number of players by 1
If they were the host, delete the HostOfLobby variable under their Uid
 */

@Composable
fun GameBoardOnline(navController : NavController) {
    var dummy by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = dummy) {
        delay(1000)
        dummy = !dummy
    }

    val ls = "Lobby1"

    var communications = Communications()
    var game by remember { mutableStateOf(GameValues()) }
    var list by remember { mutableStateOf(mutableListOf<Long>(-1)) }
    var showDialog by remember { mutableStateOf(false) }

    var gameClass by remember { mutableStateOf(Game(game, communications, ls)) }

    //communcations.addEventListener("Lobby1", list)
    communications.setupLobbyEventListener(game, ls)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter)
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        )
        {
            AddText(text = "Bet: ${game.betToString()}")
            AddText(text = "Pot: ${game.potToString()}")
            AddText(text = "Card1: ${game.card1ToString()}")
            AddText(text = "Card2: ${game.card2ToString()}")
            AddText(text = "Card3: ${game.card3ToString()}")
            AddText(text = "Card4: ${game.card4ToString()}")
            AddText(text = "Card5: ${game.card5ToString()}")

            Button(
                onClick = { gameClass.setCardsRound1() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) { Text("Round1") }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { gameClass.setCardsRound2() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) { Text("Round2") }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { gameClass.setCardsRound3() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) { Text("Round3") }
        }


        Spacer(modifier = Modifier.height(10.dp))

        
        addQuitButton(navController = navController)
    }
}

    

@Composable
private fun AddText(text : String) {
    // Wrap in a surface so it can pick up on light-mode vs dark
    Surface {
        //Row for the river text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp), // However tall we need for a card
            horizontalArrangement = Arrangement.Center
        ) {
            //Box to put the text in
            Box(contentAlignment = Alignment.Center) {
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    text = text
                )
            }
        }

    }
}


data class quitInfo(var lobby : String? = "", var playerID: String? = "", var didPlayerQuit: Long? = 0)

@Composable
fun addQuitButton(navController : NavController)
{
    var quitData by remember {
        mutableStateOf(quitInfo())
    }

    //Tells all the users that they someone quit and the game is ending
    var trigerQuitDialog by remember {
        mutableStateOf(false)
    }

    //Tells the user that something went wrong with the quit and they should try again
    var triggerFailedQuitDialog by remember {
        mutableStateOf(false)
    }



    var quitDataHandler = QuitGameDataHandler()

    quitDataHandler.getTheLobbyName(quitData)

    if(quitData.didPlayerQuit?.toInt() == 1) {
        trigerQuitDialog = true
    }


    if(trigerQuitDialog)
    {
        LaunchedEffect(key1 = trigerQuitDialog) {
            delay(5000)
            Firebase.database.getReference(quitData.lobby.toString()).child("DidPlayerQuit").setValue(0)
            navController.navigate(Screen.MainMenu.route)
        }
    }


    Button(//lower bet button
        onClick = {
            if(quitData.lobby.isNullOrEmpty())
            {
                //We don't have the data yet, so issue a dialog telling them to retry

            }
            else
            {
                //We have the lobby name to make a refernece, so lets reference it and update all the stuff we need
                //Lets get the realtime database referenced at the lobby
                val lobbyRef = Firebase.database.getReference(quitData.lobby.toString())
                //Now lets start making changes.
                //Firstly, we want to issue the flag to all users that someone has quit the game
                //and interfered with the lobby progress
                lobbyRef.child("DidPlayerQuit").setValue(1)

                //Now with that set, all the players should first receive the dialog that the game has ended
                //So lets just start making everything the defaults
                lobbyRef.child("Bet").setValue(0)

                //Now the pot
                lobbyRef.child("Pot").setValue(0)

                //Change the number of players in the lobby back to 0
                lobbyRef.child("NumPlayers").setValue(0)

                //Remove the waiting room
                lobbyRef.child("WaitingRoom").removeValue()

                //Now lets change the Active Players
                lobbyRef.child("CurrentActivePlayer").setValue(0)

                //Now the Host
                lobbyRef.child("Host").setValue("")

                //Change the lobby status
                lobbyRef.child("IsGameInProgress").setValue(0)

                //Reset all the river values
                lobbyRef.child("River").child("Card1").setValue(-1)
                lobbyRef.child("River").child("Card2").setValue(-1)
                lobbyRef.child("River").child("Card3").setValue(-1)
                lobbyRef.child("River").child("Card4").setValue(-1)
                lobbyRef.child("River").child("Card5").setValue(-1)


                //Now the active users
                lobbyRef.child("ActiveUsers").removeValue()

                //Finally, lets remove the lobby reference from the player, this might goof
                val playerRef = Firebase.database.getReference(quitData.playerID.toString())



            }

        },
        modifier = Modifier
            .fillMaxWidth(.4f)
            .height(40.dp)
    )
    {
        Text(text = "Quit Game", fontSize = MaterialTheme.typography.h5.fontSize)
    }


    //Someone quit the game!
    if (trigerQuitDialog == true) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "Oh No! Someone Quit the Game!")
            },
            text = {
                Text(text = "Returning to the MainMenu in 5 seconds!")
            },
            confirmButton = {}
        )
    }


    //If we don't have the lobby name by the time they try to quit
    if (triggerFailedQuitDialog == true) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "There was an error quiting, please try again!")
            },
            confirmButton = {
                Button(onClick = {
                    triggerFailedQuitDialog = false
                })
                {
                    Text(text = "Ok")
                }
            }
        )
    }
}



@Composable
@Preview(showBackground = true)
fun GamePreview()
{
    GameBoardOnline(navController = rememberNavController())
}


