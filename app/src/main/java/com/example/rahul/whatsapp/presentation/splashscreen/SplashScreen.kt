package com.example.rahul.whatsapp.presentation.splashscreen

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rahul.whatsapp.R
import com.example.rahul.whatsapp.presentation.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun splashScreen(navHostController: NavHostController) {

    LaunchedEffect(Unit) {  // in launched efffect the  screen can show on the first time and last time

        delay(1000)

        navHostController.navigate(Routes.WelcomeScreen){
            popUpTo<Routes.SplashScreen>{inclusive= true}  // they helpes to not go back in the splashscreen
        }



    }




    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.whatsapp_icon),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.Center)

        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "From",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold

            )

            Row {
                Icon(
                    painter = painterResource(R.drawable.meta),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(R.color.LightGreen)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Meta", fontSize = 18.sp, fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.LightGreen)
                )
            }
        }

    }

}