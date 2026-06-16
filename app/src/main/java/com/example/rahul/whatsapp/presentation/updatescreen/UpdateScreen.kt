package com.example.rahul.whatsapp.presentation.updatescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rahul.whatsapp.R
import com.example.rahul.whatsapp.presentation.bottomnavigation.BottomNavigation
import com.example.rahul.whatsapp.presentation.navigation.Routes

@Composable
fun UpdateScreen(navHostController: NavHostController) {

    val scrollState = rememberScrollState()
    val samplestatus = listOf(

        statusdata(R.drawable.person_22, "Vikash", "Just now"),
        statusdata(R.drawable.person_23, "Brother","2 min ago" ),
        statusdata(R.drawable.gir, "Riya office", "5mins ago"),
        statusdata(R.drawable.person_24, "Aman Yadav Freind","5 hours ago" ),
        statusdata(R.drawable.girl_3, "Animka", "23hours ago")

    )

    val sampleChannels=listOf(
        Channels(image =R.drawable.neat_roots,name="Neat roots", description = "always learn new" ),
        Channels(image =R.drawable.img,name="Food lovers", description = "new " ),
        Channels(image =R.drawable.meta,name="face", description = "meta is here" ),

    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}, containerColor = colorResource(R.color.LightGreen),
                modifier = Modifier.size(65.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_photo_camera_24),
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            BottomNavigation(navHostController, selectedItem = 1, onClick = {index ->
                when(index){
                    0->{navHostController.navigate(Routes.HomeScreen)}
                    1->{navHostController.navigate(Routes.UpdateScreen)}
                    2->{navHostController.navigate(Routes.CommunitiesScreen)}
                    3->{navHostController.navigate(Routes.CallScreen)}

                }
            })
        },
        topBar = { TopBar() }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Text(
                text = "Status",
                modifier = Modifier.padding(12.dp, 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black

            )

            MyStatus()

            samplestatus.forEach { StatusItem(statusdata = it) }

            Spacer(Modifier.height(16.dp))


            HorizontalDivider(
                color= Color.Gray
            )

            Text(
                text = "Channels",
                modifier = Modifier.padding(12.dp, 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black

            )

            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)){
                Text(text=" Stay updates on the topics that matter to you. Find channel to follow below")

                Spacer(modifier = Modifier.height(32.dp))

                Text(text=("Find channels to Follow"))


            }
            Spacer(modifier = Modifier.height(16.dp))


          sampleChannels.forEach {
              ChannelItemDesign(it)
          }


        }

    }

}