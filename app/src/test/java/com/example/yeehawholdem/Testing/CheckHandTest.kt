package com.example.yeehawholdem.Testing

import com.example.yeehawholdem.LogicGoods.Card
import com.example.yeehawholdem.LogicGoods.CheckHand
import org.junit.Test

class CheckHandTest {
    val hand = CheckHand()
    val card1 = Card(0)
    val card2 = Card(13)
    val card3 = Card(18)
    val card4 = Card(22)
    val card5 = Card(45)
    val handValue = 9

    fun addCards(){
        hand.currentHand.add(card1)
        hand.currentHand.add(card2)
        hand.currentHand.add(card3)
        hand.currentHand.add(card4)
        hand.currentHand.add(card5)
    }

    @Test
    fun testIsRoyalFlush() {
        addCards()
        assert(hand.isRoyalFlush())
    }

    @Test
    fun testIsStraightFlush() {
        addCards()
        assert(hand.isStraightFlush())
    }

    @Test
    fun testIsFourOfAKind() {
        addCards()
        assert(hand.isFourOfAKind())
    }

    @Test
    fun testIsFullHouse() {
        addCards()
        assert(hand.isFullHouse())
    }

    @Test
    fun testIsStraight() {
        addCards()
        assert(hand.isStraight())
    }

    @Test
    fun testIsFlush() {
        addCards()
        assert(hand.isFlush())
    }

    @Test
    fun testIsThreeOfAKind() {
        addCards()
        assert(hand.isThreeOfAKind())
    }

    @Test
    fun testIsTwoPairs() {
        addCards()
        assert(hand.isTwoPairs())
    }

    @Test
    fun testIsPair() {
        addCards()
        assert(hand.isPair())
    }

    @Test
    fun testBestHand() {

    }

    @Test
    fun testGetCurHandValue1() {
        addCards()
        assert(hand.getCurHandValue() == handValue)
    }
}