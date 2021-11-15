package com.example.yeehawholdem

import android.annotation.SuppressLint
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("UnrememberedMutableState")
@Composable
fun CreateAccount(navController : NavController)
{
    //TODO implement checks on the email and user name choice, no duplicates (Security?), or just allow dupes
    //TODO implement the password check to issue the user a prompt if they don't match
    //TODO also actually do something with the gathered variables when they click the create account button

    lateinit var auth: FirebaseAuth;

    auth = Firebase.auth


    //Variables
    val username = remember { mutableStateOf("")}
    val email = remember { mutableStateOf("")}
    val password1 = remember { mutableStateOf("")}
    val password2 = remember { mutableStateOf("")}

    //These used to display an error message if needed
    var showDialog by remember { mutableStateOf(false) }
    var errorResults by remember { mutableStateOf("") }


    //This is used to display a successful or failure
    var userCreatedSuccessfully by remember { mutableStateOf(false) }
    var userNotCreatedSuccessfully by remember { mutableStateOf(false) }

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
                errorResults = checkInputFields(username = username, email = email, password1 = password1, password2 = password2)

                // This triggers a pop up if something is wrong
                if ((errorResults.isNotEmpty())) {
                    showDialog = true
                }
                // Otherwisem the information that was entered was correct
                else
                {
                    auth.createUserWithEmailAndPassword(email.value, password2.value).addOnCompleteListener(
                        OnCompleteListener {
                            if( it.isSuccessful ) {
                                //TODO Update Player OBJ to reflect successful Login

                                userCreatedSuccessfully = true
                            }
                            else {
                                userNotCreatedSuccessfully = true
                            }
                        })
                }
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

    // The pop up error message previously mentioned
    if (showDialog == true) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = errorResults)
            },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                })
                {
                    Text(text = "Dismiss")
                }
            }
        )
    }

    if (userCreatedSuccessfully == true) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "User Account Created!")
            },
            confirmButton = {
                Button(onClick = {
                    userCreatedSuccessfully = false
                    navController.navigate(route = Screen.MainMenu.route)
                })
                {
                    Text(text = "Ok")
                }
            }
        )
    }


    if (userNotCreatedSuccessfully == true) {

        AlertDialog(onDismissRequest = {},
            title = {
                Text(text = "Error creating account, please double check information!")
            },
            confirmButton = {
                Button(onClick = {
                    userNotCreatedSuccessfully = false
                })
                {
                    Text(text = "Ok")
                }
            }
        )
    }

}



fun checkInputFields(username: MutableState<String>, email: MutableState<String>, password1: MutableState<String>, password2: MutableState<String>): String {
    if ((username.value == "") or (email.value == "") or (password1.value == "") or (password2.value == "")){
        return "One or more fields are empty, please fill in appropriate values for all fields!"
    }
    else if (password1.value != password2.value){
        return "The passwords entered do not match!"
    }
    else if(username.value.length < 3) {
        return "Username must be at least 3 characters!"
    }
    else if (password2.value.length < 5){
        return "Password length must be at least 5 characters!"
    }
    else
        return ""
}






@Composable
@Preview
fun CreateAccountPrev()
{
    CreateAccount(navController = rememberNavController())
}