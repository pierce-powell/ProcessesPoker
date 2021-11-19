package com.example.yeehawholdem.Testing

import com.example.yeehawholdem.LogicGoods.Card
import com.example.yeehawholdem.LogicGoods.CheckHand
import org.junit.Test

class CheckHandTest {
    val hand = CheckHand()
    val card1 = Card(0)
    val card2 = Card(13)
    val card3 = Card(26)
    val card4 = Card(14)
    val card5 = Card(45)
    val handValue = 9
    val communityCard = mutableListOf<Card>(Card(1), Card(2))

    // Constructor that runs first
    init {
        addCards()
    }

    fun addCards(){
        hand.currentHand.add(card1)
        hand.currentHand.add(card2)
        hand.currentHand.add(card3)
        hand.currentHand.add(card4)
        hand.currentHand.add(card5)
    }

    @Test
    fun testIsRoyalFlush() {
        assert(hand.isRoyalFlush())
    }

    @Test
    fun testIsStraightFlush() {
        assert(hand.isStraightFlush())
    }

    @Test
    fun testIsFourOfAKind() {
        assert(hand.isFourOfAKind())
    }

    @Test
    fun testIsFullHouse() {
        assert(hand.isFullHouse())
    }

    @Test
    fun testIsStraight() {
        assert(hand.isStraight())
    }

    @Test
    fun testIsFlush() {
        assert(hand.isFlush())
    }

    @Test
    fun testIsThreeOfAKind() {
        assert(hand.isThreeOfAKind())
    }

    @Test
    fun testIsTwoPairs() {
        assert(hand.isTwoPairs())
    }

    @Test
    fun testIsPair() {
        assert(hand.isPair())
    }

    @Test
    fun testBestHand() {
        hand.bestHand(hand.currentHand, communityCard)
    }

    @Test
    fun testGetCurHandValue1() {
        assert(hand.getCurHandValue() == handValue)
    }
}