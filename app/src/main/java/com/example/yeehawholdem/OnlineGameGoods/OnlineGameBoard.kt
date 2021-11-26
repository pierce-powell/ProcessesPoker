package com.example.yeehawholdem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeehawholdem.GameBoardGoods.AddCard
import com.example.yeehawholdem.GameBoardGoods.AddCardBacks
import com.example.yeehawholdem.GameBoardGoods.AddText
import com.example.yeehawholdem.GameBoardGoods.CARD_HEIGHT
import com.example.yeehawholdem.LogicGoods.Card
import com.example.yeehawholdem.OnlineGameGoods.Game
import com.example.yeehawholdem.OnlineGameGoods.GameState
import com.example.yeehawholdem.LogicGoods.GameValues
import com.example.yeehawholdem.OnlineGameGoods.Communications
import com.example.yeehawholdem.OnlineGameGoods.QuitGameDataHandler
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

/*
Heyo peeps, heres some important notes to consider with how the lobby works, I tried to make it
easier on the logic end to manage a host.

At the start of a game, the lobby has a variable called IsGameInProgress(0 means false and 1 means True)
if the variable is 1, players will be fed into a waiting room. Therefore, upon starting the game,
you should pull all players out of the waiting room and add them to the ActiveUsers tag.

Furthermore, the host is saved under the LobbyName, and if a user becomes the host, under their unique ID
tag along with their username and balance, the lobby that they are the host of will be added.
This will make for an easy reference tag that the host can use to make updates this will be stored under
HostOfLobby


Personal Aside for what needs to happen when a user quits the game:
Check if the current user is a host, if they are transfer host status when the game ends
If the user is the host, don't update the host status until the IsGameInProgress is 0
When it is, remove them from the lobby (Active or Waitroom), reduce the number of players by 1
If they were the host, delete the HostOfLobby variable under their Uid
 */

var ls = ""

