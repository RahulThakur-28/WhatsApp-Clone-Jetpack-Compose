package com.example.rahul.whatsapp.presentation.userregestrationscreen


import android.R.attr.text
import android.R.attr.value
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.rahul.whatsapp.R
import com.example.rahul.whatsapp.presentation.viewmodel.PhoneAuthViewModel
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.focusModifier
import com.example.rahul.whatsapp.presentation.navigation.Routes
import com.example.rahul.whatsapp.presentation.viewmodel.AuthState

@Composable
fun AuthScreen(
    navController: NavHostController,
    PhoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()
) {

    val authState by PhoneAuthViewModel.authState.collectAsState()
    val context = LocalContext.current
    val activity = context as Activity  // maybe error in this in future


    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedCountry by remember {
        mutableStateOf("Japan")
    }

    var countryCode by remember {
        mutableStateOf("+91")
    }

    var phoneNumber by remember {
        mutableStateOf("")
    }

    var otp by remember { mutableStateOf("") }

    var verificatonId by remember { mutableStateOf<String?>(null) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Enter your phone number",
            fontSize = 20.sp,
            color = colorResource(id = R.color.DarkGreen),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row {

            Text(text = " whatsApp will need to verify your phone number ")

            Spacer(modifier = Modifier.width(4.dp))

            Text(text = " what's", color = colorResource(id = R.color.DarkGreen))

        }

        Text(text = " my number?", color = colorResource(R.color.DarkGreen))

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {

            Box(modifier = Modifier.width(230.dp)) {
                Text(
                    text = selectedCountry,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.align(
                        Alignment.CenterEnd
                    ),
                    tint = colorResource(id = R.color.LightGreen)
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 66.dp),
            thickness = 2.dp,
            color = colorResource(R.color.LightGreen)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {

            listOf(
                "India",
                "United States",
                "United Kingdom",
                "Canada",
                "Australia",
                "Japan",
                "China",
                "Germany",
                "France",
                "Italy",
                "Spain",
                "Brazil",
                "Russia",
                "South Korea",
                "Singapore",
                "Malaysia",
                "Thailand",
                "Indonesia",
                "Pakistan",
                "Bangladesh",
                "Nepal",
                "Sri Lanka",
                "Afghanistan",
                "United Arab Emirates",
                "Saudi Arabia",
                "Qatar",
                "Kuwait",
                "Oman",
                "Turkey",
                "South Africa"
            ).forEach { country ->

                DropdownMenuItem(
                    text = { Text(text = country) },
                    onClick = {
                        selectedCountry = country
                        expanded = false
                    },


                    )

            }
        }

        when (authState) {
            is AuthState.Ideal, is AuthState.Loading, is AuthState.CodeSent -> {

                if (authState is AuthState.CodeSent) {
                    verificatonId = (authState as AuthState.CodeSent).verificationId
                }

                if (verificatonId == null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = countryCode,
                            onValueChange = { countryCode = it },
                            modifier = Modifier.width(78.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(R.color.LightGreen)
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        TextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            placeholder = { Text(" Phone Number") },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height((16.dp)))
                    Button(
                        onClick = {
                            if (phoneNumber.isNotEmpty()) {
                                val fullPhoneNumber = "$countryCode$phoneNumber"
                                PhoneAuthViewModel.sendcerificationCode(fullPhoneNumber, activity)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Plz enter a valid Phone Number",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }, shape = RoundedCornerShape(6.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource((R.color.LightGreen))
                        )

                    ) {
                        Text("Send OTP")
                    }
                    if (authState is AuthState.Loading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }

                } else {
                    //Otp input ui

                    Spacer(modifier = Modifier.height((40.dp)))
                    Text(
                        "Enter Otp",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.DarkGreen)
                    )
                    Spacer(modifier = Modifier.height((8.dp)))
                    TextField(
                        value = otp,
                        onValueChange = { otp = it },
                        placeholder = { Text("Otp") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        )
                    )
                    Spacer(modifier = Modifier.height((8.dp)))
                    Button(onClick = {
                        if(otp.isNotEmpty()&&verificatonId!=null){
                            PhoneAuthViewModel.verifycode(otp,context)
                        }else{
                            Toast.makeText(context, "Please enter a valid OTP", Toast.LENGTH_SHORT).show()
                        }
                    },shape = RoundedCornerShape((6.dp)), colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.DarkGreen)
                    )) {
                        Text("Verify Otp")

                    }
                    if(authState is AuthState.Loading){
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }

                }
            }

            is AuthState.Success -> {

                Log.d("PhoneAuth","Login Successful")
                PhoneAuthViewModel.resetAuthState()

                navController.navigate(Routes.UserProfileSetScreen){
                    popUpTo<Routes.UserRegisterationScreen>{
                        inclusive = true
                    }
                }

            }

            is AuthState.Error->{
                Toast.makeText(context,(authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }

        }


    }

}

