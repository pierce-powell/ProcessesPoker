package com.example.yeehawholdem.LogicGoods

import com.example.yeehawholdem.GameBoardGoods.STARTING_BALANCE
import com.example.yeehawholdem.OnlineGameGoods.GameState

class GameOffline {
    // var dealer = Dealer()
    var dealer_player = Player(name = "Dealer Player 2")
    var player = Player(name = "Test Player 1")
    var table = Table()
    var gameState = GameState.RUNNING

    init {
        startGame()
    }

    // Starts the a 2-Player game of Texas Hold-Em
    fun startGame() {
        addPlayer(player)
        addPlayer(dealer_player)
        table.resetPlayers()
        table.setupDeck()
        for (player in table.playersStillIn) {
            table.dealer.dealCard(player)
            table.dealer.dealCard(player)
        }
        table.dealer.cardCount = 3
        for (p in table.playerArray) {
            p.balance = STARTING_BALANCE
        }
        gameState = GameState.RUNNING
    }

    fun addPlayer(_player : Player) {
        table.playerArray.add(_player)
    }

    // Since the dealer will always match the player's bet, add double the
    // player's bet to the pot.
    fun betting(userBet: Int = 0) {
        player.balance -= userBet
        player.checkFlag = true
        table.addToPot(userBet * 2)
        gameState = GameState.NEXTROUND
    }

    fun nextRound() {
        gameState = GameState.BETORCHECK
        table.dealer.cardCount++
        table.resetCheck()

        if (table.dealer.cardCount >= 5)
            gameState = GameState.SHOWDOWN
    }

    fun showdown() : String {
        val player = determineWinner()
        player.balance += table.currentPot
        gameState = GameState.STOPPED
        return player.name
    }

    // TODO: Tie-breaker/Splitting the pot?
    fun determineWinner() : Player {
        return table.playerWithHighestHand()
    }

    fun nextGame() {
        table.resetPlayers()
        table.resetHands()
        table.resetCheck()
        table.currentPot = 0
        table.setupDeck()
        table.dealAllCards()
        table.dealer.cardCount = 3
        gameState = GameState.RUNNING
    }
}