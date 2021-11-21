package com.example.yeehawholdem.AccountGoods

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.*
import junit.framework.TestCase
import com.example.yeehawholdem.AccountGoods.CreateAccountKtTest
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.FirebaseOptions
import org.junit.Before
import org.junit.runner.manipulation.Ordering


class CreateAccountKtTest : TestCase() {

    fun testCreateAccount() {
        val options = FirebaseOptions.Builder()
            .setProjectId("yeehawholdem")
            .setApplicationId("1:638876262840:android:e8841d35f9a936820fef4d")
            .setApiKey("AIzaSyDb-1-CQUFVpwFTmmQWvdqyo-FBwr8o09A") // setDatabaseURL(...)
            // setStorageBucket(...)
            .build()

        // FirebaseApp.initializeApp(, options, "secondary")
        //FirebaseDatabase.getInstance("https://yeehawholdem-default-rtdb.firebaseio.com")
        // FirebaseDatabase.getInstance("http://localhost:9000/?ns=yeehawholdem")
        //FirebaseAuth.getInstance()
    }

    fun testCheckInputFields() {}

    fun testCreateAccountPrev() {}
}