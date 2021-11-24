package com.example.yeehawholdem.LogicGoods

class Dealer {
    var deckConst: MutableList<Card> = mutableListOf()
    var usableDeck: MutableList<Card> = mutableListOf()
    var checkHand = CheckHand()
    var cardCount = 0

    fun shuffleUsable() {
        usableDeck.shuffle()
    }

    fun dealCard(player: Player?) {
        usableDeck.getOrNull(0)?.let { player?.hand?.add(it) }
        usableDeck.removeFirstOrNull()
    }

    fun dealCard(): Long{
        var card = -1
        usableDeck.getOrNull(0)?.let { card = it.getCardValue()}
        usableDeck.removeFirstOrNull()
        return card.toLong()
    }

    // Resets and shuffles the full deck
    fun setupUsableDeck() {
        usableDeck.clear()
        for (i in 0..51) {
            usableDeck.add(Card(i))
        }
        shuffleUsable()
    }

    /*
    // Betting decisionmaker for the AI player.
    // Return an integer 0 for fold, and greater than for a bet.
    // TODO: Logic to decide amount to bet (Maybe random)
    fun aiBetOrFold(player: Player, minimumBet: Int) : Int {
        checkHand.currentHand = mutableListOf()
        checkHand.currentHand.addAll(player.hand)
        checkHand.currentHand.addAll(player.hand)

        return 0
    }
    */
}