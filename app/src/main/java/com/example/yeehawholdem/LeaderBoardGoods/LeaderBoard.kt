package com.example.yeehawholdem.LeaderBoardGoods

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
    myLazyList.swapList(mapOfPlayers.toList().sortedBy { (k, v) -> v }.toMap().values.map { it })

    //Box to store everything in
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter)
    {
        Box(modifier = Modifier
            .fillMaxHeight(.75f)
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter)
        {
            LazyColumn(
                contentPadding = PaddingValues(all = 6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            )
            {
                itemsIndexed(items = myLazyList) { index, player ->
                    CustomLeaderBoardRow(curPlayer = player, index = index)
                }
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        )
        {
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


            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
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
            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.9f)
                    .background(Color.Transparent),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(//lower bet button
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth(.23f)
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
        Spacer(modifier = Modifier.padding(30.dp))

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
