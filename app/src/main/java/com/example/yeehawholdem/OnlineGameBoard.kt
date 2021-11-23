package com.example.yeehawholdem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yeehawholdem.LogicGoods.Communications
import com.example.yeehawholdem.LogicGoods.GameState

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
fun GameBoardOnline(navController : NavController)
{
    var communcations = Communications()
    var list by remember { mutableStateOf(mutableListOf<Long>()) }
    var size by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    size = list.size

    Box{
        Surface() {
            Text("Online Screen")
            Button(
                onClick = {
                    communcations.addEventListener("Lobby1", list)
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(45.dp)
            )
            {
                Text(text = "Test something", fontSize = MaterialTheme.typography.h5.fontSize)
            }

        }

        if (showDialog == true) {

            AlertDialog(onDismissRequest = {},
                title = {
                    Text(list.getOrNull(0).toString())
                },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                    })
                    {
                        Text(text = "Ok")
                    }
                }
            )
        }
    }
}






