package com.example.yeehawholdem.OnlineGameGoods

import com.example.yeehawholdem.LeaderBoardGoods.LeaderBoardPlayer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuitGameDataHandler {

    fun usersEventListener(mutableMap: MutableMap<String, LeaderBoardPlayer>) {
        val dbReference = Firebase.database.getReference("Users").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lobbyBet = dataSnapshot.children

                var curPlayer = LeaderBoardPlayer()
                lobbyBet.forEach {
                    var key = it.key


                    var playerBalance = it.child("balance").getValue() as Long?
                    var playerName = it.child("username").getValue() as String?


                    mutableMap[key.toString()] = LeaderBoardPlayer(playerName, playerBalance)


                }

                // Check for null
                if (lobbyBet == null) {
                    return
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }
}