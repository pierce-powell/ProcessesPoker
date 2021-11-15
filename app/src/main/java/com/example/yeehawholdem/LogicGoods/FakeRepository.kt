package com.example.yeehawholdem.LogicGoods

class FakeRepository {
    fun getListOfPlayers() : List<Player> {
        return listOf(
            Player("Pierce", 2000f),
            Player("Nick", 3000f),
            Player("Luke", 55000f),
            Player("Israel", 3300f),
            Player("Pierce", 2000f),
            Player("Nick", 3000f),
            Player("Luke", 55000f),
            Player("Israel", 3300f),
            Player("Pierce", 2000f),
            Player("Nick", 3000f),
            Player("Luke", 55000f),
            Player("Israel", 3300f)
        )
    }
}