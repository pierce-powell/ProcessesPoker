package com.example.yeehawholdem.LogicGoods

import com.example.yeehawholdem.R

enum class SuitType(val value: Int) {
    CLUBS(0),
    DIAMONDS(1),
    HEARTS(2),
    SPADES(3);

    companion object {
        val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
    }
}

// cardID is calculated where ID 0 is twoofclubs, 1 is threeofclubs,
// 13 is twoofdiamonds, and so on.
class Card(cardID: Int) {
    var suit: SuitType
    var value: Int
    var cardPicture: Int
    var cardID = cardID

    init {
        this.suit = SuitType.values()[(cardID % 52) / 13]
        this.value = cardID % 13
        this.cardPicture = imageResource(suit, value)
    }

    fun getCardValue(): Int{
        return (this.suit.value * 13) + this.value
    }

    fun imageResource(suit: SuitType, value: Int): Int {
        return when (suit) {
            SuitType.CLUBS -> {
                when (value + 1) {
                    1 -> R.drawable.twoofclubs
                    2 -> R.drawable.threeofclubs
                    3 -> R.drawable.fourofclubs
                    4 -> R.drawable.fiveofclubs
                    5 -> R.drawable.sixofclubs
                    6 -> R.drawable.sevenofclubs
                    7 -> R.drawable.eightofclubs
                    8 -> R.drawable.nineofclubs
                    9 -> R.drawable.tenofclubs
                    10 -> R.drawable.jackofclubs
                    11 -> R.drawable.queenofclubs
                    12 -> R.drawable.kingofclubs
                    13 -> R.drawable.aceofclubs
                    else -> R.drawable.samplecard
                }
            }
            SuitType.DIAMONDS -> {
                when (value + 1) {
                    1 -> R.drawable.twoofdiamonds
                    2 -> R.drawable.threeofdiamonds
                    3 -> R.drawable.fourofdiamonds
                    4 -> R.drawable.fiveofdiamonds
                    5 -> R.drawable.sixofdiamonds
                    6 -> R.drawable.sevenofdiamonds
                    7 -> R.drawable.eightofdiamonds
                    8 -> R.drawable.nineofdiamonds
                    9 -> R.drawable.tenofdiamonds
                    10 -> R.drawable.jackofdiamonds
                    11 -> R.drawable.queenofdiamonds
                    12 -> R.drawable.kingofdiamonds
                    13 -> R.drawable.aceofdiamonds
                    else -> R.drawable.samplecard
                }
            }
            SuitType.HEARTS ->{
                when (value + 1) {
                    1 -> R.drawable.twoofhearts
                    2 -> R.drawable.threeofhearts
                    3 -> R.drawable.fourofhearts
                    4 -> R.drawable.fiveofhearts
                    5 -> R.drawable.sixofhearts
                    6 -> R.drawable.sevenofhearts
                    7 -> R.drawable.eightofhearts
                    8 -> R.drawable.nineofhearts
                    9 -> R.drawable.tenofhearts
                    10 -> R.drawable.jackofhearts
                    11 -> R.drawable.queenofhearts
                    12 -> R.drawable.kingofhearts
                    13 -> R.drawable.aceofhearts
                    else -> R.drawable.samplecard
                }
            }
            SuitType.SPADES -> {
                when (value + 1) {
                    1 -> R.drawable.twoofspades
                    2 -> R.drawable.threeofspades
                    3 -> R.drawable.fourofspades
                    4 -> R.drawable.fiveofspades
                    5 -> R.drawable.sixofspades
                    6 -> R.drawable.sevenofspades
                    7 -> R.drawable.eightofspades
                    8 -> R.drawable.nineofspades
                    9 -> R.drawable.tenofspades
                    10 -> R.drawable.jackofspades
                    11 -> R.drawable.queenofspades
                    12 -> R.drawable.kingofspades
                    13 -> R.drawable.aceofspades
                    else -> R.drawable.samplecard
                }
            }
        }
    }
}