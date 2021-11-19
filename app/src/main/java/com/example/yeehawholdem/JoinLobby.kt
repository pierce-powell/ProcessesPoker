package com.example.yeehawholdem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun Joinlobby(navController : NavController)
{

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter)
    {
        //Inner box for our pretty picture
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background), contentAlignment = Alignment.TopCenter)
        {
            //Image(painter = painterResource(id = R.drawable.pain), contentDescription = null)
        }

        //Outer column that will contain all our text fields
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.90f)
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(color = MaterialTheme.colors.background)
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            //Remind the user of the screen
            Text(text = "Join a lobby from the list below",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = MaterialTheme.typography.h5.fontWeight,
                color = MaterialTheme.colors.primary)


            //Lobby ID Text Field
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                Surface() {

                }
            }

            //Add some space before the sign in button
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {

            },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(50.dp))
            {
                Text(text = "Join Lobby", fontSize = MaterialTheme.typography.h5.fontSize)
            }

        }


        Spacer(modifier = Modifier.height(150.dp))
    }

}

//A little helper for showing the lobbys
data class lobbyForList(val lobbyName: String, val numPlayers: Task<DataSnapshot>)


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