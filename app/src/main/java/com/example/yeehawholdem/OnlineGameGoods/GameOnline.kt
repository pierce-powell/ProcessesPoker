package com.example.yeehawholdem.LogicGoods

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.yeehawholdem.GameBoardGoods.STARTING_BALANCE
import com.example.yeehawholdem.OnlineGameGoods.Communications
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay

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

class Game {
    var dealer = Dealer()
    var table = Table()
    var gameState = GameState.STARTGAME
    var dealerButton = 0
    var turn = 0
    var gameVals: GameValues
    var communicator: Communications? = null
    var lobbyStr: String = ""
    var isRound1Setup = false
    var isRound2Setup = false
    var isRound3Setup = false


    //When creating a Game object, initialize with list of players for the game
    constructor(gameVa: GameValues, communicator: Communications, lobbyStr: String) {
        this.gameVals = gameVa
        this.communicator = communicator
        //this.lobbyStr = lobbyStr
        this.lobbyStr = "Lobby1"
    }

    init {
        // Probably want a startGame button to call the function from the GameBoard screen.
        // startGame()
    }

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

        // Update the values on the database (player hands, CurrentActivePlayer
        communicator?.setPlayerHands(lobbyStr, table.playerArray)
        communicator?.setIsGameInProgress(lobbyStr, true) // Set Game to in Progress
        communicator?.setCurrentActivePlayer(lobbyStr, 1)

        gameState = GameState.RUNNING
    }

    fun createPlayersList() {
        table.playerArray = gameVals?.playerList ?: table.playerArray
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
        communicator?.setCard1(lobbyStr, table.dealer.dealCard())
        communicator?.setCard2(lobbyStr, table.dealer.dealCard())
        communicator?.setCard3(lobbyStr, table.dealer.dealCard())
    }

    fun setCardsRound2() {
        communicator?.setCard4(lobbyStr, table.dealer.dealCard())
    }

    fun setCardsRound3() {
        communicator?.setCard5(lobbyStr, table.dealer.dealCard())
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

    fun increaseCurrentActivePlayer(){
        var temp = gameVals.getCurrentActivePlayer()
        temp = (temp + 1) % gameVals.playerList.size
        if(temp == 0L) {
            temp += 1
            communicator?.setCurrBetCycle( lobbyStr, gameVals.getCurrBetCycle() + 1L)
        }
        communicator?.setCurrentActivePlayer(lobbyStr, temp + 1)
    }

    // Card 4
    fun theFlop() {
        // TODO()
    }

    // Card 5
    fun theRiver() {
        // TODO()
    }

    // Checks if at least 2 players are still in and if everyone has checked/called
    fun checkCalled() {
        if (table.checkCalled()) {
            //gameState = GameState.NEXTROUND
        } else {
            //gameState = GameState.BETORCHECK
        }
    }

    fun checkPlayersStillIn(): Boolean {
        return table.checkPlayersStillIn()
    }

    fun raise() {

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
        //gameState = GameState.NEXTROUND
    }

    fun fold() {
        table.playerArray[turn].isStillIn = false
        incrementTurn()
    }

    fun nextRound() {
        turn = dealerButton; incrementTurn(); incrementTurn()
        //gameState = GameState.BETORCHECK
        dealer.cardCount++
        table.resetCheck()

        if (dealer.cardCount >= 5)
            gameState = GameState.SHOWDOWN
    }

    fun showdownOnline(){

    }

    fun showdown(): String {
        var player = determineWinner()
        player.balance += table.currentPot
        //gameState = GameState.NEXTGAME
        return player.name
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