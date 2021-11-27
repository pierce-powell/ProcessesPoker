package com.example.yeehawholdem.LobbyCode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yeehawholdem.Screen
import com.example.yeehawholdem.quitInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.launch

import kotlinx.coroutines.tasks.await

data class lobbyInfo(var playerName: String? = "", var numPlayers: Long? = 0, var playerBalance: Long? = 0, var isHost: String? = "", var isInProgress: Boolean? = false)

@Composable
fun Joinlobby(navController : NavController) {
    var lobby1Players by remember {
        mutableStateOf(0)
    }

    var lobby2Players by remember {
        mutableStateOf(0)
    }

    var lobby3Players by remember {
        mutableStateOf(0)
    }

    var lobby4Players by remember {
        mutableStateOf(0)
    }

    var lobby5Players by remember {
        mutableStateOf(0)
    }


    var expanded by remember { mutableStateOf(false) }
    var selectedLobby by remember { mutableStateOf("Lobby1") }
    var selectedLobbyPlayers by remember { mutableStateOf(0) }

    //All the values we need to put in the databases
    var playerUsername by remember { mutableStateOf("") }
    var playerBalance by remember { mutableStateOf(0) }
    var numberOfPlayers by remember { mutableStateOf(0) }
    var currentHost by remember { mutableStateOf("") }
    // we use 0 for a game thats not active and 1 for one that is
    var isGameInProgress by remember { mutableStateOf(false) }

    //state to show the user they're being added to the wait room
    var showWaitWarning by remember { mutableStateOf(false) }
    var showThatRoomIsFull by remember { mutableStateOf(false) }



    var lobbyInfoState by remember {
        mutableStateOf(lobbyInfo())
    }

    var lobbyDataHandler = JoinLobbyDataHandler()
    lobbyDataHandler.getTheLobbyInfo(lobbyInfoState, selectedLobby) // This selected Lobby might be jank



    // Our authentication database
    val auth: FirebaseAuth = Firebase.auth


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter)
    {
        //Inner box for our pretty picture
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background), contentAlignment = Alignment.TopCenter
        )
        {
            //Image(painter = painterResource(id = R.drawable.pain), contentDescription = null)
        }

        //Outer column that will contain all our text fields
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.90f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(color = MaterialTheme.colors.background)
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            //Remind the user of the screen
            Text(
                text = "Join a lobby from the list below",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = MaterialTheme.typography.h5.fontWeight,
                color = MaterialTheme.colors.primary
            )


            //Lobby ID Text Field
            Spacer(modifier = Modifier.padding(20.dp))

            //Add the drop down list hither
            val coroutineScope = rememberCoroutineScope()
            //val lobby1Players

            //This won't actually get any values because its assigned outside of the scope, but lets instantiate it here
            var hasMapOfLobbys: ArrayList<lobbyForList>

            //Launch our co-routine scope so that we can change all the mutable states
                coroutineScope.launch {
                    // Call the function that we defined earlier
                    hasMapOfLobbys = getLobbys()
                    //Set up all of the mutable states, we can also use these bois outside the coroutine scope
                    lobby1Players = hasMapOfLobbys[0].numPlayers!!.toInt()
                    lobby2Players = hasMapOfLobbys[1].numPlayers!!.toInt()
                    lobby3Players = hasMapOfLobbys[2].numPlayers!!.toInt()
                    lobby4Players = hasMapOfLobbys[3].numPlayers!!.toInt()
                    lobby5Players = hasMapOfLobbys[4].numPlayers!!.toInt()
                }



            //Make the anchor point for our drop down menu
            Row(Modifier.clickable {
                //When you click it, it expands or contracts
                expanded = !expanded
            }) {
                //Surface the text for darkmode users
                Surface {
                    //Print the users current lobby selections, defaults to lobby 1
                    Text(text = "Current Lobby Selected: $selectedLobby")
                }
                //Surface the Icon for darkmode users
                Surface {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Drop Down Error for Lobby Selection"
                    )
                }
                    //Make our drop down menu for all of the lobbys
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(onClick = {
                            //If they click it, change the users selected var
                            selectedLobby = "Lobby1"
                            //And then contract the menu
                            expanded = false
                        })
                            {
                                //Surface the rows text for darkmode users
                                Surface {
                                    Text(text = "Lobby1: Number of players: $lobby1Players")
                                }
                            }
                        DropdownMenuItem(onClick = {
                            selectedLobby = "Lobby2"
                            expanded = false
                        })
                        {
                            Surface {
                                Text(text = "Lobby2: Number of players: $lobby2Players")
                            }
                        }
                        DropdownMenuItem(onClick = {
                            selectedLobby = "Lobby3"
                            expanded = false
                        })
                        {
                            Surface {
                                Text(text = "Lobby 3: Number of players: $lobby3Players")
                            }
                        }
                        DropdownMenuItem(onClick = {
                            selectedLobby = "Lobby4"
                            expanded = false
                        })
                        {
                            Surface {
                                Text(text = "Lobby 4: Number of players: $lobby4Players")
                            }
                        }
                        DropdownMenuItem(onClick = {
                            selectedLobby = "Lobby5"
                            expanded = false
                        })
                        {
                            Surface {
                                Text(text = "Lobby 5: Number of players: $lobby5Players")
                            }
                        }
                    }
                }


            Spacer(modifier = Modifier.padding(150.dp))



            //Add some space before the sign in button
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    //The real time database
                    val database = Firebase.database

                    //Get the user ID
                    val userUid = auth.currentUser?.uid

                    //Reference the lobby that the user selected
                    val lobbyRef = database.getReference(selectedLobby)

                    selectedLobbyPlayers = lobbyInfoState.numPlayers!!.toInt()


                    //TODO: Cap the lobby
                    if(selectedLobbyPlayers > 6)
                    {
                        //Trigger a dialog telling them to join another lobby
                        showThatRoomIsFull = true
                    }
                    //Theres room in the lobby, so have them join it
                    else {

                        //TODO: Add a users cards reference to -1
                        //TODO: Add bet
                        //The users hand


                        //Lets check if we need this user to step in as the host
                        if (lobbyInfoState.isHost.toString().isEmpty()) {
                            //then we need to make this user the host
                            lobbyRef.child("Host").setValue(userUid.toString())
                            //Add the selected lobby to the host's Uid
                        }

                        //Now lets update the player count
                        lobbyRef.child("NumPlayers").setValue(selectedLobbyPlayers + 1)

                        //We also need to update the Lobbys section for the realtime player counts
                        val lobbysSectionRef = database.getReference("Lobbys").child(selectedLobby)
                        lobbysSectionRef.setValue(selectedLobbyPlayers + 1)

                        //Now we need to check if the game is in progress,
                        //if it isn't, just join the game
                        if (!(lobbyInfoState.isInProgress!!)) {
                            //Now lets add the player so we can keep track of them
                            lobbyRef.child("ActiveUsers").child(userUid.toString())
                                .child("username").setValue(lobbyInfoState.playerName)
                            lobbyRef.child("ActiveUsers").child(userUid.toString()).child("balance")
                                .setValue(lobbyInfoState.playerBalance)
                            lobbyRef.child("ActiveUsers").child(userUid.toString()).child("UserBet")
                                .setValue(0)

                            //Give the players some cards when they join in as well
                            lobbyRef.child("ActiveUsers").child(userUid.toString()).child("Cards").child("Card1")
                                .setValue(-1)
                            lobbyRef.child("ActiveUsers").child(userUid.toString()).child("Cards").child("Card2")
                                .setValue(-1)

                            //Give them a nice flag to tell if they've folded
                            lobbyRef.child("ActiveUsers").child(userUid.toString()).child("IsStillIn").setValue(true)
                            lobbyRef.child("ActiveUsers").child(userUid.toString()).child("IsStillIn").setValue(true)
                            lobbyRef.child("ActiveUsers").child(userUid.toString()).child("DidYaWin").setValue(false)

                            // lobbyRef.child("ActiveUsers").child(userUid.toString()).child("TurnNumber").setValue(numberOfPlayers)

                            database.getReference(userUid.toString()).child("InLobby")
                                .setValue(selectedLobby)

                            //Now we need to navigate to the next screen
                            navController.navigate(route = Screen.GameBoardOnline.route)
                        } else {
                            //Add them to the waiting room so we don't need to worry about integrating them mid game
                            lobbyRef.child("WaitingRoom").child(userUid.toString())
                                .child("username").setValue(lobbyInfoState.playerName)
                            lobbyRef.child("WaitingRoom").child(userUid.toString()).child("balance")
                                .setValue(lobbyInfoState.playerBalance)

                            //Give the players some cards when they join in as well
                            lobbyRef.child("WaitingRoom").child(userUid.toString()).child("Cards").child("Card1")
                                .setValue(-1)
                            lobbyRef.child("WaitingRoom").child(userUid.toString()).child("Cards").child("Card2")
                                .setValue(-1)

                            //Now give them the is still in flag to check if they folded

                            lobbyRef.child("WaitingRoom").child(userUid.toString()).child("IsStillIn").setValue(1)
                            lobbyRef.child("WaitingRoom").child(userUid.toString()).child("IsStillIn").setValue(1)

                            database.getReference(userUid.toString()).child("InLobby")
                                .setValue(selectedLobby)

                            //Now we need to navigate to the next screen
                            showWaitWarning = true
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(50.dp)
            )
            {
                //Button Name
                Text(text = "Join Lobby", fontSize = MaterialTheme.typography.h5.fontSize)
            }

        }

        Spacer(modifier = Modifier.height(150.dp))
    }


    if (showWaitWarning == true) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "The lobby your joining is currently mid hand. Please sit tight," +
                        " and we'll add you into the next hand!")
            },
            confirmButton = {
                Button(onClick = {
                    showWaitWarning = false
                    navController.navigate(route = Screen.GameBoardOnline.route)
                })
                {
                    Text(text = "Ok")
                }
            }
        )
    }


    if (showThatRoomIsFull == true) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "The lobby you are trying to join is currently at its maximum capacity! Please join another lobby.")
            },
            confirmButton = {
                Button(onClick = {
                    showThatRoomIsFull = false
                })
                {
                    Text(text = "Ok")
                }
            }
        )
    }

}




