package com.example.yeehawholdem.OnlineGameGoods

import com.example.yeehawholdem.quitInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuitGameDataHandler {

    fun getTheLobbyName(quitData: quitInfo) {

        val auth = Firebase.auth
        val playerUid = auth.currentUser?.uid

        val InLobbyReference = Firebase.database.getReference(playerUid.toString()).child("InLobby").addValueEventListener(object :
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


        val hostReference = Firebase.database.getReference(quitData.lobby.toString()).child("Host").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (quitData == null)
                    return

                quitData.host = snapshot.value as String?
            }

            override fun onCancelled(error: DatabaseError) {
                //failed
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

            val activePlayerReference = Firebase.database.getReference(quitData.lobby.toString()).child("ActiveUsers").addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        quitData.playerList[snapshot.key.toString()] = snapshot.child("balance").value as Long?
                    }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                        quitData.playerList[snapshot.key.toString()] = snapshot.child("balance").value as Long?
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                        quitData.playerList.remove(snapshot.key.toString())
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}

            })

        })

        val waitListPlayerReference = Firebase.database.getReference(quitData.lobby.toString()).child("WaitingRoom").addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                //Just store the key in a map to get easy look up
                quitData.waitRoom[snapshot.key.toString()] = 0

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                //Same here, the actual value doesn't matter
                quitData.waitRoom[snapshot.key.toString()] = 0
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                quitData.waitRoom.remove(snapshot.key.toString())
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        })


    }

}