package com.example.yeehawholdem.LogicGoods

class Table {
    var playerArray: MutableList<Player>? = null
    var playersStillIn: MutableList<Player>? = null
    var sharedDeck: MutableList<Card>? = null // Community Cards
    var currentPot: Int = 0

    init {
        playerArray = mutableListOf()
        playersStillIn = mutableListOf()
        sharedDeck = mutableListOf()
    }

    fun getPlayerID(player: Int): Int? {
        // TODO()
        return playerArray?.getOrNull(player)?.playerID
    }

    fun addCardToSharedDeck(card: Card){
        try {
            sharedDeck?.add(card)
        } catch (e: Exception) {
            TODO()
        }
    }

    fun addToPot(amount: Int) {
        try {
            if (amount > 0) currentPot += amount
            else throw IllegalArgumentException("Amount must be greater than 0")
        } catch (e: IllegalArgumentException) {
            TODO()
        }

    }
}