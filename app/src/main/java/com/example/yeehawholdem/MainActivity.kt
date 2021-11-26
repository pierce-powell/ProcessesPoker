package com.example.yeehawholdem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yeehawholdem.ui.theme.YeeHawHoldemTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YeeHawHoldemTheme {
                navController = rememberNavController()
                SetUpNavHost(navController = navController)
                }
            }
        }
    }


