package com.example.yeehawholdem.LeaderBoardGoods

//comment
data class LeaderBoardPlayer(val playerName : String? = "", val playerBalance : Long? = 0, val playerUid : String? = "") :
    Comparable<LeaderBoardPlayer> {
    override fun compareTo(other: LeaderBoardPlayer): Int {
        return when {
            playerBalance == other.playerBalance -> 0
            playerBalance!! < other.playerBalance!! -> 1
            else -> -1
        }

    }
}
