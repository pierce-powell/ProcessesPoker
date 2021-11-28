package com.example.yeehawholdem.Testing

import com.example.yeehawholdem.LogicGoods.Card
import com.example.yeehawholdem.LogicGoods.CheckHand
import org.junit.Test

class CheckHandTest {
    val checkHand = CheckHand()
    val communitycard1 = Card(4)
    val communitycard2 = Card(11)
    val communitycard3 = Card(33)
    val communitycard4 = Card(27)
    val communitycard5 = Card(0)
    val card6 = Card(40)
    val card7 = Card(48)
    val card8 = Card(20)
    val card9 = Card(49)
    var handValue = -1
    val actualHandVal = 6
    val communityCards = mutableListOf<Card>()
    var hand1 = mutableListOf<Card>()
    var hand2 = mutableListOf<Card>()

    // Constructor that runs first
    init {
        addCards()
    }

    fun addCards(){
        communityCards.add(communitycard1)
        communityCards.add(communitycard2)
        communityCards.add(communitycard3)
        communityCards.add(communitycard4)
        communityCards.add(communitycard5)

        hand1.add(card6)
        hand1.add(card7)
        hand2.add(card8)
        hand2.add(card9)

        checkHand.currentHand.add(Card(0))
        checkHand.currentHand.add(Card(4+13))
        checkHand.currentHand.add(Card(1))
        checkHand.currentHand.add(Card(2))
        checkHand.currentHand.add(Card(12))
    }

    @Test
    fun testBestHand() {
        val res = checkHand.bestHand(hand1, communityCards)
        println("hand1 = \n${res}")
        val res2 = checkHand.bestHand(hand2, communityCards)
        println("hand2 = \n${res2}")
    }

    @Test
    fun testgetCurHandValue() {
        println(checkHand.getCurHandValue())
    }

    @Test
    fun testIsRoyalFlush() {
        assert(checkHand.isRoyalFlush())
    }

    @Test
    fun testIsStraightFlush() {
        assert(checkHand.isStraightFlush())
    }

    @Test
    fun testIsFourOfAKind() {
        assert(checkHand.isFourOfAKind())
    }

    @Test
    fun testIsFullHouse() {
        assert(checkHand.isFullHouse())
    }

    @Test
    fun testIsStraight() {
        assert(checkHand.isStraight())
    }

    @Test
    fun testIsFlush() {
        assert(checkHand.isFlush())
    }

    @Test
    fun testIsThreeOfAKind() {
        assert(checkHand.isThreeOfAKind())
    }

    @Test
    fun testIsTwoPairs() {
        assert(checkHand.isTwoPairs())
    }

    @Test
    fun testIsPair() {
        checkHand.currentHand = communityCards
        assert(checkHand.isPair())
    }

    @Test
    fun testGetCurHandValue1() {
        assert(checkHand.getCurHandValue() == actualHandVal)
    }
}