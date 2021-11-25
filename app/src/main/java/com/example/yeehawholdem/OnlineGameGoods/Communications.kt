package com.example.yeehawholdem.OnlineGameGoods

import android.widget.TextView
import com.example.yeehawholdem.LeaderBoardGoods.LeaderBoardPlayer
import com.example.yeehawholdem.LogicGoods.GameValues
import com.example.yeehawholdem.LogicGoods.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.*
import kotlinx.coroutines.tasks.await

class Communications {
    lateinit var auth: FirebaseAuth

    fun addEventListener(lobby: String, mutableList: MutableList<Long>) {
        val dbReference = Firebase.database.getReference(lobby)
        dbReference.child("Bet").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lobbyBet = dataSnapshot.getValue()

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

    fun setupLobbyEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val UID = Firebase.auth.currentUser?.uid.toString()
        val lobbyListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                game.setBet(snapshot?.child("Bet").value as Long)
                game.setPot(snapshot?.child("Pot").value as Long)
                game.setCard1(snapshot?.child("River").child("Card1").value as Long)
                game.setCard2(snapshot?.child("River").child("Card2").value as Long)
                game.setCard3(snapshot?.child("River").child("Card3").value as Long)
                game.setCard4(snapshot?.child("River").child("Card4").value as Long)
                game.setCard5(snapshot?.child("River").child("Card5").value as Long)
                game.setCurrentActivePlayer(snapshot?.child("CurrentActivePlayer").value as Long)
                game.setIsHost(if (snapshot?.child("Host").value.toString() == UID) 1L else 0L)
                game.setIsGameInProgress(snapshot?.child("IsGameInProgress").value as Boolean)
                game.setNumPlayersChecked(snapshot?.child("NumPlayersChecked").value as Long)
                game.setCurrBetCycle(snapshot?.child("CurrBetCycle").value as Long)
                game.setNumPlayers(snapshot?.child("NumPlayers").value as Long)

                (snapshot.child("ActiveUsers").child(UID).child("Cards")
                    .child("Card1").value as Long?)?.let { game.setHandCard1(it) }
                (snapshot.child("ActiveUsers").child(UID).child("Cards")
                    .child("Card2").value as Long?)?.let { game.setHandCard2(it) }
                (snapshot.child("ActiveUsers").child(UID)
                    .child("balance").value as Long?)?.let { game.setBalance(it) }
                (snapshot.child("ActiveUsers").child(UID)
                    .child("IsStillIn").value as Boolean?)?.let { game.setIsStillIn(it) }
                (snapshot.child("ActiveUsers").child(UID)
                    .child("TurnNumber").value as Long?)?.let { game.setTurnNumber(it.toInt()) }
            }

            override fun onCancelled(error: DatabaseError) {}

        }

        database.addValueEventListener(lobbyListener)
    }

    suspend fun getLobbyString(): String {
        val UID = Firebase.auth.currentUser?.uid.toString()
        val database = Firebase.database.getReference(UID)

        val snapshot = database.get().await()

        return snapshot.child("InLobby").value.toString()
    }

    fun usersEventListener(game: GameValues, lobbyStr: String) {
        Firebase.database.getReference(lobbyStr).child("ActiveUsers")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val lobbyBet = dataSnapshot.children
                    var curPlayer = LeaderBoardPlayer()
                    game.playerList.clear()
                    // game.playersStillIn.clear()

                    lobbyBet.forEach {
                        var key = it.key.toString()
                        var playerBalance = it.child("balance").getValue() as Long?
                        var playerName = it.child("username").getValue() as String?
                        var isStillIn = it.child("IsStillIn").getValue() as Boolean?
                        var player = playerName?.let { it1 ->
                            playerBalance?.let { it2 ->
                                Player(
                                    name = it1,
                                    balance = it2.toInt(),
                                    playerFirebaseId = key
                                )
                            }
                        }
                        if (key != "") {
                            if (player != null) {
                                game.playerList.add(player)
                                if (isStillIn == true) {
                                    // game.playersStillIn.add(player)
                                }
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
    }

    fun setBet(lobbyStr: String, changeBet: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("Bet").setValue(changeBet)
    }

    fun setCurrentActivePlayer(lobbyStr: String, changeCurrentActivePlayer: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("CurrentActivePlayer").setValue(changeCurrentActivePlayer)
    }

    fun setDidPlayerQuit(lobbyStr: String, changeDidPlayerQuick: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("DidPlayerQuit").setValue(changeDidPlayerQuick)
    }

    fun setHost(lobbyStr: String, changeHost: String) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("Host").setValue(changeHost)
    }

    fun setIsGameInProgress(lobbyStr: String, changeIsGameInProgress: Boolean) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("IsGameInProgress").setValue(changeIsGameInProgress)
    }

    fun setNumPlayers(lobbyStr: String, changeNumPlayers: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("NumPlayers").setValue(changeNumPlayers)
        val dbref = Firebase.database.getReference("Lobbys")
        dbref.child(lobbyStr).setValue(changeNumPlayers)
    }

    fun setPot(lobbyStr: String, changePot: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("Pot").setValue(changePot)
    }

    fun setCard1(lobbyStr: String, changeCard1: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("River").child("Card1").setValue(changeCard1)
    }

    fun setCard2(lobbyStr: String, changeCard2: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("River").child("Card2").setValue(changeCard2)
    }

    fun setCard3(lobbyStr: String, changeCard3: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("River").child("Card3").setValue(changeCard3)
    }

    fun setCard4(lobbyStr: String, changeCard4: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("River").child("Card4").setValue(changeCard4)
    }

    fun setCard5(lobbyStr: String, changeCard5: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("River").child("Card5").setValue(changeCard5)
    }

    fun setPlayerHands(lobbyStr: String, playerList: MutableList<Player>) {
        val database = Firebase.database.getReference(lobbyStr)
        for (player in playerList) {
            database.child("ActiveUsers").child(player.playerFirebaseId).child("Cards")
                .child("Card1").setValue(player.hand.getOrNull(0)?.cardID)
            database.child("ActiveUsers").child(player.playerFirebaseId).child("Cards")
                .child("Card2").setValue(player.hand.getOrNull(1)?.cardID)
        }
    }

    fun setBalance(lobbyStr: String, balance : Long) {
        val database = Firebase.database.getReference(lobbyStr)
        val UID = Firebase.auth.currentUser?.uid.toString()
        database.child("ActiveUsers").child(UID).child("balance").setValue(balance)
    }

    fun setIsStillIn(lobbyStr: String, isStillIn : Boolean) {
        val database = Firebase.database.getReference(lobbyStr)
        val UID = Firebase.auth.currentUser?.uid.toString()
        database.child("ActiveUsers").child(UID).child("IsStillIn").setValue(isStillIn)
    }

    fun setNumPlayersChecked(lobbyStr: String, numPlayersChecked: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("NumPlayersChecked").setValue(numPlayersChecked)
    }

    fun setCurrBetCycle(lobbyStr: String, currBetCycle: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("CurrBetCycle").setValue(currBetCycle)
    }

    fun setPlayerTurnNumber(lobbyStr: String, playerList: MutableList<Player>) {
        val database = Firebase.database.getReference(lobbyStr)
        for (player in playerList) {
            database.child("ActiveUsers").child(player.playerFirebaseId).child("TurnNumber")
                .setValue(playerList.indexOf(player))
        }
    }
}