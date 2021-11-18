package com.example.Testing

import com.example.yeehawholdem.LogicGoods.*
import org.junit.Assert.*

import org.junit.Test

class CheckHandTest {

    @Test
    fun isRoyalFlush() {
    }

    @Test
    fun isStraightFlush() {
    }

    @Test
    fun isFourOfAKind() {
    }

    @Test
    fun isFullHouse() {
    }

    @Test
    fun isStraight() {
    }

    @Test
    fun isFlush() {
    }

    @Test
    fun isThreeOfAKind() {
    }

    @Test
    fun isTwoPairs() {
    }

    @Test
    fun `is not pair returns false`() {
        val cards: IntArray = intArrayOf(0, 3)

        val hand = CheckHand()
        val result = hand.validateHand(cards)
        assertFalse(result)
    }


}