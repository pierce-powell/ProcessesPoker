package com.example.yeehawholdem.LogicGoods

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
    var handValue = 0
}
