package com.example.yeehawholdem.LogicGoods

import kotlin.math.absoluteValue

class CheckHand {
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
            // Check for "Wheel" Straight (Ace, 2, 3, 4, 5)
            else if (getHighestCardValueInHand() == 12) {
                if ((list.getOrNull(3)!!.value) - list.getOrNull(0)!!.value == 3)
                    if (list.getOrNull(3)!!.value == 3)
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
        for (combo in set) {
            currentHand = combo.toMutableList()
            values.add(getCurHandValue())
        }

        // Return the highest value of the set
        return values.maxOrNull()!!
    }

    @JvmName("getCurHandValue1")
    fun getCurHandValue(): Int {
        curHandValue = when {
            isRoyalFlush() -> 9 *13*13*13*13*13
            isStraightFlush() -> 8 *13*13*13*13*13 + straight()
            isFourOfAKind() -> 7 *13*13*13*13*13 + fourOfAKind()
            isFullHouse() -> 6 *13*13*13*13*13 + fullHouse()
            isFlush() -> 5 *13*13*13*13*13 + noCombo()
            isStraight() -> 4 *13*13*13*13*13 + straight()
            isThreeOfAKind() -> 3 *13*13*13*13*13 + threeOfAKind()
            isTwoPairs() -> 2 *13*13*13*13*13 + pairs()
            isPair() -> 1 *13*13*13*13*13 + pairs()
            else -> noCombo()
        }
        return curHandValue
    }

    // These functions are to help map out the hand values for tie-breaking
    fun getHighestCardValueInHand(): Int {
        val list = currentHand.groupBy { it.value }

        return list.maxOf { it.key }
    }

    fun noCombo(): Int {
        val card1 = getHighestCardValueInHand()
        currentHand.removeAt(currentHand.indexOfFirst { it.value == getHighestCardValueInHand() })
        val card2 = getHighestCardValueInHand()
        currentHand.removeAt(currentHand.indexOfFirst { it.value == getHighestCardValueInHand() })
        val card3 = getHighestCardValueInHand()
        currentHand.removeAt(currentHand.indexOfFirst { it.value == getHighestCardValueInHand() })
        val card4 = getHighestCardValueInHand()
        currentHand.removeAt(currentHand.indexOfFirst { it.value == getHighestCardValueInHand() })
        val card5 = getHighestCardValueInHand()
        return card1*13*13*13*13 + card2*13*13*13 + card3*13*13 + card4*13 + card5
    }

    private fun straight(): Int {
        val list = currentHand.distinctBy { it.value }.sortedBy { it.value }
        if (getHighestCardValueInHand() == 12) {
            return list.getOrNull(3)!!.value
        }
        return getHighestCardValueInHand()
    }

    private fun fourOfAKind(): Int {
        val list = currentHand.groupBy { it.value }
        var four = 0
        var max = 0
        for (xs in list) {
            if (xs.value.size == 4) {
                four = xs.key
            } else {
                if (xs.key.absoluteValue > max)
                    max = xs.key.absoluteValue
            }
        }
        return four * 13*13*13 + max*13*13
    }

    private fun fullHouse(): Int {
        var pair = 0
        var three = 0
        val list = currentHand.groupBy { it.value }

        for (xs in list) {
            if (xs.value.size == 2) {
                pair = xs.key.absoluteValue
            } else if (xs.value.size == 3) {
                three = xs.key.absoluteValue
            }
        }
        return three*13*13*13 + pair*13*13
    }

    private fun threeOfAKind(): Int {
        val list = currentHand.groupBy { it.value }
        var three = 0
        var max = 0
        for (xs in list) {
            if (xs.value.size == 3) {
                three = xs.key
            } else {
                if (xs.key.absoluteValue > max)
                    max = xs.key.absoluteValue
            }
        }
        currentHand.removeAt(currentHand.indexOfFirst { it.value == three })
        currentHand.removeAt(currentHand.indexOfFirst { it.value == three })
        currentHand.removeAt(currentHand.indexOfFirst { it.value == three })
        val card1 = getHighestCardValueInHand()
        currentHand.removeAt(currentHand.indexOfFirst { it.value == getHighestCardValueInHand() })
        val card2 = getHighestCardValueInHand()
        return three*13*13*13 + card1*13*13 + card2*13
    }

    private fun pairs(): Int {
        val list = currentHand.groupBy { it.value }
        var pair = 0
        var max = 0
        for (xs in list) {
            if (xs.value.size == 2) {
                pair = xs.key
            } else {
                if (xs.key.absoluteValue > max)
                    max = xs.key.absoluteValue
            }
        }
        currentHand.removeAt(currentHand.indexOfFirst { it.value == pair })
        currentHand.removeAt(currentHand.indexOfFirst { it.value == pair })
        val card1 = getHighestCardValueInHand()
        currentHand.removeAt(currentHand.indexOfFirst { it.value == getHighestCardValueInHand() })
        val card2 = getHighestCardValueInHand()
        currentHand.removeAt(currentHand.indexOfFirst { it.value == getHighestCardValueInHand() })
        val card3 = getHighestCardValueInHand()
        return pair*13*13*13 + card1*13*13 + card2*13 + card3
    }
}