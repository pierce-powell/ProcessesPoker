package com.example.yeehawholdem.LogicGoods

class GameValues {
    private var bet: Long = 0
    private var pot: Long = 0
    private var card1: Long = -1
    private var card2: Long = -1
    private var card3: Long = -1
    private var card4: Long = -1
    private var card5: Long = -1
    private var handCard1: Long = -1
    private var handCard2: Long = -1
    private var currentActivePlayer: Long = 0

    @JvmName("getBet1")
    fun getBet(): Long { return bet}

    @JvmName("setBet1")
    fun setBet(newBet: Long){ this.bet = newBet}

    @JvmName("getPot1")
    fun getPot(): Long { return pot}

    @JvmName("setPot1")
    fun setPot(newBet: Long){ this.pot = newBet}

    @JvmName("getCurrentActivePlayer1")
    fun getCurrentActivePlayer(): Long { return currentActivePlayer}

    @JvmName("setCurrentActivePlayer1")
    fun setCurrentActivePlayer(newBet: Long){ this.currentActivePlayer = newBet}

    @JvmName("getCard1")
    fun getCard1(): Long { return card1}

    @JvmName("setCard1")
    fun setCard1(newBet: Long){ this.card1 = newBet}

    @JvmName("getCard2")
    fun getCard2(): Long { return card2}

    @JvmName("setCard2")
    fun setCard2(newBet: Long){ this.card2 = newBet}

    @JvmName("getCard3")
    fun getCard3(): Long { return card3}

    @JvmName("setCard3")
    fun setCard3(newBet: Long){ this.card3 = newBet}

    @JvmName("getCard4")
    fun getCard4(): Long { return card4}

    @JvmName("setCard4")
    fun setCard4(newBet: Long){ this.card4 = newBet}

    @JvmName("getCard5")
    fun getCard5(): Long { return card5}

    @JvmName("setCard5")
    fun setCard5(newBet: Long){ this.card5 = newBet}

    fun betToString(): String { return this.bet.toString()}

    fun potToString(): String { return this.pot.toString()}

    fun card1ToString(): String { return this.card1.toString()}

    fun card2ToString(): String { return this.card2.toString()}

    fun card3ToString(): String { return this.card3.toString()}

    fun card4ToString(): String { return this.card4.toString()}

    fun card5ToString(): String { return this.card5.toString()}

    @JvmName("getHandCard1")
    fun getHandCard1(): Long { return handCard1}

    @JvmName("setHandCard1")
    fun setHandCard1(newBet: Long){ this.handCard1 = newBet}

    @JvmName("getHandCard2")
    fun getHandCard2(): Long { return handCard2}

    @JvmName("setHandCard2")
    fun setHandCard2(newBet: Long){ this.handCard2 = newBet}
}