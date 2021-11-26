package com.example.yeehawholdem.LogicGoods

// TODO: Add bet and fold funcitons?

data class Player(
    var name: String = "",
    var balance: Int = 0,
    //Pierce - traced the logic and saw that we couldn't change this, so we will have 2 ID's
    var playerFirebaseId: String = "",
    var playerID: Int = 0,
    var hand: MutableList<Card> = mutableListOf(),
) {
    var isStillIn = true
    var checkFlag = false
    var isMyTurn = false
    var handValue = 0
    //var currBet = 0

    fun getHighCard() : Int {
        return hand.maxByOrNull { it.value }!!.value
    }

    fun acceptCard() {
        // TODO()
    }
}
