package com.example.yeehawholdem

/*
Use Case:
user has joined a lobby already
flag is updated to let to show theres a new person at the table
this tells dealer to add them last in the rotation
        (Dealer: has a counter 'Whos Next' % # of players 0,1,2,3,4,0,1,2,3,4,5)
Fold, Bet are grayed until the user turn. User has 30? seconds to make a confirmation, if no bet is
    made, auto fold and switch to the next player



Otherwise behaves the same as offline, just pulling the river and user hands from the data base
    instead of local variables


 */