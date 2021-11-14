package com.example.yeehawholdem.LogicGoods

class Dealer {
    var deckConst: MutableList<Card>? = null
    var usableDeck: MutableList<Card>? = null
    var cardCount: Int = 0
    var whosNext: Int = 0

    init {
        deckConst = mutableListOf()
        usableDeck = mutableListOf()
        for (i in 0..51) {
            usableDeck?.add(Card(i))
            shuffleUsable()
        }
    }

    private fun shuffleUsable() {
        // TODO: Put in Try-Catch block?
        usableDeck?.shuffle()
    }

    fun dealCard(player: Player?) {
        // TODO()
        usableDeck?.getOrNull(0)?.let { player?.hand?.add(it) }
        usableDeck?.removeAt(0)
    }

    fun increaseCardCount() {
        TODO()
    }

    fun promptPlayer() {
        TODO()
    }

    fun determineWinner() {
        TODO()
    }

    fun manageTurns() {
        TODO()
    }

    fun setupTable(table: Table?) {
        for (i in 0..4) {
            usableDeck?.getOrNull(0)?.let { table?.sharedDeck?.add(it) }
            usableDeck?.removeAt(0)
        }
    }
}