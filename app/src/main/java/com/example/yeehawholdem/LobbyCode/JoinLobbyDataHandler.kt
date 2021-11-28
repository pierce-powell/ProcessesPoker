package com.example.yeehawholdem.LobbyCode

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class JoinLobbyDataHandler {


    fun getTheLobbyInfo(currentLobbyInfo: lobbyInfo, curLobby: String = "") {

        val auth = Firebase.auth

        val playerUid = auth.currentUser?.uid


        val usernameNBalanceReference = Firebase.database.getReference(playerUid.toString()).child("username")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // Check for null
                    if (currentLobbyInfo == null) {
                        return
                    }
                    currentLobbyInfo.playerName = dataSnapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })

        val numPlayersRef = Firebase.database.getReference("Lobbys").child(curLobby)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // Check for null
                    if (currentLobbyInfo == null) {
                        return
                    }

                    currentLobbyInfo.numPlayers = dataSnapshot.value as Long?

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })



        val playerBalanceReference = Firebase.database.getReference(playerUid.toString()).child("balance")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // Check for null
                    if (currentLobbyInfo == null) {
                        return
                    }

                    currentLobbyInfo.playerBalance = dataSnapshot.value as Long?

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })

        val hostReference = Firebase.database.getReference(curLobby).child("Host")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // Check for null
                    if (currentLobbyInfo == null) {
                        return
                    }

                    currentLobbyInfo.isHost = dataSnapshot.value.toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })


        val inProgressReference = Firebase.database.getReference(curLobby).child("IsGameInProgress")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // Check for null
                    if (currentLobbyInfo == null) {
                        return
                    }

                    currentLobbyInfo.isInProgress = dataSnapshot.value as Boolean?

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }
}