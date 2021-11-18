package com.example.yeehawholdem.LogicGoods

class Dealer {
    var deckConst: MutableList<Card> = mutableListOf()
    var usableDeck: MutableList<Card> = mutableListOf()
    var cardCount: Int = 0
    var whosNext: Int = 0
    var checkHand = CheckHand()

    private fun shuffleUsable() {
        usableDeck?.shuffle()
    }

    fun dealCard(player: Player?) {
        // TODO: Change to also work with the table's sharedDeck?
        usableDeck?.getOrNull(0)?.let { player?.hand?.add(it) }
        usableDeck?.removeAt(0)
    }

    fun increaseCardCount() {
        TODO()
    }

    // Idea: Get player from turn order or as parameter passed in?
    fun promptPlayer() {
        TODO()
    }

    fun determineWinner() {
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

    // Betting decisionmaker for the AI player.
    // Return an integer 0 for fold, and greater than for a bet.
    // TODO: Logic to decide amount to bet (Maybe random)
    fun aiBetOrFold(player: Player, minimumBet: Int) : Int {
        checkHand.currentHand = mutableListOf()
        checkHand.currentHand.addAll(player.hand)
        checkHand.currentHand.addAll(player.hand)

        return 0
    }
}