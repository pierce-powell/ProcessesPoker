package com.example.yeehawholdem.OnlineGameGoods

import com.example.yeehawholdem.LobbyCode.lobbyInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InactiveListener {

   /* fun monitorActivity(activeCheck: activeCheck, curLobby: String = "") {
        val inactiveReference = Firebase.database.getReference(curLobby).child("InactiveCheck")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // Check for null
                    if (activeCheck == null) {
                        return
                    }

                    activeCheck.flag = dataSnapshot.value as Long?

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })

        val playerReference = Firebase.auth.currentUser?.uid.toString()

        activeCheck.curPlayerID = playerReference

        val hostRefrence = Firebase.database.getReference(curLobby).child("Host")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // Check for null
                    if (activeCheck == null) {
                        return
                    }

                    activeCheck.curHostID = dataSnapshot.value.toString()

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })

    }*/


}