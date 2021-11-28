package com.example.yeehawholdem.OnlineGameGoods

import com.example.yeehawholdem.LogicGoods.Dealer
import com.example.yeehawholdem.LogicGoods.GameValues
import com.example.yeehawholdem.LogicGoods.Player
import com.example.yeehawholdem.LogicGoods.Table
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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
    (gameVa: GameValues, var communicator: Communications, var lobbyStr: String) {
    var dealer = Dealer()
    var table = Table()
    var gameState = GameState.STOPPED
    var gameVals: GameValues = gameVa
    var isRound1Setup = false
    var isRound2Setup = false
    var isRound3Setup = false


    //Shuffles the deck of cards, deals a set of two cards to each player, and sets the game state
    //running
    fun startGame() {
        table.setupDeck()
        createPlayersList() // set up table.playerArray
        table.dealAllCards()

        // get all the states, and then set up the listeners
        // HotFix for numplayers
        communicator.setNumPlayers(lobbyStr, table.playerArray.size.toLong())

        // Update the values on the database (player hands, CurrentActivePlayer
        isRound1Setup = false
        isRound2Setup = false
        isRound3Setup = false
        communicator.setPot(lobbyStr, 0)
        communicator.setPlayerHands(lobbyStr, table.playerArray)
        communicator.setIsGameInProgress(lobbyStr, true) // Set Game to in Progress setPlayerTurnNumber
        communicator.setShowWinner(lobbyStr, false)
        communicator.setPlayerTurnNumber(lobbyStr, table.playerArray)
        communicator.setDidYaWin(lobbyStr, table.playerArray)
        communicator.resetHighestBet(lobbyStr, table.playerArray)
        communicator.setPlayerIsStillIn(lobbyStr, table.playerArray)
        communicator.setBet(lobbyStr, 0)
        communicator.setCurrentActivePlayer(lobbyStr, 0)
        communicator.setCard1(lobbyStr, -1)
        communicator.setCard2(lobbyStr, -1)
        communicator.setCard3(lobbyStr, -1)
        communicator.setCard4(lobbyStr, -1)
        communicator.setCard5(lobbyStr, -1) //
        Firebase.database.reference.child(lobbyStr).child("Winners").removeValue()

        gameState = GameState.RUNNING
    }

    fun createPlayersList() {
        table.playerArray.clear()
        table.playerArray.addAll(gameVals.playerList)
        table.resetPlayers()
    }

    // Change the suitable flags in the Database to allow the current player to bet/raise/fold
    // The pot and balance are handled on the player's side
    // Increase the CurrentActivePlayer
    fun changeState() {
        gameState = if(!isRound1Setup){
            GameState.ROUND1
        } else if(!isRound2Setup){
            GameState.ROUND2
        } else if(!isRound3Setup){
            GameState.ROUND3
        } else{
            GameState.SHOWDOWN
        }
        communicator.resetHighestBet(lobbyStr, table.playerArray)
        communicator.setNumPlayersChecked(lobbyStr, 0)
    }

    fun setupRound1(){
        communicator.setBet(lobbyStr, 0L)
        communicator.setNumPlayersChecked(lobbyStr, 0)
        communicator.setCurrentActivePlayer(lobbyStr, 0)
        setCardsRound1()
        gameState = GameState.RUNNING
        isRound1Setup = true
    }
    fun setupRound2(){
        communicator.setBet(lobbyStr, 0L)
        communicator.setNumPlayersChecked(lobbyStr, 0)
        communicator.setCurrentActivePlayer(lobbyStr, 0)
        setCardsRound2()
        gameState = GameState.RUNNING
        isRound2Setup = true
    }
    fun setupRound3(){
        communicator.setBet(lobbyStr, 0L)
        communicator.setNumPlayersChecked(lobbyStr, 0)
        communicator.setCurrentActivePlayer(lobbyStr, 0)
        setCardsRound3()
        gameState = GameState.RUNNING
        isRound3Setup = true
    }

    fun setCardsRound1() {
        communicator.setCard1(lobbyStr, table.sharedDeck[0].cardID.toLong())
        communicator.setCard2(lobbyStr, table.sharedDeck[1].cardID.toLong())
        communicator.setCard3(lobbyStr, table.sharedDeck[2].cardID.toLong())
    }

    fun setCardsRound2() {
        communicator.setCard4(lobbyStr, table.sharedDeck[3].cardID.toLong())
    }

    fun setCardsRound3() {
        communicator.setCard5(lobbyStr, table.sharedDeck[4].cardID.toLong())
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
        return gameVals.getNumPlayersChecked() >= gameVals.playerList.size
    }

    fun increaseCurrentActivePlayer(){
        val temp = gameVals.getCurrentActivePlayer()
        communicator.setCurrentActivePlayer(lobbyStr, (temp + 1) % gameVals.getNumPlayers())
    }

    fun haveAllPlayersFolded(): Boolean{
        var numPlayersStillIn = 0
        for(i in gameVals.playerList){
            if(i.isStillIn){
                numPlayersStillIn += 1
            }
            if(numPlayersStillIn > 1){
                return false
            }
        }

        return true
    }

    fun showdownOnline(){
        // val handValues = mutableListOf<Int>()
        val winners = determineWinnerOnline()
        var i = 0

        // distribute pot according to the winners
        for (player in winners) {
            val winnings = gameVals.getPot() / winners.size
            val name = player.playerFirebaseId
            val balance = gameVals.playerList.filter { it.playerFirebaseId == name }[0].balance
            Firebase.database.reference.child(lobbyStr).child("ActiveUsers").child(player.playerFirebaseId).child("balance").setValue(winnings + balance)
            Firebase.database.reference.child(player.playerFirebaseId).child("balance").setValue(winnings + balance)
            Firebase.database.reference.child(lobbyStr).child("ActiveUsers").child(player.playerFirebaseId).child("DidYaWin").setValue(true)
            Firebase.database.reference.child(lobbyStr).child("NumWinner").setValue(i + 1)
            Firebase.database.reference.child(lobbyStr).child("Winners").child(i++.toString()).setValue(player.name)
        }

        gameState = GameState.STOPPED

        communicator.setIsGameInProgress(lobbyStr, false)
        communicator.setShowWinner(lobbyStr, true)

        // return handValues.maxOrNull()!!
    }

    fun determineWinnerOnline(): List<Player> {
        val list = mutableListOf<Int>()
        val playerList = table.playersStillIn
        table.playersStillIn.clear()
        for (player in gameVals.playersStillIn) {
            for (p in table.playerArray) {
                if (player.name == p.name) {
                    if (player.isStillIn) {
                        table.playersStillIn.add(p)
                    }
                }
            }
        }

        for (i in playerList.indices) {
            val player = playerList[i]
            val handValue = dealer.checkHand.bestHand(player.hand, table.sharedDeck)
            list.add(handValue)
            playerList[i].handValue = handValue
        }
        // Get player(s) with the highest hand value
        val highestVal = list.maxOrNull()
        return playerList.filter { it.handValue == highestVal }
    }

    fun getCurrentActiveUsername(): String{
        return if (table.playerArray.size > 0) {
            table.playerArray[gameVals.getCurrentActivePlayer().toInt()].name
        } else {
            ""
        }
    }
}