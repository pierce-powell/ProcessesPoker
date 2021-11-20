package com.example.yeehawholdem

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception


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
    var selectedIndex by remember { mutableStateOf(0) }


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

            var hasMapOfLobbys = ArrayList<lobbyForList>()

                coroutineScope.launch {
                    hasMapOfLobbys = getLobbys()
                    lobby1Players = hasMapOfLobbys[0].numPlayers!!.toInt()
                }



            Text("Lobby1: $lobby1Players")
           // Text("Lobby2: $lobby2Players")
            //Text("Lobby3: $lobby3Players")
            //Text("Lobby4: $lobby4Players")
            //Text("Lobby5: $lobby5Players")




            //Add some space before the sign in button
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {

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


// kotlin coroutine for getting lobby1
suspend fun getLobby(): DataSnapshot? {
    val database = Firebase.database
    val lobby1Ref = database.getReference("Lobbys")
    val snapshot = lobby1Ref.get().await()
    return snapshot
}

suspend fun getLobbys() : ArrayList<lobbyForList> {
    var allLobbys = ArrayList<lobbyForList>()
    val lobbys = getLobby()?.children

    lobbys?.forEach {
        var curLobbyPlayers = it.value
        var curLobby = lobbyForList()

        with(curLobby) {
            lobbyName = it.key
            numPlayers = curLobbyPlayers as Long?
        }
        allLobbys.add(curLobby)
    }

    return allLobbys
}




//Get the list of all our lobbys with the respective player count
/*fun getAllLobbys() : ArrayList<lobbyForList>
{
    val database = Firebase.database
    val lobby1Ref = database.getReference("Lobby1")
    val lobby1PlayerCount = 0

    lobby1Ref.child("NumPlayers").get()

    it.value


    var lobby1 = lobbyForList("Lobby1", )

    return null
}*/