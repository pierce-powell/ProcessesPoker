package com.example.yeehawholdem.data

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class FirebaseQueryLiveData
{
    private lateinit var databaseReference : DatabaseReference
    var auth : FirebaseAuth = Firebase.auth
    var didItWork = false

    public fun createUserAccount(email: String, password : String) : Boolean {

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            OnCompleteListener {
                didItWork = it.isSuccessful
            })

        return didItWork
    }



}

/*
MIGHT BE HELPFUL LATER IF FIRST APPROACH DOESN'T WORK
private val query : Query
private val listener = MyValueEventListener()
private var removeListenerPending = false
private val handler = Handler()
private val removeListener: Runnable

constructor(query: Query){
    this.query = query
    removeListener = Runnable {
        query.removeEventListener(listener)
        removeListenerPending = false
    }
}

constructor(ref: DatabaseReference) {
    query = ref
    removeListener = Runnable {
        query.removeEventListener(listener)
        removeListenerPending = false
    }
}

override fun onActive(){
    Log.d(LOG_TAG, "onActive")
    if(removeListenerPending) handler.removeCallbacks(removeListener)
    else query.addValueEventListener(listener)
    removeListenerPending = false
}

override fun onInactive() {
    Log.d(LOG_TAG, "onInactive")
    handler.postDelayed(removeListener, 2000L) // This might delete the data from the database after 2 seconds
    removeListenerPending = true
}


private inner class MyValueEventListener : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        Log.d(LOG_TAG, "Listening to $query")
        value = dataSnapshot
    }

    override fun onCancelled(databaseError: DatabaseError){
        Log.d(LOG_TAG, "Can't listen to query $query", databaseError.toException())
        value = null
    }
}

companion object {
    private const val LOG_TAG = "FirebaseLiveDataQuery"
}

*/