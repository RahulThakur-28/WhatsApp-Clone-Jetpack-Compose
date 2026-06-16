package com.example.rahul.whatsapp.presentation.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rahul.whatsapp.R
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.rahul.whatsapp.presentation.navigation.Routes
import com.example.rahul.whatsapp.presentation.viewmodel.PhoneAuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun UserProfileSetScreen(
    phoneAuthViewModel: PhoneAuthViewModel= hiltViewModel(),
    navHostController: NavHostController
) {

    var name by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var profiileImageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }

    val firebaseAuth = Firebase.auth
    val phoneNumber = firebaseAuth.currentUser?.phoneNumber ?: ""
    val userId = firebaseAuth.currentUser?.uid ?: ""

    val context = LocalContext.current


    val imagePickerLauncher =
        rememberLauncherForActivityResult(  // gallery ko image se lekar ayega aur pickup karke aur image ko bitmap mai convert karkek karega string maai convert kargea
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                profiileImageUri = uri

                uri?.let {
                    bitmapImage = if (Build.VERSION.SDK_INT < 28) {
                        @Suppress("DEPRECATION")
                        android.provider.MediaStore.Images.Media.getBitmap(
                            context.contentResolver,
                            it
                        )
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    }

                }

            }
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Box(
            modifier = Modifier
                .size((128.dp))
                .clip(CircleShape)
                .border(2.dp, color = Color.Gray, shape = CircleShape)
                .clickable { imagePickerLauncher.launch("image/*") }
        ) {
            if (bitmapImage != null) {
                Image(
                    bitmap = bitmapImage!!.asImageBitmap(),
                    null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )


            } else if (profiileImageUri != null) {
                Image(

                    //_> coil server se image milta ukso hmare ui mai show karta hai

                    painter = rememberImagePainter(profiileImageUri),
                    null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )


            } else {

                Image(
                    painter = painterResource(R.drawable.uer_profile_prototupe),
                    null,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            }



        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "$phoneNumber")


        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name ,
            onValueChange = {name  = it },
            label = {Text("Name") },
            modifier = Modifier.fillMaxWidth(),

            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.LightGreen)

            )



        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = status ,
            onValueChange = {status  = it },
            label = {Text("Status") },
            modifier = Modifier.fillMaxWidth(),

            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.LightGreen)

            )



        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {

                if (bitmapImage != null) {

                    phoneAuthViewModel.saveUserProfile(
                        userId = userId,
                        name = name,
                        status = status,
                        profileImage = bitmapImage!!
                    )

                    navHostController.navigate(Routes.HomeScreen)

                } else {

                    Toast.makeText(
                        context,
                        "Please select a profile image",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.LightGreen)
            )
        ) {

            Text(text = "Save")

        }



    }

}
