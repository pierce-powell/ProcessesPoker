package com.example.yeehawholdem.LogicGoods

data class Player(
    var name: String? = null,
    var balance: Float = 0f,
    var playerID: Int = 0,
    var hand: MutableList<Card>? = null,
) {
    var isStillIn: Boolean? = null //TODO()

    init {
        hand = mutableListOf<Card>()
    }

    fun getHighCard() {
        // TODO()
        hand?.maxByOrNull { it.value }
    }

    fun acceptCard() {
        TODO()
    }
}
