package com.example.yeehawholdem.OnlineGameGoods

import com.example.yeehawholdem.LeaderBoardGoods.LeaderBoardPlayer
import com.example.yeehawholdem.quitInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuitGameDataHandler {

    fun getTheLobbyName(quitData: quitInfo) {

        val auth = Firebase.auth

        val playerUid = auth.currentUser?.uid


        val HostReference = Firebase.database.getReference(playerUid.toString()).child("InLobby").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // Check for null
                if (quitData == null) {
                    return
                }

                quitData.lobby = dataSnapshot.value.toString()

                quitData.playerID = playerUid.toString()



            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        val lobbyReference = Firebase.database.getReference(quitData.lobby.toString()).child("DidPlayerQuit").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (quitData == null)
                    return

                quitData.didPlayerQuit = snapshot.value as Long?
            }

            override fun onCancelled(error: DatabaseError) {
                //failed
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