package com.example.rahul.whatsapp.presentation.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rahul.whatsapp.R
import com.example.rahul.whatsapp.presentation.Chat_box.ChatListBox
import com.example.rahul.whatsapp.presentation.Chat_box.ChatListModel
import com.example.rahul.whatsapp.presentation.bottomnavigation.BottomNavigation
import com.example.rahul.whatsapp.presentation.navigation.Routes
import com.example.rahul.whatsapp.presentation.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeBaseViewModel: BaseViewModel
) {

    var showPopup by remember {
        mutableStateOf(false)
    }

    var showMenu by remember {
        mutableStateOf(false)
    }

    var isSearching by remember {
        mutableStateOf(false)
    }

    var searchText by remember {
        mutableStateOf("")
    }

    val chatData by homeBaseViewModel.chatList.collectAsState()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    if (userId != null) {
        LaunchedEffect(userId) {
            homeBaseViewModel.getChatForUser(userId) { }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showPopup = true},
                containerColor = colorResource(R.color.LightGreen),
                contentColor = Color.White,
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigation(navHostController, selectedItem = 0, onClick = {index ->
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
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                if (isSearching) {

                    TextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        placeholder = {
                            Text("Search")
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                } else {

                    Text(
                        text = "WhatsApp",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.LightGreen),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {

                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.camera),
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            isSearching = !isSearching

                            if (!isSearching) {
                                searchText = ""
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isSearching)
                                    R.drawable.cross
                                else
                                    R.drawable.search
                            ),
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            showMenu = !showMenu
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.more),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)

                        )

                        DropdownMenu(expanded = showMenu, onDismissRequest ={
                            showMenu = false
                        } , modifier = Modifier.background(color = Color.White)) {

                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = {
                                    showMenu = false
                                }
                            ) {

                                DropdownMenuItem(
                                    text = { Text("New Group") },
                                    onClick = { showMenu = false }
                                )

                                DropdownMenuItem(
                                    text = { Text("New Broadcast") },
                                    onClick = { showMenu = false }
                                )

                                DropdownMenuItem(
                                    text = { Text("Linked Devices") },
                                    onClick = { showMenu = false }
                                )

                                DropdownMenuItem(
                                    text = { Text("Starred Messages") },
                                    onClick = { showMenu = false }
                                )

                                DropdownMenuItem(
                                    text = { Text("Payments") },
                                    onClick = { showMenu = false }
                                )

                                DropdownMenuItem(
                                    text = { Text("Settings") },
                                    onClick = { showMenu = false
                                    navHostController.navigate(Routes.SettingScreen)}

                                )
                            }
                        }
                    }
                }


            }

            Spacer(modifier = Modifier.height(8.dp))


            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            if(showPopup){
                AddUserPopup(onDismiss = {showPopup= false}, onUserAdd = {newUser ->

                    homeBaseViewModel.addChat(newUser)
                }, baseViewModel =homeBaseViewModel)
            }



            LazyColumn {

                items(chatData){
                    chat ->
                    ChatListBox(chatListModel =  chat, onClick = {
                        navHostController.navigate(Routes.ChatScreen.createRoute(phoneNumber =chat.phoneNumber?:"ok"))
                    }, baseViewModel = homeBaseViewModel)

                }


            }


        }
    }


}
@Composable
fun AddUserPopup(
    onDismiss: () -> Unit,
    onUserAdd: (ChatListModel) -> Unit,
    baseViewModel: BaseViewModel
) {

    var phoneNumber by remember {
        mutableStateOf("")
    }

    var isSearching by remember {
        mutableStateOf(false)
    }

    var userFound by remember {
        mutableStateOf<ChatListModel?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text(text = "Enter Phone Number ") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        Row {

            Button(
                onClick = {

                    isSearching = true

                    baseViewModel.searchUserByPhoneNumber(phoneNumber) { user ->

                        isSearching = false

                        if (user != null) {
                            userFound = user
                        } else {
                            userFound = null
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    colorResource(R.color.LightGreen)
                )
            ) {
                Text(text = "Search")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    colorResource(R.color.LightGreen)
                )
            ) {
                Text(text = "Cancel")
            }
        }

        if (isSearching) {
            Text(
                text = "Searching....",
                color = Color.Gray
            )
        }

        userFound?.let {

            Column {

                Text(
                    text = "User Found ${it.name}"
                )

                Button(
                    onClick = {

                        onUserAdd(it)

                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.LightGreen)
                    )
                ) {
                    Text(text = "Add to Chat")
                }
            }
        }?:run {
            if(!isSearching){
                Text("No user Found")
            }
        }
    }
}