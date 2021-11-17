package com.example.yeehawholdem.LogicGoods

class Table {
    var playerArray: MutableList<Player> = mutableListOf()
    var playersStillIn: MutableList<Player> = mutableListOf()
    var sharedDeck: MutableList<Card> = mutableListOf() // Community Cards
    var currentPot: Float = 0f

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

    fun addToPot(amount: Float) {
        try {
            if (amount > 0) currentPot += amount
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

    /*
    fun raising(turn : Int) {
        for (i in 0..playersStillIn.size) {
            playersStillIn[i].checkFlag = false
        }
        playersStillIn[turn].checkFlag = true
    }

    fun nextBet(turn: Int, userBet: Int = 0) {
        // Prompt this person to raise, call, or fold
        // TODO: Put in guard statements
        val player = playersStillIn[turn]
        if (player.name == "Dealer Player 1") {
            // AI Player logic

        }
        playersStillIn[turn].balance -= userBet
        addToPot(userBet.toFloat())
    }
    */

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
}