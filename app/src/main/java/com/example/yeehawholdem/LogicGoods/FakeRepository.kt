package com.example.yeehawholdem.LogicGoods

class FakeRepository {
    fun getListOfPlayers() : List<Player> {
        return listOf(
            Player("Pierce", 2000),
            Player("Nick", 3000),
            Player("Luke", 55000),
            Player("Isreal", 3300),
            Player("Pierce", 2000),
            Player("Nick", 3000),
            Player("Luke", 55000),
            Player("Isreal", 3300),
            Player("Pierce", 2000),
            Player("Nick", 3000),
            Player("Luke", 55000),
            Player("Isreal", 3300)
        )
    }
}