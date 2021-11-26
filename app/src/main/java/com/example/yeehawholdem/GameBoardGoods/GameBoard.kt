package com.example.yeehawholdem.GameBoardGoods

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.example.yeehawholdem.LogicGoods.Card
import com.example.yeehawholdem.LogicGoods.GameOffline
import com.example.yeehawholdem.OnlineGameGoods.GameState
import com.example.yeehawholdem.R
import com.example.yeehawholdem.Screen

//Global variables
val CARD_HEIGHT = 120.dp
const val STARTING_BET = 10
const val STARTING_BALANCE = 1000

//TODO: Display Best Hand at the end of the round
//TODO: Make the height connected to the individual box instead of the row, so we can click to enlarge?

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
    var showWinner by remember { mutableStateOf(false) }
    var playerWins by remember { mutableStateOf(false) }
    val game by remember{ mutableStateOf(GameOffline()) }
    var userBet by remember{ mutableStateOf(STARTING_BET) }
    var round by remember{ mutableStateOf(0) }
    var fold by remember{ mutableStateOf(false) }
    val cardsFlags by remember{ mutableStateOf( BooleanArray(9)) }

    // Some functions for the cards display flags,
    // 0-4 = Community cards, 5-6 = player's, 7-8 = dealer's
    fun hideAllCards() {
        for (i in cardsFlags.indices) {
            cardsFlags[i] = false
        }
    }
    fun revealAllCards() {
        for (i in cardsFlags.indices) {
            cardsFlags[i] = true
        }
    }
    fun revealFirstThree() {
        cardsFlags[0] = true
        cardsFlags[1] = true
        cardsFlags[2] = true
    }
    fun revealFourth() {
        cardsFlags[3] = true
    }
    fun revealFifth() {
        cardsFlags[4] = true
    }
    // Gameplay Loop
    if (game.gameState != GameState.STOPPED) {
        when {
            game.gameState == GameState.NEXTGAME -> {
                // Start the Next New Game
                game.nextGame()
                hideAllCards()
                round = 0
                fold = false
                userBet = STARTING_BET
                game.table.currentPot = 0
            }
            game.gameState == GameState.BETORCHECK -> {
                // Betting or Checking
                game.betting()
            }
            round >= 4 -> {
                // Showdown after the 5th card is revealed
                if(game.showdown() == "Test Player 1"){
                    playerWins = true
                }
                showWinner = true
                cardsFlags[7] = true
                cardsFlags[8] = true
            }
        }
    }

    if (fold) {
        playerWins = false
        showWinner = true
    }
    if (round == 2) {
        revealFourth()
    } else if (round == 3) {
        revealFourth()
        revealFifth()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter)
    {
        //Outer Column to store our two rows
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
        )
        {
            Row(//exit game button
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
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
                Spacer(modifier = Modifier.padding(30.dp))
                AddText(text = "Dealer bet: $userBet                     ")//Jank space to center
            }


            Row(modifier = Modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT),
                horizontalArrangement = Arrangement.Center)
            {
                if (!cardsFlags[7]) AddCardBacks()
                else AddCard(card = game.dealer_player.hand.getOrNull(0))
                if (!cardsFlags[8]) AddCardBacks()
                else AddCard(card = game.dealer_player.hand.getOrNull(1))
            }
            Spacer(modifier = Modifier.padding(20.dp))
            AddText(text = "The River")
            //The River :tm:
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT),
                //.horizontalScroll(rememberScrollState()), // However tall we need for a card
                horizontalArrangement = Arrangement.Center)
            {
                // The River, Display cards facedown until they are revealed
                if (!cardsFlags[0]) AddCardBacks()
                else AddCard(card = game.table.sharedDeck.getOrNull(0))
                if (!cardsFlags[1]) AddCardBacks()
                else AddCard(card = game.table.sharedDeck.getOrNull(1))
                if (!cardsFlags[2]) AddCardBacks()
                else AddCard(card = game.table.sharedDeck.getOrNull(2))
                if (!cardsFlags[3]) AddCardBacks()
                else AddCard(card = game.table.sharedDeck.getOrNull(3))
                if (!cardsFlags[4]) AddCardBacks()
                else AddCard(card = game.table.sharedDeck.getOrNull(4))
            }
            Spacer(modifier = Modifier.padding(10.dp))
            AddText(text = "Pot: ${game.table.currentPot}")//changed from pot
            Spacer(modifier = Modifier.padding(10.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
                horizontalArrangement = Arrangement.Center)
            {
                Button(//fold button
                    onClick = {
                        //the dealer wins and the next round starts
                        fold = true
                    },
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .height(BUTTON_HEIGHT)
                )
                {
                    Text(text = "Fold", fontSize = MaterialTheme.typography.h5.fontSize)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.5f)
                )
                {
                    AddText(text = "Bet: $userBet")
                    Button(
                        onClick = {
                            game.gameState = GameState.BETORCHECK
                            game.betting(userBet)
                            revealFirstThree()
                            game.nextRound()
                            round++
                            userBet = 0
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
                            if (userBet + 5 <= game.player.balance)
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
                            if(userBet - 5 >= 0 && (round != 0))
                                userBet -= 5
                            else if (userBet - 5 > 0)
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
            AddText(text = "User Hand")
            //The users hand
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT), // However tall we need for a card
                horizontalArrangement = Arrangement.Center)
            {
                // Display Player Hand
                AddCard(card = game.player.hand.getOrNull(0))
                AddCard(card = game.player.hand.getOrNull(1))
            }
            Spacer(modifier = Modifier.padding(10.dp))
            AddText(text = "User balance: ${game.table.playerArray[0].balance}")
            Spacer(modifier = Modifier.padding(20.dp))
        }
    }

    // pop up message to say you won or lost at end of round
    if (showWinner) {
        revealAllCards()
        AlertDialog(onDismissRequest = {},
            title = {
                if(playerWins){
                    Text(text = "You won!")
                }
                else{
                    Text(text = "You lost!")
                }
            },
            confirmButton = {
                Button(onClick = {
                    showWinner = false
                    playerWins = false
                    // Wait till user presses the button to continue
                    game.gameState = GameState.NEXTGAME
                })
                {
                    Text(text = "Cool")
                }
            }
        )
    }
}

@Composable
fun AddCard(card: Card?)
{
    val scale = remember { mutableStateOf(1f)}

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
        if (card != null) {
            Image(modifier = Modifier.graphicsLayer(
                scaleX = maxOf(.1f, minOf(3f, scale.value)),
                scaleY = maxOf(.1f, minOf(3f, scale.value)),
            ),
                painter = painterResource(id = card.cardPicture),
                contentDescription = "Card"
            )
        }
    }
}

@Composable
fun AddCardBacks()
{
    val scale = remember { mutableStateOf(1f)}

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
fun AddText(text : String) {
    // Wrap in a surface so it can pick up on light-mode vs dark
    Surface {
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
fun GamePreview()
{
    GameBoardOfflineScreen(navController = rememberNavController())
}