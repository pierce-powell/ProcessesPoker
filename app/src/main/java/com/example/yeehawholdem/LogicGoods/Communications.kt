package com.example.yeehawholdem.LogicGoods

import android.widget.TextView
import androidx.compose.animation.core.snap
import androidx.compose.runtime.rememberCoroutineScope
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

    constructor(lobbyStr: String){
    }

    fun addEventListener(lobby: String, mutableList: MutableList<Long>){
        val dbReference = Firebase.database.getReference(lobby)
        dbReference.child("Bet").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lobbyBet  = dataSnapshot.getValue()


                // Check for null
                if (lobbyBet == null) {
                    return
                }

                mutableList[0] = lobbyBet as Long
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    fun setupLobbyEventListener(game: GameValues, betText: TextView, potText: TextView, lobbyStr: String){
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                game.setBet(snapshot?.child("Bet").value as Int)
                game.setPot(snapshot?.child("Pot").value as Int)

                var temp = game.getBet()
                betText.text = temp.toString()

                temp = game.getPot()
                potText.text = temp.toString()
            }

            override fun onCancelled(error: DatabaseError) { }

        }

        database.addValueEventListener(lobbyListener)
    }
}