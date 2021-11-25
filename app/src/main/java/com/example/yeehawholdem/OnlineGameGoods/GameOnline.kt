package com.example.yeehawholdem.LogicGoods

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.yeehawholdem.GameBoardGoods.STARTING_BALANCE
import com.example.yeehawholdem.OnlineGameGoods.Communications
import com.example.yeehawholdem.Screen
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import java.util.concurrent.ExecutionException

// If host deal out the cards to the players at the start of the game.
// Deal out the cards to the River at the start of each round.
// Go into a round of betting (round()).
// Keep betting until they fold/call/check.
// Have a players have checked variable in the database. Set it to 0 if someone has raised.
// After all 3 cards have been added to the river and betting is done, have all players run determineWinner().
// Send the number into the database as handValue, and host retrieves that value and puts it into a list.
enum class GameState {
    //Start, firstRound, SecondRound, ThirdRound, Showdown, End
    STARTGAME, RUNNING, ROUND1, ROUND2, ROUND3, SHOWDOWN, STOPPED, NEXTGAME, BETORCHECK, NEXTROUND
}

class Game//this.lobbyStr = lobbyStr//When creating a Game object, initialize with list of players for the game
    (gameVa: GameValues, communicator: Communications, var lobbyStr: String) {
    var dealer = Dealer()
    var table = Table()
    var gameState = GameState.STARTGAME
    var dealerButton = 0
    var turn = 0
    var gameVals: GameValues = gameVa
    var communicator: Communications = communicator
    var isRound1Setup = false
    var isRound2Setup = false
    var isRound3Setup = false


    //Shuffles the deck of cards, deals a set of two cards to each player, and sets the game state
    //running
    fun startGame() {
        table.setupDeck()
        createPlayersList() // set up table.playerArray
        table.dealAllCards()
        communicator?.usersEventListener(gameVals, lobbyStr)
        //TODO: first player = host
        //TODO: Send all players in waitlist to active users
        //TODO: Populate database.activeUsers hands with cards if host

        // get all the states, and then set up the listeners

        // HotFix for numplayers
        communicator.setNumPlayers(lobbyStr, table.playerArray.size.toLong())

        // Update the values on the database (player hands, CurrentActivePlayer
        communicator?.setPlayerHands(lobbyStr, table.playerArray)
        communicator?.setIsGameInProgress(lobbyStr, true) // Set Game to in Progress setPlayerTurnNumber
        communicator?.setPlayerTurnNumber(lobbyStr, table.playerArray)
        communicator?.setCurrentActivePlayer(lobbyStr, 0)

        gameState = GameState.RUNNING
    }

    fun createPlayersList() {
        table.playerArray.addAll(gameVals.playerList)
        table.resetPlayers()
    }

    // Change the suitable flags in the Database to allow the current player to bet/raise/fold
    // The pot and balance are handled on the player's side
    // Increase the CurrentActivePlayer
    fun changeState() {
        if(!isRound1Setup){
            gameState = GameState.ROUND1
        }
        else if(!isRound2Setup){
            gameState = GameState.ROUND2
        }
        else if(!isRound3Setup){
            gameState = GameState.ROUND3
        }
        else{
            gameState = GameState.SHOWDOWN
        }
        communicator?.setNumPlayersChecked(lobbyStr, 0)
    }

    fun setupRound1(){
        communicator?.setBet(lobbyStr, 0L)
        communicator?.setNumPlayersChecked(lobbyStr, 0)
        communicator?.setCurrentActivePlayer(lobbyStr, 0)
        setCardsRound1()
        gameState = GameState.RUNNING
        isRound1Setup = true
    }
    fun setupRound2(){
        communicator?.setBet(lobbyStr, 0L)
        communicator?.setNumPlayersChecked(lobbyStr, 0)
        communicator?.setCurrentActivePlayer(lobbyStr, 0)
        setCardsRound2()
        gameState = GameState.RUNNING
        isRound2Setup = true
    }
    fun setupRound3(){
        communicator?.setBet(lobbyStr, 0L)
        communicator?.setNumPlayersChecked(lobbyStr, 0)
        communicator?.setCurrentActivePlayer(lobbyStr, 0)
        setCardsRound3()
        gameState = GameState.RUNNING
        isRound3Setup = true
    }

    fun setCardsRound1() {
        communicator?.setCard1(lobbyStr, table.sharedDeck[0].cardID.toLong())
        communicator?.setCard2(lobbyStr, table.sharedDeck[1].cardID.toLong())
        communicator?.setCard3(lobbyStr, table.sharedDeck[2].cardID.toLong())
    }

    fun setCardsRound2() {
        communicator?.setCard4(lobbyStr, table.sharedDeck[3].cardID.toLong())
    }

    fun setCardsRound3() {
        communicator?.setCard5(lobbyStr, table.sharedDeck[4].cardID.toLong())
    }

    fun isTurn(): Boolean{
        return gameVals.getTurnNumber() == gameVals.getCurrentActivePlayer().toInt()
    }

    fun isFolded(): Boolean{
        return !gameVals.getIsStillIn()
    }

    fun isShowdown(): Boolean {
        return gameState == GameState.SHOWDOWN
    }

    fun haveAllPlayersChecked(): Boolean{
        return gameVals.getNumPlayersChecked() == gameVals.playerList.size
    }
    /*
    fun increaseCurrentActivePlayer(){
        var temp = gameVals.getCurrentActivePlayer()
        temp = (temp + 1) % gameVals.getNumPlayers()
        if(temp == 0L) {
            temp += 1
            communicator?.setCurrBetCycle( lobbyStr, gameVals.getCurrBetCycle() + 1L)
        }
        communicator?.setCurrentActivePlayer(lobbyStr, temp + 1)
    }
    */
    fun increaseCurrentActivePlayer(){
        var temp = gameVals.getCurrentActivePlayer()
        communicator?.setCurrentActivePlayer(lobbyStr, (temp + 1) % gameVals.getNumPlayers())
    }

    suspend fun showdownOnline(): Int = coroutineScope {
        var playersStillIn = mutableListOf<Player>()
        var handValues = mutableListOf<Int>()

        val taskResult = getPlayersStillIn(playersStillIn)

        for (player in playersStillIn) {
            handValues.add(dealer.checkHand.bestHand(player.hand, table.sharedDeck))
        }

        return@coroutineScope handValues.maxOrNull()!!
    }

    // TODO: Underconstruction
    suspend fun getPlayersStillIn(list: MutableList<Player>)  = coroutineScope {
        // var list = mutableListOf<Player>()
        var usernames = mutableListOf<String>()

        val dbref = Firebase.database.getReference(lobbyStr).get().await()
        val test = dbref.child("ActiveUsers").children

        var count = 0
        test.forEach {
            var isStillIn = it.child("IsStillIn").value as Boolean
            if (isStillIn)
                count++
            usernames.add(it.child("username").value.toString())
            print(1)
        }
        for (name in usernames) {
            for (player in table.playerArray) {
                if (name == player.name)
                    list.add(player)
            }
        }
    }

    // TODO: Determine the winner among the remaining players, and distribute the pot accordingingly
    // In the case of a tie, just split the pot.
    fun determineWinner(): Player {
        var list = mutableListOf<Int>()
        for (player in table.playersStillIn) {
            list.add(dealer.checkHand.bestHand(player.hand, table.sharedDeck))
        }

        // Get player with the highest hand value
        val test = list.maxOrNull()
        val index = list.indexOf(list.maxOrNull())

        return table.playersStillIn[index]
    }
}