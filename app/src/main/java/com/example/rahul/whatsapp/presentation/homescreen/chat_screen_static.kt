//package com.example.rahul.whatsapp.presentation.homescreen
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.example.rahul.whatsapp.R
//import com.example.rahul.whatsapp.presentation.Chat_box.ChatDesignModel
//import com.example.rahul.whatsapp.presentation.bottomnavigation.BottomNavigation
//import com.example.rahul.whatsapp.presentation.navigation.Routes
//
//@Composable
//fun HomeScreen(
//    navHostController: NavHostController
//) {
//
//    val chatData = listOf(
//        ChatDesignModel(
//            image = R.drawable._1,
//            name = "Me(You)",
//            time = "5 minutes ago",
//            message = "this is the last quotes "
//        ),
//        ChatDesignModel(
//            image = R.drawable.gir,
//            name = "Riya office",
//            time = "1:00 PM",
//            message = "is tommorow holiday?"
//        ),
//        ChatDesignModel(
//            image = R.drawable.person_22,
//            name = "Vikash ",
//            time = "2:35 PM",
//            message = "Kal bahar chale?"
//        ),
//        ChatDesignModel(
//            image = R.drawable.person_23,
//            name = "Brother",
//            time = "6:23 PM",
//            message = "this is pdf "
//        ),
//        ChatDesignModel(
//            image = R.drawable.person_24,
//            name = "Dad",
//            time = "7:32PM",
//            message = "is evertything alriht?"
//        ),
//        ChatDesignModel(
//            image = R.drawable.person_25,
//            name = "Aman Yadav Freind ",
//            time = "yesterday",
//            message = "Hi budddy"
//        ),
//        ChatDesignModel(
//            image = R.drawable.person_26,
//            name = "School freind ",
//            time = "28 may",
//            message = "Kal bahar chale?"
//        ),
//
//                ChatDesignModel(
//                image = R.drawable.girl_3,
//        name = "Anamika home",
//        time = "22 may ",
//        message = "hiiii"
//    ),
//        ChatDesignModel(
//            image = R.drawable.girl_2,
//            name = "20 may",
//            time = " ",
//            message = "nothing happen"
//        )
//    )
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {},
//                containerColor = colorResource(R.color.LightGreen),
//                contentColor = Color.White
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.chat_icon),
//                    contentDescription = null, modifier = Modifier.size(40.dp)
//                )
//            }
//        },
//
//        bottomBar = {
//            BottomNavigation(
//                navHostController = navHostController,
//                selectedItem = 0,
//                onClick = { index ->
//
//                    when (index) {
//
//                        0 -> navHostController.navigate(Routes.HomeScreen)
//
//                        1 -> navHostController.navigate(Routes.UpdateScreen)
//
//                        2 -> navHostController.navigate(Routes.CommunitiesScreen)
//
//                        3 -> navHostController.navigate(Routes.CallScreen)
//                    }
//                }
//            )
//        }
//
//    ) { paddingValues ->
//
//        Column(
//            modifier = Modifier.padding(paddingValues)
//        ) {
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Box(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//
//                Text(
//                    text = "WhatsApp",
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = colorResource(R.color.LightGreen),
//                    modifier = Modifier
//                        .align(Alignment.CenterStart)
//                        .padding(start = 16.dp)
//                )
//
//                Row(
//                    modifier = Modifier.align(Alignment.CenterEnd)
//                ) {
//
//                    IconButton(onClick = {}) {
//                        Icon(
//                            painter = painterResource(R.drawable.camera),
//                            contentDescription = null
//                        )
//                    }
//
//                    IconButton(onClick = {}) {
//                        Icon(
//                            painter = painterResource(R.drawable.search),
//                            contentDescription = null
//                        )
//                    }
//
//                    IconButton(onClick = {}) {
//                        Icon(
//                            painter = painterResource(R.drawable.more),
//                            contentDescription = null
//                        )
//                    }
//                }
//            }
//
//            HorizontalDivider()
//
//            LazyColumn {
//
//                items(chatData) { chat ->
//
//                    ChatDesign(
//                        chatDesignModel = chat
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun HomeScreenPreview() {
//
//    HomeScreen(
//        navHostController = rememberNavController()
//    )
//}