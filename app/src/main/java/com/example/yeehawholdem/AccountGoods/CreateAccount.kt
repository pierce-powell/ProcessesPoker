package com.example.yeehawholdem

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CreateAccount(navController : NavController)
{
    //TODO implement checks on the email and user name choice, no duplicates (Security?), or just allow dupes
    //TODO implement the password check to issue the user a prompt if they don't match
    //TODO also actually do something with the gathered variables when they click the create account button

    //Variables
    val username = remember { mutableStateOf("")}
    val email = remember { mutableStateOf("")}
    val password1 = remember { mutableStateOf("")}
    val password2 = remember { mutableStateOf("")}

    //Password visibility controller
    var passwordVisibility by remember { mutableStateOf(false) }

    //determine the visibility Icon
    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.design_ic_visibility)
    else
        painterResource(id = R.drawable.design_ic_visibility_off)

    //Outer box to throw everything in
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter)
    {
        //Inner box for our pretty picture
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background), contentAlignment = Alignment.TopCenter)
        {
            //Image(painter = painterResource(id = R.drawable.pain), contentDescription = null)
        }

        //Outer column that will contain all our text fields
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.90f)
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(color = MaterialTheme.colors.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) 
        {
            //Remind the user of the screen
            Text(text = "Create An Account ",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = MaterialTheme.typography.h5.fontWeight,
                color = MaterialTheme.colors.primary)

            //Username text field
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                Surface() {
                    OutlinedTextField(
                        value = username.value, onValueChange = { username.value = it },
                        label = { Text(text = "Username") },
                        placeholder = { Text(text = "Username") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(.70f)
                    )
                }
            }

            //Email text field
            Spacer(modifier = Modifier.padding(5.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                Surface() {
                    OutlinedTextField(
                        value = email.value, onValueChange = { email.value = it },
                        label = { Text(text = "Email") },
                        placeholder = { Text(text = "Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(.70f)
                    )
                }
            }


            //First Password text field
            Spacer(modifier = Modifier.padding(5.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                Surface() {
                    //Password text field
                    OutlinedTextField(
                        value = password1.value,
                        onValueChange = { password1.value = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            })
                            {
                                Icon(painter = icon, contentDescription = "Visibility Icon")
                            }
                        },
                        label = { Text(text = "Password") },
                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(.7f),
                        visualTransformation =
                        if (passwordVisibility)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation()
                    )
                }
            }

            //Second password text field
            Spacer(modifier = Modifier.padding(5.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                Surface() {
                    //Password text field
                    OutlinedTextField(
                        value = password2.value,
                        onValueChange = { password2.value = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            })
                            {
                                Icon(painter = icon, contentDescription = "Visibility Icon")
                            }
                        },
                        label = { Text(text = "Confirm Password") },
                        placeholder = { Text(text = "Confirm Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(.7f),
                        visualTransformation =
                        if (passwordVisibility)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation()
                    )
                }
            }

            //Add some space before the sign in button
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {
                //TODO HERE
                //Create account functionality
                //Something like check the user name and password in the database
                //If it exists, then send em back to the main menu, logged in style
            },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(50.dp))
            {
                Text(text = "Create Account", fontSize = MaterialTheme.typography.h5.fontSize)
            }

        }
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
@Preview
fun CreateAccountPrev()
{
    CreateAccount(navController = rememberNavController())
}