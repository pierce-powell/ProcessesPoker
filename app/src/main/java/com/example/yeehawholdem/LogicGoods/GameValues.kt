package com.example.yeehawholdem.LogicGoods

class GameValues {
    private var bet: Int = 0
    private var pot: Int = 0

    @JvmName("getBet1")
    fun getBet(): Int { return bet}

    @JvmName("setBet1")
    fun setBet(newBet: Int){ this.bet = newBet}

    @JvmName("getPot1")
    fun getPot(): Int { return pot}

    @JvmName("setPot1")
    fun setPot(newBet: Int){ this.pot = newBet}

}