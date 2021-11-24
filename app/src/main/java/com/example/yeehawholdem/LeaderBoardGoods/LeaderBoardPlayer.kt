package com.example.yeehawholdem.LeaderBoardGoods

//comment
data class LeaderBoardPlayer(val playerName : String? = "", val playerBalance : Long? = 0) :
    Comparable<LeaderBoardPlayer> {
    override fun compareTo(other: LeaderBoardPlayer): Int {
        if(playerBalance == other.playerBalance)
            return 0
        else if(playerBalance!! < other.playerBalance!!)
            return 1
        else
            return -1

    }
}
