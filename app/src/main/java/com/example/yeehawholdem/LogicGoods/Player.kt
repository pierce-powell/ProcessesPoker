package com.example.yeehawholdem.LogicGoods

// TODO: Add bet and fold funcitons?

data class Player(
    var name: String? = null,
    var balance: Float = 0f,
    var playerID: Int = 0,
    var hand: MutableList<Card>? = null,
) {
    var isStillIn: Boolean? = null

    init {
        hand = mutableListOf<Card>()
    }

    fun getHighCard() : Int {
        return hand?.maxByOrNull { it.value }!!.value
    }

    fun acceptCard() {
        // TODO()
    }
}
