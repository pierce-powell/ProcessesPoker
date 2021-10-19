package com.example.yeehawholdem.LeaderBoardGoods

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yeehawholdem.LogicGoods.FakeRepository
import com.example.yeehawholdem.R


@Composable
//TODO: link the data to the actual repository of players
//TODO: add a back button
//TODO: discuss and implement better style (sorting by the top scores, ?DateTime/asOfScore?)

fun LeaderBoardScreen(navController : NavController)
{
    val fakeRepository = FakeRepository()
    val getAllData = fakeRepository.getListOfPlayers()


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
            Image(
                painter = painterResource(id = R.drawable.pain),
                contentDescription = "Login Image"
            )
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
                items(items = getAllData) { player ->
                    CustomLeaderBoardRow(curPlayer = player)
                }
            }
        }

    }
}