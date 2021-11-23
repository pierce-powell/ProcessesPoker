package com.example.yeehawholdem.LogicGoods

import androidx.compose.runtime.rememberCoroutineScope
import androidx.room.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.*
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.*

class Communications
{
    lateinit var auth: FirebaseAuth

    fun addEventListener(lobby: String) : Long {
        val dbReference = Firebase.database.getReference(lobby)
        dbReference.child("Bet").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lobbyBet  = dataSnapshot.value

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