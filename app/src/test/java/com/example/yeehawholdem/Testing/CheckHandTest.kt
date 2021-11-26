package com.example.yeehawholdem.Testing

import com.example.yeehawholdem.LogicGoods.Card
import com.example.yeehawholdem.LogicGoods.CheckHand
import org.junit.Test

class CheckHandTest {
    val checkHand = CheckHand()
    val communitycard1 = Card(11)
    val communitycard2 = Card(25)
    val communitycard3 = Card(20)
    val communitycard4 = Card(32)
    val communitycard5 = Card(46)
    val card6 = Card(35)
    val card7 = Card(23)
    val card8 = Card(8)
    val card9 = Card(9)
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
        checkHand.currentHand.add(Card(0))
        checkHand.currentHand.add(Card(1))
        checkHand.currentHand.add(Card(1))
        checkHand.currentHand.add(Card(1))
    }

    @Test
    fun testBestHand() {
        val res = checkHand.bestHand(hand1, communityCards)
        println("\n${res}")
    }

    @Test
    fun foo() {
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