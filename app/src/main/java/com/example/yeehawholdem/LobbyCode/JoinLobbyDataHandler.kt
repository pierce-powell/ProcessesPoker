package com.example.yeehawholdem.LobbyCode

import com.example.yeehawholdem.lobbyInfo
import com.example.yeehawholdem.quitInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JoinLobbyDataHandler {


/*    fun getTheLobbyName(currentLobbyInfo: lobbyInfo) {

        val auth = Firebase.auth

        val playerUid = auth.currentUser?.uid


        val HostReference = Firebase.database.getReference(playerUid.toString()).child("username").
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // Check for null
                    if (currentLobbyInfo == null) {
                        return
                    }

                    quitData.lobby = dataSnapshot.value.toString()

                    quitData.playerID = playerUid.toString()


                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }*/
}