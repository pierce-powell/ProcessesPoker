package com.example.yeehawholdem.LogicGoods

class Dealer {
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

    // Resets and shuffles the full deck
    fun setupUsableDeck() {
        usableDeck.clear()
        for (i in 0..51) {
            usableDeck.add(Card(i))
        }
        shuffleUsable()
    }
}