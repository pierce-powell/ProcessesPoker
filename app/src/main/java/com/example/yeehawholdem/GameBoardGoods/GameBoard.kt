package com.example.yeehawholdem.GameBoardGoods

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeehawholdem.R

//Global variables
public val CARD_HEIGHT = 109.dp

@Composable
fun GameBoardScreen(navController : NavController)
{
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
            // The Pretty Picture
            Image(
                painter = painterResource(id = R.drawable.pain),
                contentDescription = "Login Image"
            )
        }

        //Outer Column to store our two rows
        Column(modifier = Modifier
            .fillMaxHeight(.8f)
            .fillMaxWidth()
        )
        {
            addText(text = "The River")
            //The River :tm:
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT), // However tall we need for a card
                horizontalArrangement = Arrangement.Center)
            {
                //The river will have 5 cards. we can do this by making 5 boxes to hold out images
                addCard(curCardID = 1)
                addCard(curCardID = 2)
                addCard(curCardID = 3)
                addCard(curCardID = 4)
                addCard(curCardID = 5)
            }
            
            Spacer(modifier = Modifier.padding(50.dp))
            
            addText(text = "User Hand")
            //The users hand
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT), // However tall we need for a card
                horizontalArrangement = Arrangement.Center)
            {
                //The river will have 5 cards. we can do this by making 5 boxes to hold out images
                addCard(curCardID = 1)
                addCard(curCardID = 2)
            }
        }
    }
}

@Composable
private fun addCard(curCardID: Int)
{
    //determine which drawable to use
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
            .padding(1.dp),
        contentAlignment = Alignment.Center,
    )
    {
        // The Pretty Picture
        Image(
            painter = painterResource(id = R.drawable.samplecard),
            contentDescription = "Card"
        )
    }
}

@Composable
private fun addText(text : String)
{
    //Row for the river text
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp), // However tall we need for a card
        horizontalArrangement = Arrangement.Center) {
        //Box to put the text in
        Box(contentAlignment = Alignment.Center) {
            Text(
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.h5.fontSize,
                text = text
            )
        }
    }
}


@Composable
@Preview
fun gamePreview()
{
    GameBoardScreen(navController = rememberNavController())
}