// TODO: Add timer to timeout players who take too long
@Composable
fun GameBoardOnline(navController: NavController) {
    var dummy by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = dummy) {
        delay(200)
        dummy = !dummy
    }

    val communications = Communications()
    val gameVals by remember { mutableStateOf(GameValues()) }
    var list by remember { mutableStateOf(mutableListOf<Long>(-1)) }
    var showDialog by remember { mutableStateOf(false) }
    var card1 by remember { mutableStateOf(false) }
    var card2 by remember { mutableStateOf(false) }
    var card3 by remember { mutableStateOf(false) }
    var card4 by remember { mutableStateOf(false) }
    var card5 by remember { mutableStateOf(false) }
    var card6 by remember { mutableStateOf(false) }
    var card7 by remember { mutableStateOf(false) }
    var bet by remember { mutableStateOf(0) }
    var curBet by remember { mutableStateOf(0) }
    var userBet by remember { mutableStateOf(0) }
    var pot by remember { mutableStateOf(0) }
    var balance by remember { mutableStateOf(gameVals.getBalance().toInt()) }
    var isStillIn by remember { mutableStateOf(gameVals.getIsStillIn()) }
    var isHost by remember { mutableStateOf(false) }
    var IsGameInProgress by remember { mutableStateOf(false) }
    var startGame by remember { mutableStateOf(false) }
    var gameClass by remember { mutableStateOf(Game(gameVals, communications, ls)) }
    var curBetCycle by remember { mutableStateOf(0) }

    // Get the lobby number and store it in the String "ls"
    LaunchedEffect(Unit) {
        ls = communications.getLobbyString()
        gameClass = Game(gameVals, communications, ls)
        // communications.setupLobbyEventListener(gameVals, ls)
        communications.usersEventListener(gameVals, ls)
        communications.setupRiverEventListener(gameVals, ls)
        communications.setupBetEventListener(gameVals, ls)
        communications.setupPotEventListener(gameVals, ls)
        communications.setupIsHostListener(gameVals, ls)
        communications.setupIsGameInProgressListener(gameVals, ls)
        communications.setupCurrentActivePlayerEventListener(gameVals, ls)
        communications.setupNumPlayersCheckedEventListener(gameVals, ls)
        communications.setupCurrBetCycleEventListener(gameVals, ls)
        communications.setupNumPlayersEventListener(gameVals, ls)
        communications.setupPlayerEventListener(gameVals, ls)
    }

    // flags for the UI to display the cards
    card1 = gameVals.getCard1() != -1L
    card2 = gameVals.getCard2() != -1L
    card3 = gameVals.getCard3() != -1L
    card4 = gameVals.getCard4() != -1L
    card5 = gameVals.getCard5() != -1L
    card6 = gameVals.getHandCard1() != -1L
    card7 = gameVals.getHandCard2() != -1L
    balance = gameVals.getBalance().toInt()
    curBet = gameVals.getBet().toInt()
    pot = gameVals.getPot().toInt()
    IsGameInProgress = gameVals.getIsGameInProgress()
    curBetCycle = gameVals.getCurrBetCycle()

    // This is the isHost check for the composable, use with if statement
    if(gameClass.gameState == GameState.STARTGAME)
        isHost = gameVals.getIsHost()

    if((gameClass.isFolded() && gameClass.isTurn()) && gameClass.gameState != GameState.STARTGAME){
        gameClass.increaseCurrentActivePlayer()
    }

    if(gameClass.isShowdown()){
        gameClass.showdownOnline()
        startGame = false
        gameClass.gameState = GameState.STARTGAME
    }

    // Gameplay Loop for Host
    if (isHost) {
        if ((startGame) && (gameClass.gameState == GameState.STARTGAME)) {
            gameClass.startGame()
        }
        when {
            gameClass.haveAllPlayersChecked() -> {
                gameClass.changeState()
            }
            gameClass.gameState == GameState.ROUND1 -> {
                gameClass.setupRound1()
            }
            gameClass.gameState == GameState.ROUND2 -> {
                gameClass.setupRound2()
            }
            gameClass.gameState == GameState.ROUND3 -> {
                gameClass.setupRound3()
            }
        }
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
        }
        //Outer Column to store our two rows
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxHeight(0.97f)
                .fillMaxWidth()
        )

        {
            if (isHost) {
                Button(//Start button
                    onClick = {
                        startGame = true
                        // communications.setIsGameInProgress(ls, true)
                        if(gameVals.getNumPlayers() > 1)
                            startGame = true
                    },
                    modifier = Modifier
                        .fillMaxWidth(.27f)
                        .height(BUTTON_HEIGHT), enabled = !startGame
                )
                {
                    Text(text = "Start Game", fontSize = MaterialTheme.typography.h5.fontSize, textAlign = TextAlign.Center)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                /*if (!cardsFlags[7]) AddCardBacks()
                else AddCard(card = game.dealer_player.hand.getOrNull(0))
                if (!cardsFlags[8]) AddCardBacks()
                else AddCard(card = game.dealer_player.hand.getOrNull(1))*/
            }
            AddText(text = "The River")
            //The River :tm:
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CARD_HEIGHT), // However tall we need for a card
                horizontalArrangement = Arrangement.Center
            )
            {
                // The River, Display cards facedown until they are revealed
                if (card1) AddCard(card = Card(gameVals.getCard1().toInt()))
                else AddCardBacks()
                if (card2) AddCard(card = Card(gameVals.getCard2().toInt()))
                else AddCardBacks()
                if (card3) AddCard(card = Card(gameVals.getCard3().toInt()))
                else AddCardBacks()
                if (card4) AddCard(card = Card(gameVals.getCard4().toInt()))
                else AddCardBacks()
                if (card5) AddCard(card = Card(gameVals.getCard5().toInt()))
                else AddCardBacks()
            }
            Spacer(modifier = Modifier.padding(10.dp))
            AddText(text = "Pot: ${gameVals.getPot()}" + "   Highest Bet: ${gameVals.getBet()}")
            Spacer(modifier = Modifier.padding(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                Button(//fold button
                    onClick = {
                        gameVals.setIsStillIn(false)
                    },
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .height(BUTTON_HEIGHT), enabled = gameClass.isTurn() && IsGameInProgress
                )
                {
                    Text(text = "Fold", fontSize = MaterialTheme.typography.h5.fontSize)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(.5f)
                )
                {
                    AddText(text = "Bet: $bet")
                    Button(
                        onClick = {
                            if(gameClass.isTurn() && !gameClass.isFolded()) {
                                communications.setUserBet(ls, bet.toLong())
                                //raise logic
                                if (bet > curBet && bet < balance) {
                                    communications.setBet(ls, bet.toLong())
                                    balance -= bet - curBet
                                    communications.setBalance(ls, balance.toLong())
                                    communications.setPot(ls, bet - userBet + gameVals.getPot())
                                    communications.setNumPlayersChecked(ls, 1)
                                    gameClass.increaseCurrentActivePlayer()
                                }
                                //check logic
                                else if (bet == curBet && bet < balance) {
                                    communications.setBet(ls, bet.toLong())
                                    balance -= bet - curBet
                                    communications.setBalance(ls, balance.toLong())
                                    communications.setPot(ls, bet - userBet + gameVals.getPot())
                                    communications.setNumPlayersChecked(
                                        ls,
                                        gameVals.getNumPlayersChecked() + 1L
                                    )
                                    gameClass.increaseCurrentActivePlayer()
                                }
                                //all in logic
                                else if (bet == balance) {
                                    //TODO: all in logic
                                } else {
                                    //TODO: Invalid bet toast
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(45.dp), enabled = gameClass.isTurn() && IsGameInProgress
                    )
                    {
                        Text(text = "Lock in", fontSize = MaterialTheme.typography.h5.fontSize)
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(.4f)
                )
                {
                    Button(//raise bet button
                        onClick = {
                            bet += 5
                            //communications.setBet(ls, bet.toLong())
                        },
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .height(40.dp), enabled = gameClass.isTurn() && IsGameInProgress
                    )
                    {
                        Text(text = "+", fontSize = MaterialTheme.typography.h5.fontSize)
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    Button(//lower bet button
                        //TODO: Expand logic for allowing lower bet (do not allow bet bellow current Bet variable)
                        onClick = {
                            if (bet - 5 >= curBet)
                                bet -= 5
                        },
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .height(40.dp), enabled = gameClass.isTurn() && IsGameInProgress
                    )
                    {
                        Text(text = "-", fontSize = MaterialTheme.typography.h5.fontSize)
                    }
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            AddText(text = "User Hand")
            //The users hand
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CARD_HEIGHT), // However tall we need for a card
                horizontalArrangement = Arrangement.Center
            )
            {
                // Display Player Hand
                if (card6) AddCard(card = Card(gameVals.getHandCard1().toInt()))
                else AddCardBacks()
                if (card7) AddCard(card = Card(gameVals.getHandCard2().toInt()))
                else AddCardBacks()
                // AddText(text = "player cards goes here")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            AddText(text = "User balance: $balance")
        }

        addQuitButton(navController = navController)
    }
}

data class quitInfo(
    var lobby: String? = "",
    var playerID: String? = "",
    var didPlayerQuit: Long? = 0,
    var playerList: MutableMap<String?, Long?> = mutableMapOf(),
    var waitRoom: MutableMap<String?, Long?> = mutableMapOf(),
    var host: String? = ""
)


@Composable
fun addQuitButton(navController: NavController) {

    var quitData by remember {
        mutableStateOf(quitInfo())
    }

    //Tells all the users that they someone quit and the game is ending
    var trigerQuitDialog by remember {
        mutableStateOf(false)
    }

    //Tells the user that something went wrong with the quit and they should try again
    var triggerFailedQuitDialog by remember {
        mutableStateOf(false)
    }

    var numPlayers by remember {
        mutableStateOf(0)
    }



    var quitDataHandler = QuitGameDataHandler()

    quitDataHandler.getTheLobbyName(quitData)


    //If a player quit and they are not in the waitroom
    if ((quitData.didPlayerQuit?.toInt() == 1) && (quitData.waitRoom.containsKey(quitData.playerID) == false) ) { //And check to make sure they're not in the wait room
        trigerQuitDialog = true
    }


    if (trigerQuitDialog) {
        LaunchedEffect(key1 = trigerQuitDialog) {
            delay(5000)
            Firebase.database.getReference(quitData.lobby.toString()).child("DidPlayerQuit")
                .setValue(0)
            navController.navigate(Screen.MainMenu.route)
        }
    }


    Button(//quit button
        onClick = {
            if (quitData.lobby.isNullOrEmpty()) {
                triggerFailedQuitDialog = true
            } else {
                //Before we start deleting all of the data, lets edit the balance for every player
                quitData.playerList.map {
                    //First update the floating uid balance
                    Firebase.database.getReference(it.key.toString()).child("balance")
                        .setValue(it.value)
                    Firebase.database.getReference("Users").child(it.key.toString())
                        .child("balance").setValue(it.value)
                }

                //We have the lobby name to make a refernece, so lets reference it and update all the stuff we need
                //Lets get the realtime database referenced at the lobby
                val lobbyRef = Firebase.database.getReference(quitData.lobby.toString())
                //Now lets start making changes.
                //Firstly, we want to issue the flag to all users that someone has quit the game
                //and interfered with the lobby progress
                //If a player in the waitroom quit, no one cares, just remove them
                if(quitData.waitRoom.containsKey(quitData.playerID))
                {
                    //Now we also need to remove them from the player count
                    //So before we remove them from the WaitingRoom, lets calculate the current player count
                    numPlayers = quitData.playerList.count() + quitData.waitRoom.count()
                    //Now we set it to this -1
                    lobbyRef.child("NumPlayers").setValue(numPlayers - 1)
                    //Making sure that we get both locations
                    Firebase.database.getReference("Lobbys").child(quitData.lobby.toString()).setValue(numPlayers - 1)

                    //Now we need to check and make sure some jank didn't unfold, if they're the host we're going to need to migrate it to someone else
                    if(quitData.playerID == quitData.host)
                    {
                        //they're the host, so lets see if theres someone else we can change it to
                            //Lets check if theres another person in the wait room
                        if(quitData.waitRoom.count() > 1)
                        {
                            //This means that the host migration actually matters because somone else will be playing
                            if(quitData.waitRoom.toList()[0].toString() == quitData.playerID.toString())
                            {
                                // they're the first key, so set it to the second
                                lobbyRef.child("Host").setValue(quitData.waitRoom.toList()[1])
                            }
                            else
                            {
                                //they we'ren't the first, so set it to the first
                                lobbyRef.child("Host").setValue(quitData.waitRoom.toList()[0])
                            }
                        }
                        else
                        {
                            //They're the last person in the wait room anyway, so everyone has quit the game already
                            lobbyRef.child("Host").setValue("")
                        }

                    }

                    //Remove them from the waitlist
                    lobbyRef.child("WaitingRoom").child(quitData.playerID.toString()).removeValue()


                    //And that it, no balance update because they never played, so lets reroute them
                    navController.navigate(Screen.MainMenu.route)

                }
                //Otherwise, its one of our active players thats quit
                else {

                    //Trigger the warning, note this won't trigger for people in the wait room
                    lobbyRef.child("DidPlayerQuit").setValue(1)

                    //TODO Graceful quit, removes the players from active but not waitroom, player count should reflect the wait room players

                    //Update the player count with num waitlist players
                    lobbyRef.child("NumPlayers").setValue(quitData.waitRoom.count())
                    //Making sure that we get both locations
                    Firebase.database.getReference("Lobbys").child(quitData.lobby.toString()).setValue(quitData.waitRoom.count())

                    //Now with that squared away, lets see if we need to perform a host migration
                    //TODO Feed in the host ID
                    if(quitData.waitRoom.contains(quitData.host) == false)
                    {
                        //awe shucks, the host isn't in the wait room by some miraculous miracle
                            //SO lets check if theres someone in the waitroom
                        if(quitData.waitRoom.count() > 0) {
                            //Someone is, so lets do the host migration, and set it to the first player in the wait room
                            lobbyRef.child("Host").setValue(quitData.waitRoom.keys.toList()[0])
                        }
                        else{
                            //The wait room is empty so set the host to be nothing
                            lobbyRef.child("Host").setValue("")
                        }
                    }
                    //Otherwise, the host is already in the wait room, so no need to do anything special


                    //Now with that set, all the players should first receive the dialog that the game has ended
                    //So lets just start making everything the defaults
                    lobbyRef.child("Bet").setValue(0)

                    //Now the pot
                    lobbyRef.child("Pot").setValue(0)


                    //Now lets change the Active Players
                    lobbyRef.child("CurrentActivePlayer").setValue(0)

                    //Change the lobby status
                    lobbyRef.child("IsGameInProgress").setValue(false)

                    //Reset all the river values
                    lobbyRef.child("River").child("Card1").setValue(-1)
                    lobbyRef.child("River").child("Card2").setValue(-1)
                    lobbyRef.child("River").child("Card3").setValue(-1)
                    lobbyRef.child("River").child("Card4").setValue(-1)
                    lobbyRef.child("River").child("Card5").setValue(-1)

                    //reset current bet cycle
                    lobbyRef.child("CurrBetCycle").setValue(0)

                    //reset number of players checked
                    lobbyRef.child("NumPlayersChecked").setValue(0)

                    //Now the active users
                    lobbyRef.child("ActiveUsers").removeValue()
                }
            }

        },
        modifier = Modifier
            .fillMaxWidth(.4f)
            .height(40.dp)
    )
    {
        Text(text = "Quit Game", fontSize = MaterialTheme.typography.h5.fontSize)
    }


    //Someone quit the game!
    if (trigerQuitDialog == true) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "Oh No! Someone Quit the Game!")
            },
            text = {
                Text(text = "Returning to the MainMenu in 5 seconds!")
            },
            confirmButton = {}
        )
    }


    //If we don't have the lobby name by the time they try to quit
    if (triggerFailedQuitDialog == true) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "There was an error quiting, please try again!")
            },
            confirmButton = {
                Button(onClick = {
                    triggerFailedQuitDialog = false
                })
                {
                    Text(text = "Ok")
                }
            }
        )
    }
}


@Composable
@Preview(showBackground = true)
fun GamePreview() {
    GameBoardOnline(navController = rememberNavController())
}


