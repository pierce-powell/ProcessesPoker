package com.example.yeehawholdem.LogicGoods

import kotlin.math.absoluteValue


// TODO: Need a function that decides tie-breaking (e.g. highest cards)

class CheckHand {
    // var pokerHandValues: MutableList<Int> = mutableListOf()
    var currentHand: MutableList<Card> = mutableListOf()
    var curHandValue: Int = 0

    fun isRoyalFlush(): Boolean {
        if (isStraightFlush()) {
            if (currentHand.sortedBy { it.value }.getOrNull(0)?.value == 8)
                return true
        }
        return false
    }

    fun isStraightFlush(): Boolean {
        if (isStraight() && isFlush())
            return true
        return false
    }

    fun isFourOfAKind(): Boolean {
        val list = currentHand.groupBy { it.value }
        val max = list.maxOfOrNull { it.value.size }

        if (max == 4)
            return true
        return false
    }

    fun isFullHouse(): Boolean {
        val list = currentHand.groupBy { it.value }
        var pair = false
        var three = false

        for (xs in list) {
            if (xs.value.size == 2)
                pair = true
            if (xs.value.size == 3)
                three = true
        }
        if (pair && three)
            return true
        return false
    }

    fun isStraight(): Boolean {
        val list = currentHand.distinctBy { it.value }.sortedBy { it.value }
        if (list.size == 5) {
            if (list.getOrNull(4)!!.value - list.getOrNull(0)!!.value == 4)
                return true
            else if (getHighestCardValueInHand() == 12) {
                if (list.getOrNull(3)!!.value - list.getOrNull(0)!!.value == 3)
                    return true
            }
        }
        return false
    }

    fun isFlush(): Boolean {
        if (currentHand.distinctBy { it.suit }.size == 1)
            return true
        return false
    }

    fun isThreeOfAKind(): Boolean {
        val list = currentHand.groupBy { it.value }
        val max = list.maxOfOrNull { it.value.size }

        if (max == 3)
            return true
        return false
    }

    fun isTwoPairs(): Boolean {
        val list = currentHand.groupBy { it.value }
        var count = 0

        for (xs in list) {
            if (xs.value.size == 2)
                count++
        }
        if (count >= 2)
            return true
        return false
    }

    fun isPair(): Boolean {
        val list = currentHand.groupBy { it.value }
        val max = list.maxOfOrNull { it.value.size }

        if (max == 2)
            return true
        return false
    }

    // Go through all the combinations of hands and return the value of the hand with
    // the highest value determine by getCurHandValue(). Does not check for differences
    // between hands with equal values (e.g. different straights, flushes, etc.)
    @Suppress("UnstableApiUsage")
    fun bestHand(_hand: MutableList<Card>, communityCards: MutableList<Card>) : Int {
        val all7 = mutableSetOf<Card>()
        mutableListOf<MutableList<Card>>()
        val values = mutableListOf<Int>()

        all7.addAll(_hand)
        all7.addAll(communityCards)

        // Creates a set containing all combinations of size 5 from the 7 available cards.
        val set = com.google.common.collect.Sets.combinations(all7, 5)

        // Calculate the value of each combination
        for (comb in set) {
            currentHand = comb.toMutableList()
            values.add(getCurHandValue())
        }

        // Return the highest value of the set
        return values.maxOrNull()!!
    }

    @JvmName("getCurHandValue1")
    fun getCurHandValue(): Int {
        if (isRoyalFlush())
            curHandValue = 9 *13*13
        else if (isStraightFlush())
            curHandValue = 8 *13*13 + straight()
        else if (isFourOfAKind())
            curHandValue = 7 *13*13 + fourOfAKind()
        else if (isFullHouse())
            curHandValue = 6 *13*13 + fullHouse()
        else if (isFlush())
            curHandValue = 5 *13*13 + getHighestCardValueInHand()
        else if (isStraight())
            curHandValue = 4 *13*13 + straight()
        else if (isThreeOfAKind())
            curHandValue = 3 *13*13 + threeOfAKind()
        else if (isTwoPairs())
            curHandValue = 2 *13*13 + pairs()
        else if (isPair())
            curHandValue = 1 *13*13 + pairs()
        else
            curHandValue = 0 *13*13 + getHighestCardValueInHand()
        return curHandValue
    }

    // These functions are to help map out the hand values for tie-breaking
    fun getHighestCardValueInHand(): Int {
        val list = currentHand.groupBy { it.value }

        return list.maxOf { it.key }
    }

    fun straight(): Int {
        val list = currentHand.distinctBy { it.value }.sortedBy { it.value }
        if (getHighestCardValueInHand() == 12) {
            return list.getOrNull(3)!!.value
        }
        return getHighestCardValueInHand()
    }

    fun fourOfAKind(): Int {
        val list = currentHand.groupBy { it.value }
        for (xs in list) {
            if (xs.value.size == 1) {
                return xs.key.absoluteValue
            }
        }
        return -1
    }

    fun fullHouse(): Int {
        var pair = 0
        var three = 0
        val list = currentHand.groupBy { it.value }
        val max = list.maxOfOrNull { it.value.size }

        for (xs in list) {
            if (xs.value.size == 2) {
                pair = xs.key.absoluteValue
            } else if (xs.value.size == 3) {
                three = xs.key.absoluteValue
            }
        }
        return three * 13 + pair
    }

    fun threeOfAKind(): Int {
        val list = currentHand.groupBy { it.value }
        var max = 0
        for (xs in list) {
            if (xs.value.size != 3) {
                if(xs.key.absoluteValue > max)
                    max = xs.key.absoluteValue
            }
        }
        return max
    }

    fun pairs(): Int {
        val list = currentHand.groupBy { it.value }
        var max = 0
        for (xs in list) {
            if (xs.value.size != 2) {
                if(xs.key.absoluteValue > max)
                    max = xs.key.absoluteValue
            }
        }
        return max
    }
}