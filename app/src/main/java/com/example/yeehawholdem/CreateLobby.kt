package com.example.yeehawholdem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun CreateLobbyScreen(navController: NavController)
{
    val database = Firebase.database
    val myRef = database.getReference("Lobby")

    //This will be the lobby ID, I.E. we will use this to reference the database calls
    val lobbyName = remember { mutableStateOf("") }

    //This is to control the pop up if the ID is invalid
    var showDialog by remember { mutableStateOf(false) }

    //This displays the error messaage to the user in the dialog afformentioned
    var errorResults by remember { mutableStateOf("") }

    //Outer box to throw everything in
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
            Text(text = "Create a Lobby to Play Online",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = MaterialTheme.typography.h5.fontWeight,
                color = MaterialTheme.colors.primary)


            //Lobby ID Text Field
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                Surface() {
                    OutlinedTextField(
                        value = lobbyName.value, onValueChange = { lobbyName.value = it },
                        label = { Text(text = "Lobby Name") },
                        placeholder = { Text(text = "Lobby Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(.70f)
                    )
                }
            }

            //Add some space before the sign in button
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {
                errorResults = checkID(lobbyName.value)

                // This triggers a pop up if something is wrong
                if ((errorResults.isNotEmpty())) {
                    showDialog = true
                }
                // Otherwisem the information that was entered was correct
                else
                {
                   val newRef = database.getReference("Lobby1")
                    newRef.child("Bet").setValue(10)

                }
            },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(50.dp))
            {
                Text(text = "Create Lobby", fontSize = MaterialTheme.typography.h5.fontSize)
            }

        }


        Spacer(modifier = Modifier.height(150.dp))
    }

}

fun checkID(id : String) : String
{
    //Database call to check if ID exists

    //Otherwise return
    return ""
}