package com.example.yeehawholdem.Testing

import com.example.yeehawholdem.LogicGoods.Card
import com.example.yeehawholdem.LogicGoods.CheckHand
import org.junit.Test

class CheckHandTest {
    val checkHand = CheckHand()
    val communitycard1 = Card(0)
    val communitycard2 = Card(7+13)
    val communitycard3 = Card(8+39)
    val communitycard4 = Card(11+26)
    val communitycard5 = Card(6)

    val card6 = Card(5+26)
    val card7 = Card(12)

    val card8 = Card(10)
    val card9 = Card(12)

    val card10 = Card(8)
    val card11 = Card(5)
    var handValue = -1
    val actualHandVal = 6
    val communityCards = mutableListOf<Card>()
    var hand1 = mutableListOf<Card>()
    var hand2 = mutableListOf<Card>()
    var hand3 = mutableListOf<Card>()

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
        hand3.add(card10)
        hand3.add(card11)

        checkHand.currentHand.add(Card(0))
        checkHand.currentHand.add(Card(0+13))
        checkHand.currentHand.add(Card(1))
        checkHand.currentHand.add(Card(25))
        checkHand.currentHand.add(Card(12))
    }

    @Test
    fun testBestHand() {
        val res = checkHand.bestHand(hand1, communityCards)
        println("hand1 = ${res}")
        val res2 = checkHand.bestHand(hand2, communityCards)
        println("hand2 = ${res2}")
    }

    @Test
    fun foo() {
        checkHand.currentHand.removeAt(checkHand.currentHand.indexOfFirst { it.value == checkHand.getHighestCardValueInHand() })
        println(checkHand.getHighestCardValueInHand())
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