package com.example.yeehawholdem.Testing

import com.example.yeehawholdem.LogicGoods.Game
import junit.framework.TestCase
import org.junit.Test

class GameTest : TestCase() {
    val game = Game()

    init {

    }

    @Test
    fun testbetting() {
        println(game.table.currentPot)
        game.betting(50)
        game.nextRound()
        println(game.table.currentPot)
        game.betting(50)
        game.nextRound()
        println(game.table.currentPot)

    }

    @Test
    fun stbetting() {
        println(game.table.currentPot)
        game.betting(50)
        game.nextRound()
        println(game.table.currentPot)
        game.betting(50)
        game.nextRound()
        println(game.table.currentPot)

    }
}