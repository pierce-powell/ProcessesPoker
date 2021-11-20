package com.example.yeehawholdem

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.launch

import kotlinx.coroutines.tasks.await



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
                .padding(10.dp),
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
            var hasMapOfLobbys = ArrayList<lobbyForList>()

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
                Surface() {
                    //Print the users current lobby selections, defaults to lobby 1
                    Text(text = "Current Lobby Selected: $selectedLobby")
                }
                //Surface the Icon for darkmode users
                Surface() {
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
                                Surface() {
                                    Text(text = "Lobby1: Number of players: $lobby1Players")
                                }
                            }
                        DropdownMenuItem(onClick = {
                            selectedLobby = "Lobby2"
                            expanded = false
                        })
                        {
                            Surface() {
                                Text(text = "Lobby2: Number of players: $lobby2Players")
                            }
                        }
                        DropdownMenuItem(onClick = {
                            selectedLobby = "Lobby3"
                            expanded = false
                        })
                        {
                            Surface() {
                                Text(text = "Lobby 3: Number of players: $lobby3Players")
                            }
                        }
                        DropdownMenuItem(onClick = {
                            selectedLobby = "Lobby4"
                            expanded = false
                        })
                        {
                            Surface() {
                                Text(text = "Lobby 4: Number of players: $lobby4Players")
                            }
                        }
                        DropdownMenuItem(onClick = {
                            selectedLobby = "Lobby5"
                            expanded = false
                        })
                        {
                            Surface() {
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
                          //TODO: actually have the user join the lobby, this would feed their unique player ID into
                                // the repective lobby as well as updating the number of players for the lobby they joined
                                // in both the "Lobbys" reference and the respective lobby they joined
                                // This also may be where we update host information (writting some for of check
                                // to see if another user is already host, and if there isn't we set this user)
                                // furthermore, we should update navgraph to go to the multiplayer screen

                },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(50.dp)
            )
            {
                Text(text = "Join Lobby", fontSize = MaterialTheme.typography.h5.fontSize)
            }

        }


        Spacer(modifier = Modifier.height(150.dp))
    }
}




//A little helper for showing the lobbys
data class lobbyForList(var lobbyName: String? = "", var numPlayers: Long? = 0)


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


//Another coroutine that stores all the lobby info to array Lists
suspend fun getLobbys() : ArrayList<lobbyForList> {
    //Since we have 5 lobbys, lets make an arraylist to store them all
    var allLobbys = ArrayList<lobbyForList>()
    //Call the previous helper that we made and then make a reference for each of its children (Lobby1 - 5)
    val lobbys = getLobby()?.children

    //Go through each of the parents kiddos
    lobbys?.forEach {
        //Assign the player count to the current value
        var curLobbyPlayers = it.value
        //Instantiate the data object we're using
        var curLobby = lobbyForList()

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
