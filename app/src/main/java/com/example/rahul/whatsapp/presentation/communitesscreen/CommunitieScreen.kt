package com.example.rahul.whatsapp.presentation.communitesscreen

import android.R.attr.id
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
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
fun CommunitiesScreen(navHostController: NavHostController) {

    var isSearching by remember {
        mutableStateOf(false)
    }

    var search by remember {
        mutableStateOf("")
    }

    var showMenu by remember {
        mutableStateOf(false)
    }

    val sampleCommunities = listOf(
        Communities(image = R.drawable.img, name = "Tech enthuahsitics","256 memebers" ),
        Communities(image = R.drawable.meta, name = "Meta","992 memebers" ),
        Communities(image = R.drawable.man, name = "Mens","444 memebers" )


    )
    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {

                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        if (isSearching) {

                            TextField(
                                value = search,
                                onValueChange = { search = it },
                                placeholder = {
                                    Text("Search")
                                },
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.padding(start = 12.dp),
                                singleLine = true
                            )

                        } else {

                            Text(
                                text = "Communities",
                                fontSize = 28.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(
                                    start = 12.dp, top = 5.dp
                                )
                            )
                        }

                        Spacer(
                            modifier = Modifier.weight(1f)
                        )

                        if (isSearching) {

                            IconButton(
                                onClick = {
                                    isSearching = false
                                    search = ""
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.cross),
                                    contentDescription = "Close Search",
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                        } else {


                            IconButton(
                                onClick = {
                                    isSearching = true
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.search),
                                    contentDescription = "Search",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Box {
                                IconButton(
                                    onClick = {
                                        showMenu = true
                                    }) {
                                    Icon(
                                        painter = painterResource(R.drawable.more),
                                        contentDescription = "More",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                DropdownMenu(
                                    expanded = showMenu, onDismissRequest = {
                                        showMenu = false
                                    }) {

                                    DropdownMenuItem(text = {
                                        Text("Settings")
                                    }, onClick = {
                                        showMenu = false
                                    })
                                }
                            }
                        }
                    }

                    HorizontalDivider()
                }
            }
        },

        bottomBar = {
            BottomNavigation(navHostController, selectedItem = 2, onClick = {index ->
                when(index){
                    0->{navHostController.navigate(Routes.HomeScreen)}
                    1->{navHostController.navigate(Routes.UpdateScreen)}
                    2->{navHostController.navigate(Routes.CommunitiesScreen)}
                    3->{navHostController.navigate(Routes.CallScreen)}

                }
            })
        }


    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.LightGreen)),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(text="Start a new Community", fontSize =16.sp, fontWeight = FontWeight.Bold )

            }


            Spacer(Modifier.height(8.dp))

            Text(text="Your Communities", fontSize =20.sp, fontWeight = FontWeight.Bold , modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

            LazyColumn() {
                items(sampleCommunities){
                CommunityItemsDesign(communities = it)
                }
            }


        }
    }
}
