package com.example.yeehawholdem.LogicGoods

import androidx.compose.runtime.rememberCoroutineScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Communications {
    lateinit var databaseReference : DatabaseReference
    lateinit var auth: FirebaseAuth

    suspend fun getBet(){
        val database = Firebase.database
        auth = Firebase.auth


    }
}