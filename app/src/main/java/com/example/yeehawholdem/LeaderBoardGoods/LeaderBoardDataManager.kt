package com.example.yeehawholdem.LeaderBoardGoods

import com.example.yeehawholdem.LogicGoods.Player
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.yeehawholdem.LeaderBoardGoods.LeaderBoardPlayer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

//comment
class LeaderBoardDataManager {

    fun usersEventListener(mutableMap: MutableMap<String, LeaderBoardPlayer>) {

        val auth: FirebaseAuth = Firebase.auth




        Firebase.database.getReference("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lobbyBet  = dataSnapshot.children

                var curPlayer = LeaderBoardPlayer()
                lobbyBet.forEach {
                    var key = it.key

                    var playerUid = ""

                    if (it.key == auth.currentUser?.uid.toString() )
                        playerUid = it.key.toString()

                    var playerBalance = it.child("balance").getValue() as Long?
                    var playerName = it.child("username").getValue() as String?


                    mutableMap[key.toString()] = LeaderBoardPlayer(playerName, playerBalance, playerUid)


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
