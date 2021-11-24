package com.example.yeehawholdem.LogicGoods

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.yeehawholdem.GameBoardGoods.STARTING_BALANCE
import com.example.yeehawholdem.OnlineGameGoods.Communications
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


enum class GameState {
    //Start, firstRound, SecondRound, ThirdRound, Showdown, End
    STOPPED, RUNNING, BETORCHECK, SHOWDOWN, NEXTGAME, NEXTROUND
}

class Game {
    var dealer = Dealer()
    var table = Table()
    var gameState = GameState.RUNNING
    var dealerButton = 0
    var turn = 0
    var gameVals: GameValues? = null
    var communicator: Communications? = null
    var lobbyStr: String = ""
    private var mDatabase: DatabaseReference? = null
    private var lobbyCloudEndpoint: DatabaseReference? = null
    private var betCloudEndpoint: DatabaseReference? = null


    //When creating a Game object, initialize with list of players for the game
    constructor(gameVals: GameValues, communicator: Communications, lobbyStr: String) {
        this.gameVals = gameVals
        this.communicator = communicator
        //this.lobbyStr = lobbyStr
        this.lobbyStr = "Lobby1"
    }

    init {
        startGame()
    }

    //Shuffles the deck of cards, deals a set of two cards to each player, and sets the game state
    //running
    fun startGame() {
        table.setupDeck()
        table.dealAllCards()
        //TODO: first player = host
        //TODO: Send all players in waitlist to active users
        //TODO: Populate database.activeUsers hands with cards if host

        // get all the states, and then set up the listeners
        gameState = GameState.RUNNING
    }

    fun round() {
        for (_player in table.playerArray) {
            table.playersStillIn.add(_player)
        }

        checkCalled()
    }

    fun setCardsRound1(){
        communicator?.setCard1(lobbyStr, table.dealer.dealCard())
        communicator?.setCard2(lobbyStr, table.dealer.dealCard())
        communicator?.setCard3(lobbyStr, table.dealer.dealCard())
    }

    fun setCardsRound2(){
        communicator?.setCard4(lobbyStr, table.dealer.dealCard())
    }

    fun setCardsRound3(){
        communicator?.setCard5(lobbyStr, table.dealer.dealCard())
    }

    // fun playRound(){

    //}

    /*
    fun roundOfBetting() {
        while (table.checkCalled()) {
            // Keep betting until everyone has called or folded
            table.nextBet(turn, 5)
            turn++
        }
        turn = dealerButton + 1
        gameState = GameState.CHECKING
    }
    */

    // Card 4
    fun theFlop(){
        // TODO()
    }

    // Card 5
    fun theRiver(){
        // TODO()
    }

    // Checks if at least 2 players are still in and if everyone has checked/called
    fun checkCalled() {
        if (table.checkCalled()) {
            gameState = GameState.NEXTROUND
        } else {
            gameState = GameState.BETORCHECK
        }
    }

    fun checkPlayersStillIn() : Boolean {
        return table.checkPlayersStillIn()
    }

    fun raising() {
        for (i in 0..table.playersStillIn.size) {
            table.playersStillIn[i].checkFlag = false
        }
        table.playersStillIn[turn].checkFlag = true
        incrementTurn()
    }

    // TODO: Logic to prevent player from overbetting
    fun betting(userBet: Int = 0) {
        var bet = userBet

        while (!table.checkCalled()) {
            // Keep betting until everyone has called or folded
            val player = table.playersStillIn[turn]
            player.balance -= userBet
            player.checkFlag = true
            table.addToPot(bet)
            incrementTurn()
        }
        incrementTurn()
        gameState = GameState.NEXTROUND
    }

    fun fold() {
        table.playerArray[turn].isStillIn = false
        incrementTurn()
    }

    fun nextRound() {
        turn = dealerButton; incrementTurn(); incrementTurn()
        gameState = GameState.BETORCHECK
        dealer.cardCount++
        table.resetCheck()

        if (dealer.cardCount >= 5)
            gameState = GameState.SHOWDOWN
    }

    fun showdown() : String {
        var player = determineWinner()
        player.balance += table.currentPot
        gameState = GameState.NEXTGAME
        return player.name
    }

    // TODO: Determine the winner among the remaining players, and distribute the pot accordingingly
    // In the case of a tie, just split the pot.
    fun determineWinner() : Player {
        var list = mutableListOf<Int>()
        for (player in table.playersStillIn) {
            list.add(dealer.checkHand.bestHand(player.hand, table.sharedDeck))
        }

        // Get player with the highest hand value
        val test = list.maxOrNull()
        val index = list.indexOf(list.maxOrNull())



        return table.playersStillIn[index]
    }

    fun nextGame() {
        table.resetPlayers()
        table.resetHands()
        table.resetCheck()
        table.currentPot = 0
        table.setupDeck()
        dealer.cardCount = 3
        dealerButton = (dealerButton + 1) % table.playerArray.size
        turn = dealerButton
        gameState = GameState.RUNNING
    }

    fun incrementTurn() {
        turn = (turn + 1) % table.playerArray.size
    }

}