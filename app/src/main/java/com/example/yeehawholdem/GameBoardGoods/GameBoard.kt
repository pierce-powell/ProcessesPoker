package com.example.yeehawholdem.GameBoardGoods

import android.view.Surface
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeehawholdem.BUTTON_HEIGHT
import com.example.yeehawholdem.BUTTON_WIDTH
import com.example.yeehawholdem.LogicGoods.*
import com.example.yeehawholdem.R
import com.example.yeehawholdem.Screen

//Global variables
public val CARD_HEIGHT = 109.dp
val SMALL_BLIND = 10
val BIG_BLIND = 20
val STARTING_BET = 10
val STARTING_BALANCE = 1000

//TODO: Diplay the current pot
//TODO: Quit game button
//TODO: Hold, Bet, Raise
//TODO: Make the image correspond to the actual card in play
//TODO: Display Best Hand at the end of the round
//TODO: Make the height connected to the individual box instead of the row, so we can click to enlarge?

//private val howManyFlips = 0 (Just ask the dealer obj)

/*
Use Case:
User clicks play offline
Booted into game screen, seeing only their hand and are asked to fold or bet and can
    see the dealers starting bet as well as the pot initialized with that bet
upon bet confirmation, the first three cards in the river are flipped, dealer places a new bet,
    user is then faced with same question, hold, fold, raise
Upon confirmation, the 4th card is flipped (more bets)
then 5th (more bets)
Winner is then displayed on screen with their best hand.
Pot is added to the users balance
Then Quit or Continue prompt is displayed
Continue restarts the game
Quit returns to Main Menu

 */

@Composable
fun GameBoardOfflineScreen(navController : NavController)
{
    //variables for later
    val game by remember{ mutableStateOf(GameOffline())}
    // var gameState by remember{ mutableStateOf(game.gameState)}
    var pot by remember{ mutableStateOf(0)}
    var cardsFlipped = 0
    var userBet by remember{ mutableStateOf(STARTING_BET) }
    var dealerBet by remember{ mutableStateOf(STARTING_BET)}
    var card1 by remember{ mutableStateOf(false)}
    var card2 by remember{ mutableStateOf(false)}
    var card3 by remember{ mutableStateOf(false)}
    var card4 by remember{ mutableStateOf(false)}
    var card5 by remember{ mutableStateOf(false)}
    var card6 by remember{ mutableStateOf(false)}
    var card7 by remember{ mutableStateOf(false)}
    var round by remember{ mutableStateOf(0)}
    var _card1 by remember{ mutableStateOf(game.table.sharedDeck[0])}
    _card1 = game.table.sharedDeck[0]

    // Gameplay Loop
    // TODO: Implement proper game state logic. Get bet and fold parameters from user.
    if (game.gameState != GameState.STOPPED) {
        if (game.gameState == GameState.NEXTGAME) {
            // Start the Next New Game
            game.nextGame()
            round = 0
            card1 = false
            card2 = false
            card3 = false
            card4 = false
            card5 = false
            card6 = false
            card7 = false
            userBet = STARTING_BET
            dealerBet = STARTING_BET
            pot = 0
        } else if (game.gameState == GameState.BETORCHECK) {
            // Betting or Checking
            game.betting()
        } else if (round >= 4) {
            // Showdown
            game.showdown()
        } else if (game.gameState == GameState.NEXTROUND) {
            // Next Round
            // game.gameState = GameState.BETORCHECK
            // game.nextRound()
        }
    }


    card6 = true
    card7 = true
    if (round == 2) {
        card4 = true
    } else if (round == 3) {
        card4 = true
        card5 = true
    }

    if (round == 4) {
        card1 = false
        card2 = false
        card3 = false
        card4 = false
        card5 = false
        card6 = false
        card7 = false
    }



    if (game.gameState == GameState.STOPPED) {
        // Ask User If they want to keep playing?
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter)
    {
        Row(//exit game button
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalArrangement = Arrangement.End
        )
        {
            Button(
                onClick = {
                    game.gameState = GameState.STOPPED
                    navController.navigate(route = Screen.MainMenu.route)
            }, modifier = Modifier
                    .fillMaxWidth(.15f)
                    .height(BUTTON_HEIGHT))
            {
                Text(text = "X", fontSize = MaterialTheme.typography.h5.fontSize)
            }
            addText(text = "Dealer bet: $dealerBet")
        }

        //Outer Column to store our two rows
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
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
                if (!card1) addCardBacks()
                else  addCard(card = _card1, curCardID = 1)
                if (!card2) addCardBacks()
                else addCard(card = game.table.sharedDeck[1], curCardID = 2)
                if (!card3) addCardBacks()
                else addCard(card = game.table.sharedDeck[2], curCardID = 3)
                if (!card4) addCardBacks()
                else addCard(card = game.table.sharedDeck[3], curCardID = 4)
                if (!card5) addCardBacks()
                else addCard(card = game.table.sharedDeck[4], curCardID = 5)

                /*
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(MaterialTheme.colors.background)
                        .padding(1.dp)
                        .clip(RectangleShape),
                    contentAlignment = Alignment.Center,
                )
                {
                    // The Pretty Picture
                    Image(
                        painter = painterResource(id = game.table.sharedDeck.getOrNull(4)!!.cardPicture),
                        contentDescription = "Card"
                    )
                }*/
            }
            Spacer(modifier = Modifier.padding(15.dp))
            addText(text = "Pot: $pot")
            Spacer(modifier = Modifier.padding(15.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
                horizontalArrangement = Arrangement.Center)
            {
                Button(//fold button
                    onClick = {
                              //the dealer wins and the next round starts
                              game.fold()
                    },
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .height(BUTTON_HEIGHT)
                )
                {
                    Text(text = "Fold", fontSize = MaterialTheme.typography.h5.fontSize)
                }
                Spacer(modifier = Modifier.padding(15.dp))
                //if(userBet < dealerBet)
                    //userBet = dealerBet
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.5f)
                )
                {
                    addText(text = "Bet: $userBet")
                    Button(
                        onClick = {
                            game.gameState = GameState.BETORCHECK
                            game.betting(userBet)
                            card1 = true
                            card2 = true
                            card3 = true
                            game.nextRound()
                            round++
                            pot = dealerBet + userBet
                            userBet = STARTING_BET
                            dealerBet = STARTING_BET
                        },
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(45.dp)
                    )
                    {
                        Text(text = "Lock in", fontSize = MaterialTheme.typography.h5.fontSize)
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.4f)
                )
                {
                    Button(//raise bet button
                        onClick = {
                            userBet += 5
                        },
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .height(40.dp)
                    )
                    {
                        Text(text = "+", fontSize = MaterialTheme.typography.h5.fontSize)
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    Button(//lower bet button
                        onClick = {
                            if(userBet - 5 > 0)
                                userBet -= 5
                        },
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .height(40.dp)
                    )
                    {
                        Text(text = "-", fontSize = MaterialTheme.typography.h5.fontSize)
                    }
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            addText(text = "User Hand")
            //The users hand
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT), // However tall we need for a card
                horizontalArrangement = Arrangement.Center)
            {
                //The river will have 5 cards. we can do this by making 5 boxes to hold out images
                if (!card6) addCardBacks()
                else addCard(card = game.player.hand[0], curCardID = 1)
                if (!card7) addCardBacks()
                else addCard(card = game.player.hand[1], curCardID = 2)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            addText(text = "User balance: ${game.table.playerArray[0].balance}")
        }
    }
}

