package com.example.yeehawholdem.Testing

import com.example.yeehawholdem.LogicGoods.Card
import com.example.yeehawholdem.LogicGoods.GameOffline
import junit.framework.TestCase

class GameOfflineTest : TestCase() {
    val game = GameOffline()

    init {
        game.table.sharedDeck[0] = Card(12)
        game.table.sharedDeck[1] = Card(11)
        game.table.sharedDeck[2] = Card(10)
        game.table.sharedDeck[3] = Card(9)
        game.table.sharedDeck[4] = Card(8)
    }

    fun testDetermineWinner() {
        val p = game.determineWinner()
        print("\n" + p + "\n")
    }
}