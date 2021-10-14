package com.example.yeehawholdem

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CreateAccount(navController : NavController)
{
    //TODO implement checks on the email and user name choice, no duplicates (Security?), or just allow dupes
    //TODO also actually do something with the gathered variables when they click the create account button

    val image = Image(painter = painterResource(id = R.drawable.pain), contentDescription = null)

    val username = remember { mutableStateOf("")}
    val email = remember { mutableStateOf("")}
    val password1 = remember { mutableStateOf("")}
    val password2 = remember { mutableStateOf("")}

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter)
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter)
        {
            image
        }
        
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.70f)
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(color = MaterialTheme.colors.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) 
        {
            Text(text = "Create An Account", 
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = MaterialTheme.typography.h5.fontWeight)
            
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) 
            {
                OutlinedTextField(value = username.value, onValueChange = {username.value = it},
                label = { Text(text = "Username")},
                placeholder = { Text(text = "Username")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth(.8f))
            }
        }
    }
}

@Composable
@Preview
fun CreateAccountPrev()
{
    CreateAccount(navController = rememberNavController())
}