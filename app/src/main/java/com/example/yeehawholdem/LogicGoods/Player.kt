package com.example.yeehawholdem.LogicGoods

// TODO: Add bet and fold funcitons?

data class Player(
    var name: String = "",
    var balance: Float = 0f,
    var playerID: Int = 0,
    var hand: MutableList<Card> = mutableListOf(),
) {
    var isStillIn = true
    var checkFlag = false

    fun getHighCard() : Int {
        return hand.maxByOrNull { it.value }!!.value
    }

    fun acceptCard() {
        // TODO()
    }
}
