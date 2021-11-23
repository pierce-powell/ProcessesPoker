package com.example.yeehawholdem.LeaderBoardGoods

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yeehawholdem.LogicGoods.FakeRepository
import com.example.yeehawholdem.R
import com.example.yeehawholdem.lobbyForList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.getValue


@Composable
//TODO: link the data to the actual repository of players
//TODO: add a back button
//TODO: discuss and implement better style (sorting by the top scores, ?DateTime/asOfScore?)

fun LeaderBoardScreen(navController : NavController)
{
    //val fakeRepository = FakeRepository()
    //val getAllData = fakeRepository.getListOfPlayers()

    //The data for our leaderboards
    var mapOfPlayers by remember { mutableStateOf(mutableMapOf<String, LeaderBoardPlayer>()) }

    //We use this to update the list and force recompostion
    val myLazyList = remember { mutableStateListOf<LeaderBoardPlayer>() }

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
    myLazyList.swapList(mapOfPlayers.values.map { it })

    //Box to store everything in
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter)
    {
        //A box to put our pretty picture in
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.TopCenter
        )
        {
           /* Image(
                painter = painterResource(id = R.drawable.pain),
                contentDescription = "Login Image"
            )*/
        }


        Box(modifier = Modifier
            .fillMaxHeight(.8f)
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter)
        {
            LazyColumn(
                contentPadding = PaddingValues(all = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            )
            {
                items(items = myLazyList) { player ->
                    CustomLeaderBoardRow(curPlayer = player)
                }
            }
        }

    }
}

//Swap list to update the lazyList
fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}

