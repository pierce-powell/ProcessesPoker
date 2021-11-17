package com.example.yeehawholdem.LogicGoods

class Dealer {
    var deckConst: MutableList<Card> = mutableListOf()
    var usableDeck: MutableList<Card> = mutableListOf()
    var cardCount: Int = 0
    var whosNext: Int = 0
    var checkHand = CheckHand()

    private fun shuffleUsable() {
        usableDeck.shuffle()
    }

    fun dealCard(player: Player?) {
        // TODO: Change to also work with the table's sharedDeck?
        usableDeck.getOrNull(0)?.let { player?.hand?.add(it) }
        usableDeck.removeAt(0)
    }

    fun increaseCardCount() {
        TODO()
    }

    // Idea: Get player from turn order or as parameter passed in?
    fun promptPlayer() {
        TODO()
    }

    fun determineWinner() {
        TODO()
    }

    fun manageTurns() {
        TODO()
    }

    fun setupDeck(table: Table?) {
        usableDeck.clear()
        table!!.sharedDeck.clear()
        for (i in 0..51) {
            usableDeck.add(Card(i))
            shuffleUsable()
        }

        for (i in 0..4) {
            usableDeck.getOrNull(0)?.let { table.sharedDeck.add(it) }
            usableDeck.removeAt(0)
        }
    }

    fun aiBetOrFold(player: Player) {
        checkHand.currentHand = mutableListOf()
        checkHand.currentHand.addAll(player.hand)
        checkHand.currentHand.addAll(player.hand)
    }
}