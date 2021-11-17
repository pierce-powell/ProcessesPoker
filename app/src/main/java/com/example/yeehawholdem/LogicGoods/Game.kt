package com.example.yeehawholdem.LogicGoods

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.yeehawholdem.GameBoardGoods.BIG_BLIND
import com.example.yeehawholdem.GameBoardGoods.SMALL_BLIND
import com.example.yeehawholdem.GameBoardGoods.STARTING_BALANCE

enum class GameState {
    STOPPED, RUNNING, BETORCHECK, SHOWDOWN, NEXTGAME, NEXTROUND, RAISING, AIPLAYER
}

class Game {
    var dealer = Dealer()
    var dealer_player = Player(name = "Dealer Player 1")
    var player = Player(name = "Test Player 1")
    var table = Table()
    var checkHand = CheckHand()
    var gameState by mutableStateOf(GameState.RUNNING)
    var dealerButton = 0
    var turn = 0

    fun startGame() {
        // For Offline Play
        addPlayer(player)
        addPlayer(dealer_player)
        table.resetPlayers()
        dealer.setupDeck(table = table)
        dealer.dealCard(player)
        dealer.dealCard(player)

        // table.sharedDeck = mutableListOf<Card>(Card(0), Card(13), Card(26), Card(1), Card(14))
        // checkHand.currentHand = table.sharedDeck
        for (p in table.playerArray) {
            p.balance = STARTING_BALANCE
        }

        // Do Small and Big Blind bets
        table.addToPot(SMALL_BLIND)
        table.playerArray.getOrNull(dealerButton + 1 % table.playerArray.size)!!.balance -= SMALL_BLIND
        table.addToPot(BIG_BLIND)
        table.playerArray.getOrNull(dealerButton + 2 % table.playerArray.size)!!.balance -= BIG_BLIND

        gameState = GameState.RUNNING

        // roundStart()
        // theFlop()
        // theRiver()
        // dealerButton++
    }

    fun addPlayer(_player : Player) {
        table.playerArray.add(_player)
    }

    fun removePlayer(_player : Player) {
        table.playerArray.remove(_player)
    }

    fun roundStart() {
        for (_player in table.playerArray) {
            table.playersStillIn.add(_player)
        }
        //
        checkCalled()
    }

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

    fun betting(userBet: Int = 0) {
        while (table.checkCalled()) {
            // Keep betting until everyone has called or folded
            val player = table.playersStillIn[turn]
            if (player.name == "Dealer Player 1") {
                // AI Player logic for betting
                dealer.aiBetOrFold(player)
            }
            player.balance -= userBet
            player.checkFlag = true
            table.addToPot(userBet.toFloat())
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
    }

    fun showdown() {
        // TODO()
        gameState = GameState.NEXTGAME
    }

    fun nextGame() {
        table.resetPlayers()
        table.resetHands()
        dealer.setupDeck(table = table)
        dealer.dealCard(player)
        dealer.dealCard(player)
        dealerButton = (dealerButton + 1) % table.playerArray.size
        turn = dealerButton
    }

    fun incrementTurn() {
        turn = (turn + 1) % table.playerArray.size
    }
}