package com.example.yeehawholdem.LogicGoods

import kotlinx.coroutines.CoroutineScope

class Table {
    var playerArray: MutableList<Player> = mutableListOf()
    var playersStillIn: MutableList<Player> = mutableListOf()
    var sharedDeck: MutableList<Card> = mutableListOf() // Community Cards
    var currentPot: Int = 0
    val dealer = Dealer()

    /*
    init {
    }
     */

    fun getPlayerID(player: Int): Int {
        return playerArray.getOrNull(player)!!.playerID
    }


    fun addCardToSharedDeck(card: Card){
        try {
            sharedDeck.add(card)
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }

    fun addToPot(amount: Int) {
        try {
            if (amount >= 0) currentPot += amount
            else throw IllegalArgumentException("Amount must be greater than 0")
        } catch (e: IllegalArgumentException) {
            // TODO: handle exception
        }
    }

    // Returns true if all players have checked or called
    fun checkCalled() : Boolean {
        val list = playersStillIn.distinctBy { it.checkFlag }
        if (checkPlayersStillIn()) {
            if (!(list.size != 1 || !list[0].checkFlag))
                return true
        }

        return false
    }

    // Returns true if Atleast 2 players are still in
    fun checkPlayersStillIn() : Boolean {
        val list = playersStillIn.distinctBy { it.isStillIn }
        if (!(list.size != 1 || !list[0].isStillIn))
            return true

        return false
    }

    fun resetPlayers() {
        playersStillIn.clear()
        for (p in playerArray) {
            p.isStillIn = true
            playersStillIn.add(p)
            p.hand.clear()
        }
    }

    fun resetCheck() {
        for (p in playerArray) {
            p.checkFlag = false
        }
    }

    fun resetHands() {
        for (p in playerArray) {
            p.hand.clear()
        }
    }

    // Deal two cards to all players
    fun dealAllCards() {
        for (player in playersStillIn) {
            dealer.dealCard(player)
            dealer.dealCard(player)
        }
    }

    // Setup the full deck and user deck
    fun setupDeck () {
        sharedDeck.clear()
        dealer.setupUsableDeck()
        for (i in 0..4) {
            dealer.usableDeck.getOrNull(0)?.let { sharedDeck.add(it) }
            dealer.usableDeck.removeFirstOrNull()
        }
    }

    // Returns the player with the highest hand value
    // TODO: Detect if there is a tie. Return multiple players?
    fun playerWithHighestHand(): Player {
        var list = mutableListOf<Int>()
        for (player in playersStillIn) {
            list.add(dealer.checkHand.bestHand(player.hand, sharedDeck))
        }
        // Get player with the highest hand value
        val index = list.indexOf(list.maxOrNull())
        return playersStillIn[index]
    }
}