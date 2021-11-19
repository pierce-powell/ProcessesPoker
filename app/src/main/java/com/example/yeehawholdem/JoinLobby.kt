package com.example.yeehawholdem

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


@Composable
fun Joinlobby(navController : NavController) {
    //Set up the values to give user and idea of the amount of players in each lobby
    var lobby1Players : Int
    var lobby2Players : Int
    var lobby3Players : Int
    var lobby4Players : Int
    var lobby5Players : Int

    //Get the real time values of the players in the lobby


   /* var lobby1 = lobbyForList("Lobby1", lobby1Players)
    var lobby2 = lobbyForList("Lobby2", lobby2Players)
    var lobby3 = lobbyForList("Lobby3", lobby3Players)
    var lobby4 = lobbyForList("Lobby4", lobby4Players)
    var lobby5 = lobbyForList("Lobby5", lobby5Players)*/

    //val lobbyList = listOf(lobby1, lobby2, lobby3, lobby4, lobby5)

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
            //Text(text = "Lobby1: players: $lobby1Players")


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
data class lobbyForList(val lobbyName: String, val numPlayers: Int)


fun readLeaderBoardData() : String {
    var result : String

    var database = FirebaseDatabase.getInstance().getReference("Lobbys")
    database.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            result = snapshot.child("Lobby1").value.toString()
            //lobby2Players = snapshot.child("Lobby2").value.toString().toInt()
           // lobby3Players = snapshot.child("Lobby3").value.toString().toInt()
           // lobby4Players = snapshot.child("Lobby4").value.toString().toInt()
           // lobby5Players = snapshot.child("Lobby5").value.toString().toInt()
        }

        //It will always have a value, so this should never get called
        override fun onCancelled(error: DatabaseError) {
          //  lobby1Players = -500
        }
    })

    return ""
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