package com.example.rahul.whatsapp.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rahul.whatsapp.models.Message
import com.example.rahul.whatsapp.presentation.Chat_box.ChatListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import kotlin.collections.emptyList
import android.util.Base64
import kotlin.jvm.java

class BaseViewModel : ViewModel() {

    fun searchUserByPhoneNumber(
        phoneNumber: String,
        callback: (ChatListModel?) -> Unit
    ) {

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null) {

            Log.e("BaseViewModel", "User is not authenticated")

            callback(null)
            return
        }
        val databaseReference = FirebaseDatabase.getInstance().getReference("users")

        databaseReference
            .orderByChild("phoneNumber")
            .equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                        val user = snapshot.children.first().getValue(ChatListModel::class.java)
                        callback(user)
                        // yaha user ko process karenge
                    } else {

                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "BaseViewModel",
                        "Error fteching User:${error.message},Details:${error.details}"
                    )

                    callback(null)

                }
            }

            )

    }


    fun getChatForUser(
        userId: String,
        callback: (List<ChatListModel>) -> Unit
    ) {

        val chatRef = FirebaseDatabase
            .getInstance()
            .getReference("users/$userId/chats")

        chatRef.orderByChild("userId")
            .equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    val chatList = mutableListOf<ChatListModel>()

                    for (childSnapshot in snapshot.children) {

                        val chat = childSnapshot.getValue(
                            ChatListModel::class.java
                        )

                        if (chat != null) {
                            chatList.add(chat)
                        }
                    }

                    callback(chatList)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }
            }
            )
    }


    private val _chatList = MutableStateFlow<List<ChatListModel>>(emptyList())
    val chatList = _chatList.asStateFlow()

    init {
        loadChatData()

    }

    // this load the chat data
    private fun loadChatData() {

        val currentUserId =
            FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {

            val chatRef =
                FirebaseDatabase.getInstance()
                    .getReference("chats")

            chatRef.orderByChild("userId")
                .equalTo(currentUserId)
                .addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val chatList = mutableListOf<ChatListModel>()

                        for (childSnapshot in snapshot.children) {

                            val chat = childSnapshot.getValue(
                                ChatListModel::class.java
                            )

                            if (chat != null) {
                                chatList.add(chat)
                            }
                        }

                        _chatList.value = chatList

                        // yaha chatList use hoga
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }


    fun addChat(newChat: ChatListModel) {

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {

            val newChatRef =
                FirebaseDatabase.getInstance()
                    .getReference("chats")
                    .push()

            val chatWithUser = newChat.copy(currentUserId)

            newChatRef.setValue(chatWithUser).addOnSuccessListener {
                Log.d(
                    "BaseViewModel",
                    "chat added successfully to firebase"
                )
            }.addOnFailureListener{ exception ->

                Log.e(
                    "BaseViewModel",
                    "Failed to add chat at this time :${exception.message}"
                )

            }
        } else {

            Log.e(
                "BaseViewModel",
                "No User is authenticated "
            )

        }
    }


    private val databaseReference = FirebaseDatabase.getInstance().reference

    fun sendMessage(
        senderPhoneNumber: String, recevierPhoneNumber: String, messageText: String
    ) {
        val messageId = databaseReference.push().key ?: return
        val message = Message(
            senderPhoneNumber = senderPhoneNumber,
            message = messageText,
            timestamp = System.currentTimeMillis()
        )

        databaseReference.child("messages")
            .child(senderPhoneNumber)
            .child(recevierPhoneNumber)
            .child(messageId)
            .setValue(message)


        databaseReference.child("messages")
            .child(recevierPhoneNumber)
            .child(senderPhoneNumber)
            .child(messageId)
            .setValue(message)


    }

    fun getMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onNewMessage: (Message) -> Unit
    ) {

        val messageRef = databaseReference
            .child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        messageRef.addChildEventListener(object : ChildEventListener {


            override fun onChildAdded(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {

                val message =
                    snapshot.getValue(Message::class.java)

                if (message != null) {
                    onNewMessage(message)
                }
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
            }

            override fun onChildRemoved(
                snapshot: DataSnapshot
            ) {
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
            }

            override fun onCancelled(
                error: DatabaseError
            ) {
            }
        })
    }




    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onLastMessageFetched : (String, String) -> Unit
    ) {

        val chatRef = FirebaseDatabase.getInstance().reference
            .child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        chatRef.orderByChild("timestamp")
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                        val lastMessage =
                            snapshot.children.firstOrNull()
                                ?.child("message")
                                ?.value as? String

                        val timestamp =
                            snapshot.children.firstOrNull()
                                ?.child("timestamp")
                                ?.value?.toString()

                        onLastMessageFetched(lastMessage?:"No message", timestamp?:"--:--")

                    } else {
                      onLastMessageFetched("No message ","--:--")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "BaseViewModel",
                        "Failed to fetch last message: ${error.message}"
                    )

                    onLastMessageFetched("", "")
                }


            })
    }


    fun loadChatList(
        currentUserPhoneNumber: String,
        onChatListLoaded: (List<ChatListModel>) -> Unit
    ) {

        val chatList = mutableListOf<ChatListModel>()

        val chatRef = FirebaseDatabase.getInstance()
            .reference
            .child("chats")
            .child(currentUserPhoneNumber)

        chatRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {


                if (snapshot.exists()) {

                    snapshot.children.forEach { child ->

                        val phoneNumber = child.key ?: return@forEach

                        val name = child.child("name").value as? String ?: "Unknown"

                        val image = child.child("image").value as? String

                        val profileImageBitmap = image?.let {
                            decodeBase64ToBitmap(it)
                        }

                        fetchLastMessageForChat(
                            currentUserPhoneNumber,
                            phoneNumber
                        ) { lastMessage, time ->

                            chatList.add(
                                ChatListModel(
                                    name = name,
                                    profileImage = image,
                                    message = lastMessage,
                                    time = time
                                )
                            )

                            if(chatList.size==snapshot.childrenCount.toInt()){

                                onChatListLoaded(chatList)

                            }
                        }
                    }
                }else{
                    onChatListLoaded(emptyList())
                }


            }

            override fun onCancelled(error: DatabaseError) {

                onChatListLoaded(emptyList())
            }
        })
    }


    private fun decodeBase64ToBitmap(base64Image: String): Bitmap? {

        return try {

            val decodedByte =
                Base64.decode(base64Image, android.util.Base64.DEFAULT)

            BitmapFactory.decodeByteArray(
                decodedByte,
                0,
                decodedByte.size
            )

        } catch (e: IOException) {

            null
        }
    }


    // function to converthe bitmap

    fun base64ToBitmap(base64String: String): Bitmap? {

        return try {

            val decodedByte =
                Base64.decode(base64String, android.util.Base64.DEFAULT)

            BitmapFactory.decodeByteArray(
                decodedByte,
                0,
                decodedByte.size
            )

        } catch (e: Exception) {

            null
        }
    }











}
