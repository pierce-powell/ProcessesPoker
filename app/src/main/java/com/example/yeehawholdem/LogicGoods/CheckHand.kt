package com.example.yeehawholdem.LogicGoods


// TODO: Need a function that decides tie-breaking (e.g. highest cards)

class CheckHand {
    var pokerHandValues: MutableList<Int> = mutableListOf()
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
        val max = list.maxByOrNull { list.size }
        if (max?.value?.size == 4)
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
        if (list.size == 5)
            if (list.getOrNull(4)!!.value - list.getOrNull(0)!!.value == 4)
                return true
        return false
    }

    fun isFlush(): Boolean {
        if (currentHand.distinctBy { it.suit }.size == 1)
            return true
        return false
    }

    fun isThreeOfAKind(): Boolean {
        val list = currentHand.groupBy { it.value }
        val max = list.maxByOrNull { list.size }
        if (max?.value?.size == 3)
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
        val max = list.maxByOrNull { list.size }
        if (max?.value?.size == 2)
            return true
        return false
    }

    // Go through all the combinations of hands and return the value of the hand with
    // the highest value determine by getCurHandValue(). Does not check for differences
    // between hands with equal values (e.g. different straights, flushes, etc.)
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

        // val indexOfBestHand = values.lastIndexOf(values.maxOrNull())
        // return set.elementAt(indexOfBestHand).toMutableList()

        // Return the highest value of the set
        return values.maxOrNull()!!
    }

    @JvmName("getCurHandValue1")
    fun getCurHandValue(): Int {
        if (isRoyalFlush())
            curHandValue = 9
        else if (isStraightFlush())
            curHandValue = 8
        else if (isFourOfAKind())
            curHandValue = 7
        else if (isFullHouse())
            curHandValue = 6
        else if (isFlush())
            curHandValue = 5
        else if (isStraight())
            curHandValue = 4
        else if (isThreeOfAKind())
            curHandValue = 3
        else if (isTwoPairs())
            curHandValue = 2
        else if (isPair())
            curHandValue = 1
        else
            curHandValue = 0
        return curHandValue
    }
}