package com.example.yeehawholdem.LogicGoods

import com.example.yeehawholdem.GameBoardGoods.STARTING_BALANCE

class GameOffline {
    var dealer = Dealer()
    var dealer_player = Player(name = "Dealer Player 2")
    var player = Player(name = "Test Player 1")
    var table = Table()
    var gameState = GameState.RUNNING
    var dealerButton = 0
    var turn = 0

    init {
        startGame()
    }

    fun startGame() {
        // For Offline Play
        addPlayer(player)
        addPlayer(dealer_player)
        table.resetPlayers()
        dealer.setupDeck(table = table)

        for (player in table.playersStillIn) {
            dealer.dealCard(player)
            dealer.dealCard(player)
        }

        dealer.cardCount = 3

        // table.sharedDeck = mutableListOf<Card>(Card(0), Card(13), Card(26), Card(1), Card(14))
        // dealer.checkHand.currentHand = table.sharedDeck
        for (p in table.playerArray) {
            p.balance = STARTING_BALANCE
        }

        // Do Small and Big Blind bets
        // table.addToPot(SMALL_BLIND)
        //table.playerArray.getOrNull(dealerButton + 1 % table.playerArray.size)!!.balance -= SMALL_BLIND
        // table.addToPot(BIG_BLIND)
        // table.playerArray.getOrNull(dealerButton + 2 % table.playerArray.size)!!.balance -= BIG_BLIND

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

    // TODO: Logic to prevent player from overbetting
    fun betting(userBet: Int = 0) {
        /*var bet = userBet
        val player = table.playersStillIn[turn]

        if (player.name == "Dealer Player 2") {
            // AI Player logic for betting
            bet = userBet
            table.addToPot(userBet)
            // Calculate difference to subtract from balance
        }*/

        player.balance -= userBet
        player.checkFlag = true
        table.addToPot(userBet * 2)
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
        dealer.setupDeck(table = table)
        dealer.dealCard(player)
        dealer.dealCard(player)
        dealer.cardCount = 3
        dealerButton = (dealerButton + 1) % table.playerArray.size
        turn = dealerButton
        gameState = GameState.RUNNING
    }

    fun incrementTurn() {
        turn = (turn + 1) % table.playerArray.size
    }
}