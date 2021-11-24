package com.example.yeehawholdem.OnlineGameGoods

import com.example.yeehawholdem.LeaderBoardGoods.LeaderBoardPlayer
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuitGameDataHandler {

    fun getTheHostAndPlayer(mutableMap: MutableMap<String, LeaderBoardPlayer>) {

        var auth = Firebase.auth

        var playerId = auth.currentUser?.uid



        val HostReference = Firebase.database.getReference("Users").addValueEventListener(object :
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

//Data that we need
// Current Host
// Current User ID
// Current Lobby
// If the current user is the host, remove HostOfLobby under host ID
// Delete WaitRoom
// Delete Active Players
// Reset all the values back to their defaults