fun setIsGameActive() {
    //table.getGameStatus()
}

private fun FoldBetConfirm()
{
    /*
    Fold : Button they can click (no user input)
    Bet : Mini pop up populated with the dealers bet locked,
    ______________________________________
    |  INT BET                       ^    |
    | (Default to dealers)        adjust  |
    |  CONFIRM                       v    |
    --------------------------------------
     */


   /* //On click functionality for confirm button
    if (cardsFlipped == 0)
        //Flip 3
    else if (cardsFlipped == 3)
        //flip the 4th
    else if (cardsFlipped == 4)
        //flip the 5th

    */
}

private fun incrementRound()
{

}

@Composable
private fun addCard(card: Card, curCardID: Int = 0)
{
    val scale = remember { mutableStateOf(1f)}

    // var card = table.sharedDeck?.getOrNull(curCardID - 1)
    // var card: Card? = dealer?.usableDeck?.getOrNull(0) // TODO: Change to use dealer's deck
    // dealer?.usableDeck?.removeAt(0) // TODO: Deal cards before removing from deck

    //TODO: Add the proper card based on ID

    //determine which drawable to use
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
            .padding(1.dp)
            .clip(RectangleShape)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                }
            },
        contentAlignment = Alignment.Center,
    )
    {
        // The Pretty Picture
        Image(modifier = Modifier.graphicsLayer(
            scaleX = maxOf(.1f, minOf(3f, scale.value)),
            scaleY = maxOf(.1f, minOf(3f, scale.value)),
        ),
            painter = painterResource(id = card.cardPicture),
            contentDescription = "Card"
        )
    }
}

@Composable
private fun addCardBacks(curCardID: Int = 0)
{
    val scale = remember { mutableStateOf(1f)}

    // var card = table.sharedDeck?.getOrNull(curCardID - 1)
    // var card: Card? = dealer?.usableDeck?.getOrNull(0) // TODO: Change to use dealer's deck
    // dealer?.usableDeck?.removeAt(0) // TODO: Deal cards before removing from deck

    //TODO: Add the proper card based on ID

    //determine which drawable to use
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
            .padding(1.dp)
            .clip(RectangleShape)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                }
            },
        contentAlignment = Alignment.Center,
    )
    {
        // The Pretty Picture
        Image(modifier = Modifier.graphicsLayer(
            scaleX = maxOf(.1f, minOf(3f, scale.value)),
            scaleY = maxOf(.1f, minOf(3f, scale.value)),
        ),
            painter = painterResource(id = R.drawable.samplecard),
            contentDescription = "Card"
        )
    }
}

@Composable
private fun addText(text : String) {
    // Wrap in a surface so it can pick up on light-mode vs dark
    Surface() {
        //Row for the river text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp), // However tall we need for a card
            horizontalArrangement = Arrangement.Center
        ) {
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
}

@Composable
@Preview(showBackground = true)
fun gamePreview()
{
    // val game = Game()
    // game.startGame()
    GameBoardOfflineScreen(navController = rememberNavController())
}