//A little helper for showing the lobbys
data class lobbyForList(var lobbyName: String? = "", var numPlayers: Long? = 0)

//A little helper for getting all the relevant lobby information to update
//username to show their preffered name
//numPlayers to update the lobby players
//playerBalance to use in game for bets etc.
//isHost to determine if theres already a host machine




// kotlin coroutine for getting the lobby snapshot and passing it to our other worker
suspend fun getLobby(): DataSnapshot? {
    //Instantiate the database
    val database = Firebase.database
    //get the reference to the parent in the database we want to use
    val lobby1Ref = database.getReference("Lobbys")
    //wait for the database to get all the information stored under that reference
    val snapshot = lobby1Ref.get().await()
    //return the snapshot of data for the other function to use
    return snapshot
}
//comment

//Another coroutine that stores all the lobby info to array Lists
suspend fun getLobbys() : ArrayList<lobbyForList> {
    //Since we have 5 lobbys, lets make an arraylist to store them all
    val allLobbys = ArrayList<lobbyForList>()
    //Call the previous helper that we made and then make a reference for each of its children (Lobby1 - 5)
    val lobbys = getLobby()?.children

    //Go through each of the parents kiddos
    lobbys?.forEach {
        //Assign the player count to the current value
        val curLobbyPlayers = it.value
        //Instantiate the data object we're using
        val curLobby = lobbyForList()

        //Go through each memember of the data object
        with(curLobby) {
            //the name of the lobby is the key
                //Note: we never actuall use this name, as we already know all the names of the lobbys,
                    //This was just to illustrate the example of how its possible
            lobbyName = it.key
            //The players are it.value itself as we have already made note of, so lets cast it
                //Note: Here we use a long instead of int, because this is how firebase stores its values
            numPlayers = curLobbyPlayers as Long?
        }
        //Append the new object to our list
        allLobbys.add(curLobby)
    }
    //Return our list
    return allLobbys
}
