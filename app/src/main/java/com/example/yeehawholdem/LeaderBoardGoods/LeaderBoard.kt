package com.example.yeehawholdem.LeaderBoardGoods

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.yeehawholdem.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@Composable
fun LeaderBoardScreen(navController : NavController)
{
    //The data for our leaderboards
    var mapOfPlayers by remember { mutableStateOf(mutableMapOf<String, LeaderBoardPlayer>()) }

    //We use this to update the list and force recompostion
    val myLazyList = remember { mutableStateListOf<LeaderBoardPlayer>() }

    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    //This is our flag so that only signed in users can jump to their location
    var isLoggedIn by remember { mutableStateOf(false) }

    //Lets get the players id for the jump button
    var curPlayerID by remember { mutableStateOf("") }

    //lets first make sure they're logged in before we start making any database calls with their ID
    //Now that we know they're logged in, lets save the player uid



    var curPlayerIndex by remember { mutableStateOf(0)}

    //Instantiate our data manager
    var leaderBoardManager = LeaderBoardDataManager()

    //Call the event listener
    leaderBoardManager.usersEventListener(mapOfPlayers)

    //manual trigger recompostions every second
    var dummy by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = dummy) {
        delay(1000)
        dummy =! dummy
    }

    //commenty
    //Get the list values intially
    myLazyList.swapList(mapOfPlayers.toList().sortedBy { (k, v) -> v }.toMap().values.map { it })

    //Box to store everything in
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter)
    {
        Box(
            modifier = Modifier
                .fillMaxHeight(.75f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        )
        {
            LazyColumn(
                contentPadding = PaddingValues(all = 6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                state = listState
            )
            {
                itemsIndexed(items = myLazyList) { index, player ->
                    if (player.playerUid == curPlayerID) {
                        curPlayerIndex = index
                        CustomLeaderBoardRow(curPlayer = player, index = index, true)
                    }
                    else
                        CustomLeaderBoardRow(curPlayer = player, index = index, false)
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        )
        {
            Row() {
                Button(//lower bet button
                    onClick = {
                        navController.navigate(Screen.MainMenu.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(.23f)
                        .height(44.dp)
                )
                {
                    Text(text = "X", fontSize = MaterialTheme.typography.h5.fontSize)
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Button(//lower bet button
                    onClick = {
                        coroutineScope.launch { listState.scrollToItem(index = curPlayerIndex) }

                    },
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(44.dp)
                )
                {
                    Text(
                        text = "My Postion",
                        fontSize = MaterialTheme.typography.h5.fontSize
                    )
                }
            }
    }


            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.84f)
                    .background(Color.Transparent),
                verticalAlignment = Alignment.Top
            ) {
                androidx.compose.material.Surface {
                    Text(
                        "           Money:   Player:",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .background(Color.Transparent)
                    )


                }
            }
            Spacer(modifier = Modifier.padding(12.dp))
        }
    }


//Swap list to update the lazyList
fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}


@Composable
@Preview
fun myPreview()
{
    LeaderBoardScreen(navController = rememberNavController())
}
