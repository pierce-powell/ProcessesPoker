package com.example.yeehawholdem.OnlineGameGoods

import com.example.yeehawholdem.LogicGoods.Card
import com.example.yeehawholdem.LogicGoods.GameValues
import com.example.yeehawholdem.LogicGoods.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class Communications {
    lateinit var auth: FirebaseAuth

    fun setupBetEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setBet(snapshot.value as Long)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("Bet").addValueEventListener(lobbyListener)
    }

    fun setupPotEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setPot(snapshot.value as Long)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("Pot").addValueEventListener(lobbyListener)
    }

    fun setupIsHostListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val UID = Firebase.auth.currentUser?.uid.toString()
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setIsHost(if (snapshot.value.toString() == UID) 1L else 0L)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("Host").addValueEventListener(lobbyListener)
    }

    fun setupIsGameInProgressListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setIsGameInProgress(snapshot.value as Boolean)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("IsGameInProgress").addValueEventListener(lobbyListener)
    }

    fun setupShowWinnerListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (snapshot.value as Boolean?)?.let { game.setShowWinner(it) }
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("ShowWinner").addValueEventListener(lobbyListener)
    }

    fun setupCurrentActivePlayerEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setCurrentActivePlayer(snapshot.value as Long)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("CurrentActivePlayer").addValueEventListener(lobbyListener)
    }

    fun setupNumPlayersCheckedEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setNumPlayersChecked(snapshot.value as Long)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("NumPlayersChecked").addValueEventListener(lobbyListener)
    }

    fun setupNumWinnersEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setNumWinners(snapshot.value as Long)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("NumWinner").addValueEventListener(lobbyListener)
    }

    fun setupWinnersEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.winners.clear()
                for (i in 0.until(game.getNumWinners())) {
                    val str = i.toString()
                    (snapshot.child(str).value as String?)?.let { game.winners.add(it) }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("Winners").addValueEventListener(lobbyListener)
    }

    fun setupCurrBetCycleEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setCurrBetCycle(snapshot.value as Long)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("CurrBetCycle").addValueEventListener(lobbyListener)
    }

    fun setupNumPlayersEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setNumPlayers(snapshot.value as Long)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("NumPlayers").addValueEventListener(lobbyListener)
    }

    fun setupPlayerEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val UID = Firebase.auth.currentUser?.uid.toString()
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (snapshot.child("Cards").child("Card1").value as Long?)?.let { game.setHandCard1(it) }
                (snapshot.child("Cards").child("Card2").value as Long?)?.let { game.setHandCard2(it) }
                (snapshot.child("balance").value as Long?)?.let { game.setBalance(it) }
                (snapshot.child("IsStillIn").value as Boolean?)?.let { game.setIsStillIn(it) }
                (snapshot.child("TurnNumber").value as Long?)?.let { game.setTurnNumber(it.toInt()) }
                (snapshot.child("UserBet").value as Long?)?.let { game.setUserBet(it.toInt()) }
                (snapshot.child("DidYaWin").value as Boolean?)?.let { game.setDidYaWin(it) }
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("ActiveUsers").child(UID).addValueEventListener(lobbyListener)
    }

    fun setupRiverEventListener(game: GameValues, lobbyStr: String) {
        val database = Firebase.database.getReference(lobbyStr)
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                game.setCard1(snapshot.child("Card1").value as Long)
                game.setCard2(snapshot.child("Card2").value as Long)
                game.setCard3(snapshot.child("Card3").value as Long)
                game.setCard4(snapshot.child("Card4").value as Long)
                game.setCard5(snapshot.child("Card5").value as Long)
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        database.child("River").addValueEventListener(lobbyListener)
    }

    fun usersEventListener(game: GameValues, lobbyStr: String) {
        Firebase.database.getReference(lobbyStr).child("ActiveUsers")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val lobbyBet = dataSnapshot.children
                    // var curPlayer = LeaderBoardPlayer()
                        game.playerList.clear()
                    game.playersStillIn.clear()

                    lobbyBet.forEach {
                        val key = it.key.toString()
                        val playerBalance = it.child("balance").value as Long?
                        val playerName = it.child("username").value as String?
                        val isStillIn = it.child("IsStillIn").value as Boolean?
                        val card1 = (it.child("Cards").child("Card1").value as Long?)?.let { it1 ->
                            Card(
                                it1.toInt())
                        }
                        val card2 = (it.child("Cards").child("Card2").value as Long?)?.let { it1 ->
                            Card(
                                it1.toInt())
                        }

                        val hand = if (card1 == null || card2 == null) mutableListOf() else mutableListOf(card1, card2)

                        val player = playerName?.let { it1 ->
                            playerBalance?.let { it2 ->
                                Player(
                                    name = it1,
                                    balance = it2.toInt(),
                                    playerFirebaseId = key, hand = hand
                                )
                            }
                        }
                        if (player != null) {
                            game.playerList.add(player)
                            if (isStillIn == true) {
                                game.playersStillIn.add(player)
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    suspend fun getLobbyString(): String {
        val UID = Firebase.auth.currentUser?.uid.toString()
        val database = Firebase.database.getReference(UID)
        val snapshot = database.get().await()
        return snapshot.child("InLobby").value.toString()
    }

    fun setUserBet(lobbyStr: String, changeBet: Long) {
        val UID = Firebase.auth.currentUser?.uid.toString()
        val database = Firebase.database.getReference(lobbyStr)
        database.child("ActiveUsers").child(UID).child("UserBet").setValue(changeBet)
    }

    fun setBet(lobbyStr: String, changeBet: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("Bet").setValue(changeBet)
    }

    fun setCurrentActivePlayer(lobbyStr: String, changeCurrentActivePlayer: Long) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("CurrentActivePlayer").setValue(changeCurrentActivePlayer)
    }

    fun setIsGameInProgress(lobbyStr: String, changeIsGameInProgress: Boolean) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("IsGameInProgress").setValue(changeIsGameInProgress)
    }

    fun setShowWinner(lobbyStr: String, changeShowWinner: Boolean) {
        val database = Firebase.database.getReference(lobbyStr)
        database.child("ShowWinner").setValue(changeShowWinner)
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

    fun setPlayerTurnNumber(lobbyStr: String, playerList: MutableList<Player>) {
        val database = Firebase.database.getReference(lobbyStr)
        for (player in playerList) {
            database.child("ActiveUsers").child(player.playerFirebaseId).child("TurnNumber")
                .setValue(playerList.indexOf(player))
        }
    }

    fun setDidYaWin(lobbyStr: String, playerList: MutableList<Player>) {
        val database = Firebase.database.getReference(lobbyStr)
        for (player in playerList) {
            database.child("ActiveUsers").child(player.playerFirebaseId).child("DidYaWin")
                .setValue(false)
        }
    }

    fun resetHighestBet(lobbyStr: String, playerList: MutableList<Player>) {
        val database = Firebase.database.getReference(lobbyStr)
        for (player in playerList) {
            database.child("ActiveUsers").child(player.playerFirebaseId).child("UserBet")
                .setValue(0)
        }
    }

    fun setPlayerIsStillIn(lobbyStr: String, playerList: MutableList<Player>){
        val database = Firebase.database.getReference(lobbyStr)
        for (player in playerList) {
            database.child("ActiveUsers").child(player.playerFirebaseId).child("IsStillIn")
                .setValue(true)
        }
    }
}